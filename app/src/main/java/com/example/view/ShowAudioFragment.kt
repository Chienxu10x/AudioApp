package com.example.view

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.PermissionsUtils
import com.example.adapter.AudioAdapter
import com.example.audioapp.R
import com.example.audioapp.databinding.FragmentShowAudioBinding
import com.example.audioapp.model.AudioFileInfo
import com.example.listener.AudioClick
import com.example.presenter.AudioPresenter
import com.example.recyclerview.RecyclerItemClickListener
import java.util.jar.Manifest


class ShowAudioFragment : Fragment(), AudioView, AudioClick {
    private lateinit var binding: FragmentShowAudioBinding
    private lateinit var audioPresenter: AudioPresenter
    private lateinit var adapter: AudioAdapter
    private lateinit var permissionUtils: PermissionsUtils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        init()
        binding = FragmentShowAudioBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        audioPresenter = AudioPresenter(requireContext(), this)
        audioPresenter.showAudios()
    }

    override fun onResume() {
        super.onResume()
        audioPresenter.showAudios()
    }

    override fun displayAudios(audioFileInfo: List<AudioFileInfo>) {
        binding.rycShowAudio.layoutManager = LinearLayoutManager(requireContext())
        adapter = AudioAdapter(requireContext(), audioFileInfo, this)
        binding.rycShowAudio.adapter = adapter
    }

    override fun onClick(audioFileInfo: AudioFileInfo) {

    }

    override fun longClick(audioFileInfo: AudioFileInfo) {
        showPopup(audioFileInfo)
    }

    private fun showPopup(audioFileInfo: AudioFileInfo) {
        val inflater = requireActivity().layoutInflater
        val popupView = inflater.inflate(R.layout.popup_update_data, null)
        val explanationTextView = popupView.findViewById<TextView>(R.id.txt_name_file_old)
        explanationTextView.text = "file name: ${audioFileInfo.fileName}"
        val edTextNewName = popupView.findViewById<EditText>(R.id.ed_txt_new_file_name)
        val buttonUpdate = popupView.findViewById<Button>(R.id.btn_update)
        val buttonDelete = popupView.findViewById<Button>(R.id.btn_delete)


        buttonUpdate.setOnClickListener {
            audioPresenter.renameAudio(audioFileInfo.id, edTextNewName.text.toString())
            audioPresenter.showAudios()
            audioPresenter.showAudios()
        }
        buttonDelete.setOnClickListener {
            audioPresenter.deleteAudio(audioFileInfo.id)
            audioPresenter.showAudios()
            audioPresenter.showAudios()
        }
        val popupWindow = PopupWindow(popupView, 800, 600)
        // Set focusable to true to enable touch events outside of the popup window
        popupWindow.isFocusable = true
        // Show the popup window at the center of the screen
        popupWindow.showAtLocation(requireActivity().findViewById(android.R.id.content), Gravity.CENTER, 0, 0)

        popupWindow.setOnDismissListener {
            audioPresenter.showAudios()
        }
    }

    private fun init() {
        permissionUtils = PermissionsUtils(requireActivity()) { granted ->
        }
        permissionUtils.checkAndRequestStoragePermissions()

    }








}