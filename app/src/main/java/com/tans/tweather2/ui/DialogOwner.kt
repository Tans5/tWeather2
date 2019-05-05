package com.tans.tweather2.ui

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import com.tans.tweather2.R
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

interface DialogOwner {

    val context: Context

    fun Completable.showLoadingDialog(): Completable {
        val loadingDialog = createLoadingDialog()
        return doOnSubscribe { loadingDialog.show() }
                .doFinally { loadingDialog.cancel() }
    }

    fun <T> Single<T>.showLoadingDialog(): Single<T> {
        val loadingDialog = createLoadingDialog()
        return doOnSubscribe { loadingDialog.show() }
                .doFinally { loadingDialog.cancel() }
    }

    fun <T> Observable<T>.showLoadingDialog(): Observable<T> {
        val loadingDialog = createLoadingDialog()
        return doOnSubscribe { loadingDialog.show() }
                .doFinally { loadingDialog.cancel() }
    }

    fun <T> Maybe<T>.showLoadingDialog(): Maybe<T> {
        val loadingDialog = createLoadingDialog()
        return doOnSubscribe { loadingDialog.show() }
                .doFinally { loadingDialog.cancel() }
    }

    fun showCommonDialog(title: Int, msg: Int): Single<DialogEvent> = Single.create { emitter ->
        AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(R.string.dialog_ok) { dialog, _ ->
                    emitter.onSuccess(DialogEvent.PositiveEvent(dialog))
                }
                .setNegativeButton(R.string.dialog_cancel) { dialog, _ ->
                    emitter.onSuccess(DialogEvent.NegativeEvent(dialog))
                }
                .setOnCancelListener {
                    emitter.onSuccess(DialogEvent.CancelEvent(it))
                }
                .create()
                .show()
    }


    private fun createLoadingDialog()
            : AlertDialog = AlertDialog.Builder(context)
            .setView(R.layout.layout_loading)
            .setCancelable(false)
            .create().apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }

}

sealed class DialogEvent(val dialog: DialogInterface) {
    class PositiveEvent(dialog: DialogInterface) : DialogEvent(dialog)
    class NegativeEvent(dialog: DialogInterface) : DialogEvent(dialog)
    class CancelEvent(dialog: DialogInterface) : DialogEvent(dialog)
}