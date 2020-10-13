package com.pandamy.notepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_note_list.*

class NoteListActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        val TAG: String = NoteListActivity::class.java.simpleName
    }

    // create les vars
    lateinit var notes : MutableList<Note>
    lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        //init les var
        notes = mutableListOf<Note>()

        // note temporaire pour test
        notes.add(Note("note1", "dfdjkfbjdbd"))
        notes.add(Note("note2", "dfdjk"))
        notes.add(Note("note", "fsjcohjcirgiorbhqhp"))
        notes.add(Note("note65", "fsjgsrgsgcohjcirgiorbhqhp"))
        notes.add(Note("note00", "rgs"))
        notes.add(Note("note5", "fsjcohjcitjykfyuli'(-è-(-rgiorbhqhp"))
        notes.add(Note("note3555", "&&²²²²"))
        notes.add(Note("note23", ")ççàçg)àsçrg)rs"))
        notes.add(Note("note110", "fsjcohjcirgiorbhqhp"))
        notes.add(Note("note30", "fsjcohjcirgiorbhqhprdthdjyhretjsy"))


        adapter = NoteAdapter(notes,this)
        noteRV.layoutManager = LinearLayoutManager(this)
        noteRV.adapter = adapter
    }

    override fun onClick(itemView: View) {
        Log.i(TAG, "onClick: Click")
        showNote(itemView.tag as Int)
    }

    private fun showNote(noteIndex : Int) {
        val note = notes[noteIndex]
        val intent = Intent(this, NoteDetailActivity::class.java)
        //passe en intent le int et la class parcelable
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE,note)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, noteIndex)
        startActivity(intent)
    }
}