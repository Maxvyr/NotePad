package com.pandamy.notepad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pandamy.notepad.utils.deleteNoteStorage
import com.pandamy.notepad.utils.loadNotesStorage
import com.pandamy.notepad.utils.persistNoteStorage
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

        //toolbar
        setSupportActionBar(toolbar)

        //FAB send click to the click of the view
        createNoteFab.setOnClickListener(this)

        //init les Notes
        notes = loadNotesStorage(this)

        // note temporaire de base
        if(notes.isEmpty()) {
            notes.add(Note("Idée", "Le savoir c'est le pouvoir"))
        }


        adapter = NoteAdapter(notes,this)
        noteRV.layoutManager = LinearLayoutManager(this)
        noteRV.adapter = adapter
    }

    override fun onClick(itemView: View) {
        Log.i(TAG, "onClick: Click")
        if (itemView.tag != null) {
            showNote(itemView.tag as Int)
        }
        when(itemView.id){
            R.id.createNoteFab -> createNewnote()
        }
    }

    private fun createNewnote() {
        //passe neg note to create a new note
        showNote(-1)
    }

    private fun showNote(noteIndex : Int) {
        // if neg create a new note
        val note = if(noteIndex < 0) Note() else notes[noteIndex]
        val intent = Intent(this, NoteDetailActivity::class.java)
        //passe en intent le int et la class parcelable
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE,note as Parcelable)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, noteIndex)
        startActivityForResult(intent,NoteDetailActivity.REQUEST_EDIT_NOTE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK || data ==null) {
            return
        }
        when (requestCode) {
            NoteDetailActivity.REQUEST_EDIT_NOTE -> editNoteResult(data) 
        }
    }

    private fun editNoteResult(data: Intent) {
        val indexNote = data.getIntExtra(NoteDetailActivity.EXTRA_NOTE_INDEX,-1)

        // with the diff ACTION (string) put in the intent of the NoteDetailActivity
        // make diff thing
        when (data.action){
            NoteDetailActivity.ACTION_SENDING_NEW_NOTE_VALUE -> {
                val note = data.getParcelableExtra<Note>(NoteDetailActivity.EXTRA_NOTE)
                saveNote(note, indexNote)
            }
            NoteDetailActivity.ACTION_DELETE_NOTE -> {
                deleteNote(indexNote)
            }
        }
    }

    private fun saveNote(note: Note?, indexNote: Int) {
        //save note in appFolder
        persistNoteStorage(this,note!!)
        // if note neg on l'add au debut sinon
        //update les valeur de l'affichage de la note existante
        if (indexNote < 0) {
            //on le met a l'index 0 pour qui se mette en haut de la liste
            notes.add(0, note)
        } else {
            notes[indexNote] = note
        }
        adapter.notifyDataSetChanged()
    }

    private fun deleteNote(indexNote: Int) {
        // note index inf 0 nothing to do exit fun
        // else remove the note from the list
        // update the adapter
        if (indexNote < 0) {
            return
        }
        val note = notes.removeAt(indexNote)
        //delete note in appFolder
        deleteNoteStorage(this,note)
        adapter.notifyDataSetChanged()

        // show snackbar to explain
        // wich note is delete
        Snackbar.make(coodinatorLayoutListNote, "${note.title} delete", Snackbar.LENGTH_LONG)
            .show()
    }
}