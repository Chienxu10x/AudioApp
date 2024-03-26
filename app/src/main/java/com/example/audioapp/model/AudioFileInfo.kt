package com.example.audioapp.model


data class AudioFileInfo(
    val id: Long,
    val filePath: String,
    val fileName: String,
    val dateAdded: String,
    val fileSize: String
)