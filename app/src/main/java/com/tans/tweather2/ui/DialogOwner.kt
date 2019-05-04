package com.tans.tweather2.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import com.tans.tweather2.R
import io.reactivex.subjects.Subject

interface DialogOwner {

    val context: Context

    val showingDialogs: MutableMap<String, AlertDialog>

    val dialogEvents: Subject<DialogEvent>

    fun showLoadingDialog(tag: String) {
        disMissDialog(tag)
        val dialog = AlertDialog.Builder(context)
                .setView(R.layout.layout_loading)
                .setCancelable(false)
                .dismissEvent(tag)
                .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        showingDialogs[tag] = dialog
    }

    fun showCommonDialog(tag: String, title: String?, msg: String?) {
        val dialog = AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .dismissEvent(tag)
                .positiveEvent(tag)
                .negativeEvent(tag)
                .create()
        dialog.show()
        showingDialogs[tag] = dialog
    }

    fun disMissDialog(tag: String) {
        showingDialogs[tag]?.cancel()
        showingDialogs.remove(tag)
        dialogEvents.onNext(DialogEvent.DismissEvent(tag))
    }

    fun AlertDialog.Builder.dismissEvent(tag: String): AlertDialog.Builder =
            setOnDismissListener { disMissDialog(tag) }


    fun AlertDialog.Builder.positiveEvent(tag: String): AlertDialog.Builder =
            setPositiveButton(R.string.dialog_ok) { dialog, _ ->
                dialogEvents.onNext(DialogEvent.OkEvent(tag))
                dialog.dismiss()
            }

    fun AlertDialog.Builder.negativeEvent(tag: String): AlertDialog.Builder =
            setNegativeButton(R.string.dialog_cancel) { dialog, _ ->
                dialogEvents.onNext(DialogEvent.CancelEvent(tag))
                dialog.dismiss()
            }


}

sealed class DialogEvent(val tag: String) {
    class OkEvent(tag: String) : DialogEvent(tag)
    class CancelEvent(tag: String) : DialogEvent(tag)
    class DismissEvent(tag: String) : DialogEvent(tag)
}