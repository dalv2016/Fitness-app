package com.example.fitness_app.utils.Objects

import android.app.AlertDialog
import android.content.Context
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import com.example.fitness_app.R
import com.example.fitness_app.databinding.WeightDialogBinding
import com.example.fitness_app.db.DayModel
import com.example.fitness_app.intarfaces.DialogListener

object DialogManager {
    fun showDialog(context: Context,mID: String, listener : Listener ){
        var builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AlertDialog_AppCompat))
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

    fun showDialog(context: Context, listener : WeightListener ){
        var builder = AlertDialog.Builder(context)
        var dialog = builder.create()
        val binding = WeightDialogBinding.inflate(LayoutInflater.from(context))
        dialog.setView(binding.root)

        binding.apply {
            btnSave.setOnClickListener{
                listener.onClick(editWeight.text.toString())
                dialog.dismiss()
            }
            btnCancle.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }
    interface WeightListener{
        fun onClick(weight: String)
    }
    interface Listener {
        fun onClick()

    }
}