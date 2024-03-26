package com.example.audioapp.view

import com.example.audioapp.model.AudioFileInfo

interface AudioView {
    fun displayAudios(audioFileInfo: List<AudioFileInfo>)
}