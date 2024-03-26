package com.example.audioapp.listener

import com.example.audioapp.model.AudioFileInfo

interface AudioClick {
    fun onClick(audioFileInfo: AudioFileInfo)

    fun longClick(audioFileInfo: AudioFileInfo)
}