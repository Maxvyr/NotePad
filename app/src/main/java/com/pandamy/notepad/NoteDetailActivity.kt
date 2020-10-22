package com.pandamy.notepad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_note_detail.*

class NoteDetailActivity : AppCompatActivity() {

    companion object{
        val EXTRA_NOTE = "notes"
        val EXTRA_NOTE_INDEX = "indexNotes"
        val REQUEST_EDIT_NOTE = 1
    }

    lateinit var note : Note
    var indexNote : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        //toolbar + button return
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        note = intent.getParcelableExtra<Note>(EXTRA_NOTE)!!
        indexNote = intent.getIntExtra(EXTRA_NOTE_INDEX, -1)

        titleDetail.setText(note.title)
        noteDetail.setText(note.txt)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_note_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_save -> {
                sendingNewNoteValue()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sendingNewNoteValue() {
        //recup le text entrée dans la note
        note.title = titleDetail.text.toString()
        note.txt = noteDetail.text.toString()

        intent = Intent()
        intent.putExtra(EXTRA_NOTE, note)
        intent.putExtra(EXTRA_NOTE_INDEX,indexNote)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}