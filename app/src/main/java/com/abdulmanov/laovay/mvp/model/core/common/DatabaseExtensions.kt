package com.abdulmanov.laovay.mvp.model.core.common

import android.content.Context
import java.io.*

fun Context.copyAttachedDatabase(nameOldDB:String,nameNewDB:String,callback:()->Unit) {
    with(getDatabasePath(nameNewDB)) {
        if (!exists()) {
            parentFile?.mkdirs()
            this@copyAttachedDatabase.assets.open(nameOldDB).copyFile(FileOutputStream(this))
        }
        callback.invoke()
    }
}

fun InputStream.copyFile(outputStream: OutputStream) {
    val buffer = ByteArray(1024)
    while (true) {
        val length = read(buffer, 0, 1024)
        if (length > 0) {
            outputStream.write(buffer, 0, length)
        } else {
            break
        }
    }
    outputStream.flush()
    outputStream.close()
    close()
}
