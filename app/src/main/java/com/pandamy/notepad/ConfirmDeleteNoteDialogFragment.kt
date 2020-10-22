package com.pandamy.notepad

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ConfirmDeleteNoteDialogFragment(val noteTitle : String? = "") : DialogFragment() {

    interface ConfirmDeleteNoteDialogListener {
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    var listener : ConfirmDeleteNoteDialogListener? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(context!!)
        builder.setMessage("Etes vous sur de vouloir supprimer la note  : ${noteTitle}")
            .setPositiveButton("Supprimer",DialogInterface.OnClickListener{ dialogInterface, id -> listener?.onDialogPositiveClick() })
            .setNegativeButton("Annuler",DialogInterface.OnClickListener{ dialogInterface, id -> listener?.onDialogNegativeClick() })
        return builder.create()
    }
}