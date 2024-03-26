package com.example.audioapp.model

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object AudioHelper {
    fun getAllAudioFiles(context: Context): ArrayList<AudioFileInfo> {
        val audioFilesInfo = ArrayList<AudioFileInfo>()
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.SIZE
        )

        val cursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                val filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                val fileName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                val dateAdded = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED))
                val fileSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE))


                // Convert file size to KB or MB
                val fileSizeString = when {
                    fileSize >= 1024 * 1024 -> "%.2f MB".format(fileSize.toFloat() / (1024 * 1024))
                    fileSize >= 1024 -> "%.2f KB".format(fileSize.toFloat() / 1024)
                    else -> "$fileSize bytes"
                }

                val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
                val dateAdd = Date(dateAdded * 1000) // Convert milliseconds to seconds
                val formattedDate = dateFormat.format(dateAdd)


                val audioFileInfo = AudioFileInfo(id, filePath, fileName, formattedDate, fileSizeString)
                audioFilesInfo.add(audioFileInfo)
                Log.d("AudioFile", "File path: $filePath, Name: $fileName, Date Added: $dateAdded, Size: $fileSizeString")
            }
            cursor.close()
        }
        return audioFilesInfo
    }

    fun renameAudioFile(context: Context, audioId: Long, newFileName: String): Boolean {
        val contentResolver: ContentResolver = context.contentResolver
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Audio.Media.DISPLAY_NAME, newFileName)

        val updateUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, audioId)

        val rowsAffected = contentResolver.update(updateUri, contentValues, null, null)
        return rowsAffected > 0
    }

    // Function to delete an audio file by ID
    fun deleteAudioFile(context: Context, audioId: Long): Boolean {
        val contentResolver: ContentResolver = context.contentResolver

        val deleteUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, audioId)
        val rowsAffected = contentResolver.delete(deleteUri, null, null)
        return rowsAffected > 0
    }



}

