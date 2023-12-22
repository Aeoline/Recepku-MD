package com.aubrey.recepku.view.fragments

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
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
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import com.aubrey.recepku.BuildConfig
import com.aubrey.recepku.databinding.FragmentAddBinding
import com.aubrey.recepku.ml.Model2
import com.aubrey.recepku.ml.Model4
import com.aubrey.recepku.view.ViewModelFactory
import com.aubrey.recepku.view.home.HomeViewModel
import com.aubrey.recepku.view.search.SearchActivity
import okio.IOException
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private var currentImageUri: Uri? = null

    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        }
        classifyImage(binding.imageView.drawable.toBitmap())
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
        classifyImage(binding.imageView.drawable.toBitmap())
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
        binding.cameraButton.setOnClickListener {
            setUpCamera()
        }
        binding.galleryButton.setOnClickListener {
            setUpGallery()
        }
        binding.btnSearchRecipe.setOnClickListener {
            val foodName = binding.tvMakanan.text.toString()
            val intent = Intent(requireContext(), SearchActivity::class.java)
            intent.putExtra("searchQuery", foodName)
            startActivity(intent)
        }

        playAnimation()
    }



    private fun classifyImage(image: Bitmap) {
        try {
            val model = context?.let { Model2.newInstance(it) }
            val imageSize = 32
            val result = binding.tvMakanan

            // Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 32, 32, 3), DataType.FLOAT32)
            val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())
            inputFeature0.loadBuffer(byteBuffer)

            val intValues = IntArray(image.width * image.height)
            image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)

            var pixel = 0
            // Iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for (i in 0 until imageSize) {
                for (j in 0 until imageSize) {
                    val value = intValues[pixel++] // RGB
                    byteBuffer.putFloat(((value shr 16) and 0xFF) * (1f / 1))
                    byteBuffer.putFloat(((value shr 8) and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((value and 0xFF) * (1f / 1))
                }
            }
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs = model?.process(inputFeature0)
            val outputFeature0 = outputs?.outputFeature0AsTensorBuffer

            val confidences = outputFeature0?.floatArray
            // Find the index of the class with the biggest confidence.
            var maxPos = 0
            var maxConfidence = 0f
            if (confidences != null && confidences.isNotEmpty()) {
                for (i in confidences.indices) {
                    if (confidences[i] > maxConfidence) {
                        maxConfidence = confidences[i]
                        maxPos = i
                    }
                }
            } else {
                // Handle the case when confidences array is null or empty.
                // For example, you can set maxPos to a default value or display an error message.
                return
            }

            val classes = arrayOf(
                "Bakso", "Bika Ambon", "Dadar Gulung", "Kue Cubit", "Nasi Goreng",
                "Pepes Ikan", "Putu Ayu", "Rendang", "Sate ayam", "Telur Balado", "Tempe Bacem"
            )
            result.text = classes[maxPos]


            // Releases model resources if no longer used.
            model.close()
        } catch (e: IOException) {
            // TODO: Handle the exception
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

    private fun playAnimation(){
        val image = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(800)
        val makanan = ObjectAnimator.ofFloat(binding.tvMakanan, View.ALPHA, 1f).setDuration(800)
        val gallery = ObjectAnimator.ofFloat(binding.galleryButton, View.ALPHA, 1f).setDuration(800)
        val camera = ObjectAnimator.ofFloat(binding.cameraButton, View.ALPHA, 1f).setDuration(800)
        val search = ObjectAnimator.ofFloat(binding.btnSearchRecipe, View.ALPHA, 1f).setDuration(800)

        val tgt = AnimatorSet().apply {
            playTogether(gallery,camera,search)
        }

        AnimatorSet().apply {
            playSequentially(image,makanan,tgt)
            start()
        }
    }






    companion object{
        private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
    }

}