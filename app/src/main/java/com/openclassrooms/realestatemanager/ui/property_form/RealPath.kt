package com.openclassrooms.realestatemanager.ui.property_form

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log

object RealPath {
    fun getRealPathFromURI(context: Context, contentURI: Uri): String? {
        val result: String?
        val cursor = context.contentResolver.query(contentURI, null, null, null, null)

        if (cursor == null) {
            // path
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            result = try {
                val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                cursor.getString(idx)
            } catch (e: Exception) {
                Log.e("Exception getRealPath", "e: $e")
                ""
            }
            cursor.close()
        }
        return result
    }
}