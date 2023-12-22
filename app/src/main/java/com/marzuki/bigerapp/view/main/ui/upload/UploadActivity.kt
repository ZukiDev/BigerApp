package com.marzuki.bigerapp.view.main.ui.upload

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.marzuki.bigerapp.data.ResultState
import com.marzuki.bigerapp.databinding.ActivityUploadBinding
import com.marzuki.bigerapp.view.ViewModelFactory
import com.marzuki.bigerapp.view.getImageUri
import com.marzuki.bigerapp.view.main.DashboardActivity
import com.marzuki.bigerapp.view.reduceFileImage
import com.marzuki.bigerapp.view.uriToFile

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private var currentImageUri: Uri? = null

    private val viewModel by viewModels<UploadViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.uploadButton.setOnClickListener { uploadImage() }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            val compressedImageFile = imageFile.reduceFileImage()

            val title = binding.etTitle.text.toString().trim()
            val text = binding.etText.text.toString().trim()

            if (title.isNotEmpty() && text.isNotEmpty()) {
                viewModel.getSession().observe(this) { user ->
                    val token = user.token
                    viewModel.uploadImage(token, compressedImageFile, title, text).observe(this) { result ->
                        if (result != null) {
                            when (result) {
                                is ResultState.Loading -> {
                                    binding.uploadButton.isEnabled = false
                                    showLoading(true)
                                }

                                is ResultState.Success -> {
                                    showToast(result.data.status.toString())
                                    binding.uploadButton.isEnabled = true
                                    showLoading(false)
                                    navigateToDashboard()
                                }

                                is ResultState.Error -> {
                                    showToast(result.message.toString())
                                    binding.uploadButton.isEnabled = true
                                    showLoading(false)
                                }
                            }
                        }
                    }
                }
            } else {
                showToast("Title and description cannot be empty")
            }
        } ?: showToast("Empty Image")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        observeUploadResult()
    }

    private fun observeUploadResult() {
        viewModel.uploadResult.observe(this) { result ->
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                }

                is ResultState.Success -> {
                    showToast("Post uploaded successfully: ${result.data}")
                    showLoading(false)
                }

                is ResultState.Error -> {
                    showToast("Failed to upload post. Please try again.")
                    showLoading(false)
                }
            }
        }


    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    private fun navigateToDashboard() {
        val intent = Intent(this@UploadActivity, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}
