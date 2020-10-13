package com.pandamy.notepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_note_detail.*

class NoteDetailActivity : AppCompatActivity() {

    companion object{
        val EXTRA_NOTE = "notes"
        val EXTRA_NOTE_INDEX = "indexNotes"
    }

    lateinit var note : Note
    var indexNote : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        val titleDetailActivity = findViewById<TextView>(R.id.titleDetail)
        val noteDetailActivity = findViewById<TextView>(R.id.noteDetail)

        note = intent.getParcelableExtra<Note>(EXTRA_NOTE)!!
        indexNote = intent.getIntExtra(EXTRA_NOTE_INDEX, -1)

        titleDetailActivity.text = note.title
        noteDetailActivity.text = note.txt
    }
}