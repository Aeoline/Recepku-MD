package com.aubrey.recepku.view.fragments

import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.aubrey.recepku.BuildConfig
import com.aubrey.recepku.databinding.FragmentAddBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private var currentImageUri: Uri? = null

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener {
            setUpCamera()
        }
        binding.registerButton.setOnClickListener {
            setUpGallery()
        }
    }

    private fun setUpGallery(){
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun setUpCamera(){
        val timeStamp = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())
        val imageFile: File
        val uri: Uri

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "$timeStamp.jpg")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/MyCamera/")
            }
            uri = requireContext().contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ) ?: return
        } else {
            val filesDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            imageFile = File(filesDir, "/MyCamera/$timeStamp.jpg")
            if (imageFile.parentFile?.exists() == false) imageFile.parentFile?.mkdir()
            uri = FileProvider.getUriForFile(
                requireContext(),
                "${BuildConfig.APPLICATION_ID}.fileprovider",
                imageFile
            )
        }

        currentImageUri = uri
        launcherIntentCamera.launch(uri)
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.imageView.setImageURI(it)
        }
    }

    companion object{
        private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
    }

}