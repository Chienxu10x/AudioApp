package com.example.audioapp.presenter

import android.content.Context
import com.example.audioapp.model.AudioHelper
import com.example.audioapp.view.AudioView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AudioPresenter(private val context: Context, private val audioView: AudioView) {

    @OptIn(DelicateCoroutinesApi::class)
    fun showAudios(){
        GlobalScope.launch ( Dispatchers.Main){
            val audios = AudioHelper.getAllAudioFiles(context)
            audioView.displayAudios(audios)
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    fun renameAudio(id: Long, newName: String){
        GlobalScope.launch( Dispatchers.IO){
            AudioHelper.renameAudioFile(context, id, newName)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun deleteAudio(id: Long){
        GlobalScope.launch (Dispatchers.IO){
            AudioHelper.deleteAudioFile(context, id)
        }
    }

}