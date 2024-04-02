package com.example.fitness_app.utils

import android.app.AlertDialog
import android.content.Context
import com.example.fitness_app.intarfaces.DialogListener

object DialogManager {
    fun showDialog(context: Context,mID: String, listener : DialogListener ){
        var builder = AlertDialog.Builder(context)
        var dialog:AlertDialog?=null
        builder.setTitle("alert")
        builder.setMessage(mID)
        builder.setPositiveButton("reset"){_,_ ->
            listener.onClick()
            dialog?.dismiss()
        }
        builder.setNegativeButton("back"){_,_ ->
            dialog?.dismiss()
        }
        dialog = builder.create()
        dialog.show()
    }
}