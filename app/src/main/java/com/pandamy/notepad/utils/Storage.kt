package com.pandamy.notepad.utils

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.pandamy.notepad.Note
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*


private val TAG = "storage"

// save note in file
fun persistNoteStorage(context : Context, note : Note) {
    //si new note lui créer un filename pour la stocker
    if(TextUtils.isEmpty(note.fileName)){
        note.fileName = UUID.randomUUID().toString() + ".notesP"
    }

    //ouvre le fichier en écriture et le context de l'app donne accés
    // au fichier stocker dans le dossier de l'app
    // Context.MODEPRIVATE => empeche d'autre app d'accéder a se fichier
    val fileOutput = context.openFileOutput(note.fileName,Context.MODE_PRIVATE)
    //ouvir le flux de données
    //ou on écrit de dedans et lui ecrit dans le fichier
    val outPutStream = ObjectOutputStream(fileOutput)
    // c'est pour cela que note et serializable pour le stocker comme objet
    outPutStream.writeObject(note)
    // cloture le stream
    outPutStream.close()
}

//charge la note et la retourne
private fun loadNoteStorage(context : Context, filename : String) : Note {
    // inverse que persistNote
    // on lit le nom de fichier on ouvre stream liér a se nom et on récup l'object
    // il et serializable on le cast en note
    // on retourne la note
    val fileInput = context.openFileInput(filename)
    val inputStream = ObjectInputStream(fileInput)
    val note = inputStream.readObject() as Note

    return note
}

//liste de note chargé
fun loadNotesStorage(context: Context) : MutableList<Note> {
    val notes = mutableListOf<Note>()

    val notesDir = context.filesDir
    for (filename in notesDir.list()) {
        val note = loadNoteStorage(context, filename)
        Log.i(TAG, "loadNotes: note = $note")
        notes.add(note)
       }

    return notes
}

//delete Note
fun deleteNoteStorage(context: Context, note: Note) {
    context.deleteFile(note.fileName)
}