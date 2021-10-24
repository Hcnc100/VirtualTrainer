package com.d34th.nullpointer.virtual.trainer.ui.extensions

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream


fun Fragment.showSnack(message: String,duration: Int =Snackbar.LENGTH_SHORT,view: View?=null)=
    Snackbar.make(requireView(), message, duration).apply {
        anchorView = view
    }.also {
        it.show()
    }


fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun Bitmap.encodeToBase64(): String {
    val image: Bitmap = this
    val baos = ByteArrayOutputStream()
    image.compress(Bitmap.CompressFormat.PNG, 100, baos)
    val b: ByteArray = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

fun String.decodeImgBase64(): Bitmap {
    val decodedByte: ByteArray = Base64.decode(this, 0)
    return BitmapFactory
        .decodeByteArray(decodedByte, 0, decodedByte.size)
}

fun Uri.toBitmap(context: Context): Bitmap {
    return if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images.Media.getBitmap(
            context.contentResolver,
            this
        )
    } else {
        val source = ImageDecoder.createSource(context.contentResolver, this)
        ImageDecoder.decodeBitmap(source)
    }
}

