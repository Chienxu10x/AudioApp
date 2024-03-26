package com.example.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.audioapp.R
import com.example.audioapp.model.AudioFileInfo
import com.example.listener.AudioClick
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AudioAdapter(context: Context,
                   private val listAudio: List<AudioFileInfo>,
                    private val audioClick: AudioClick
    )
    : RecyclerView.Adapter<AudioAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textFIleName = view.findViewById<TextView>(R.id.txt_name_file)
        val textDateAdd = view.findViewById<TextView>(R.id.txt_date_app)
        val textSizeDate = view.findViewById<TextView>(R.id.txt_size_data)
        val layoutClick = view.findViewById<LinearLayout>(R.id.lout_click)

        fun bindView(audioFileInfo: AudioFileInfo){
            textFIleName.text = audioFileInfo.fileName
            textSizeDate.text = audioFileInfo.fileSize

            // Format date to "dd/MM/yy"


            textDateAdd.text = audioFileInfo.dateAdded
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_audio, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listAudio.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(listAudio[position])
        holder.layoutClick.setOnLongClickListener {
            audioClick.longClick(listAudio[position])
            true
        }

    }


}