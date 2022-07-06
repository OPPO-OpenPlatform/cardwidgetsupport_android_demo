package com.oplus.support

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.viewbinding.ViewBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class BindingViewHolder<VB : ViewBinding>(val mBinding: VB) : RecyclerView.ViewHolder(mBinding.root)

inline fun <reified T : ViewBinding, S : BindingViewHolder<T>> Adapter<S>.newBindViewHolder(
    viewGroup: ViewGroup
): BindingViewHolder<T> {
    return BindingViewHolder(
        inflaterBinding(
            LayoutInflater.from(viewGroup.context),
            viewGroup
        )
    )
}

inline fun <reified T : ViewBinding> Activity.inflater() = lazy {
    inflaterBinding<T>(layoutInflater).apply {
        setContentView(root)
    }
}

inline fun <reified T : ViewBinding> inflaterBinding(layoutInflater: LayoutInflater) =
    T::class.java.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as T

inline fun <reified T : ViewBinding> inflaterBinding(
    layoutInflater: LayoutInflater,
    viewGroup: ViewGroup
) = T::class.java.getMethod(
    "inflate",
    LayoutInflater::class.java,
    ViewGroup::class.java,
    Boolean::class.java
).invoke(null, layoutInflater, viewGroup, false) as T

fun String.loadFromAsset(context: Context): ByteArray {
    val inputStream = context.assets.open(this)
    val input = inputStream.reader()
    val num = input.readText()
    input.close()
    return num.toByteArray()
}

fun String.isAssertFileExists(context: Context): Boolean {
    val manager = context.assets
    try {
        val name = manager.list("")
        name?.forEach {
            if (this == it) {
                return true
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return false
}

fun String.writeFileProvider(context: Context, dirName: String, packageName: String): Uri {
    val inputStream = context.assets.open(this)
    val imagePath = File(context.filesDir, dirName)
    if (!imagePath.exists()) {
        imagePath.mkdirs()
    }
    val newFile = File(imagePath, this)
    if (!newFile.exists()) {
        newFile.createNewFile()
        val outputStream = FileOutputStream(newFile)
        var num = inputStream.read()
        while (num != -1) {
            outputStream.write(num)
            num = inputStream.read()
        }
        outputStream.close()
    }
    val contentUri =
        FileProvider.getUriForFile(context, "com.oplus.smartproviderdemo.fileprovider", newFile)
    context.grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
    inputStream.close()
    return contentUri
}


fun Drawable.writeFileProvider(
    context: Context,
    dirName: String,
    fileName: String,
    packageNames: Array<String>
) {
    var out: FileOutputStream? = null
    try {
        val file = File(context.filesDir, dirName)
        file.mkdirs()

        val bitmap = (this as BitmapDrawable).bitmap

        val drawableFile = File(file, fileName)
        if (!drawableFile.exists()) {
            out = FileOutputStream(drawableFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
        val uri = FileProvider.getUriForFile(
            context,
            "com.oplus.smartproviderdemo.fileprovider",
            drawableFile
        )

        val flag =
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        packageNames.forEach {
            context.grantUriPermission(it, uri, flag)
        }
    } catch (e: Exception) {
        Log.e("writeFileProvider", "error: $e")
    } finally {
        out?.close()
    }
}