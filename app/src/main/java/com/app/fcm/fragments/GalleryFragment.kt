package com.app.fcm.fragments

import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.app.fcm.R
import com.app.fcm.adapters.GalleryAdapter
import com.app.fcm.databinding.FragmentGalleryBinding
import com.app.fcm.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private val binding by viewBinding(FragmentGalleryBinding::bind)

    private var adapter: GalleryAdapter? = null
    private val imageUris = mutableListOf<Uri>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkAndRequestPermissions()
    }

    private fun checkAndRequestPermissions() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_MEDIA_IMAGES
        } else {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(
                requireContext(), permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), 100)
        } else {
            loadImages()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadImages()
        }
    }

    private fun loadImages() {
        adapter = GalleryAdapter(imageUris)
        binding.pager.adapter = adapter
        lifecycleScope.launch {
            getDeviceImagesFlow().collect { uri ->
                imageUris.add(uri)
                adapter?.notifyItemInserted(imageUris.size - 1)
            }
        }
    }

    private fun getDeviceImagesFlow(): Flow<Uri> = flow {
        val collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media._ID)
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        requireActivity().contentResolver.query(collection, projection, null, null, sortOrder)
            ?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val uri = ContentUris.withAppendedId(collection, id)
                    if (cursor.position > 0 && cursor.position % 3 == 0) {
                        emit(Uri.EMPTY)
                    }
                    emit(uri) // emit image as soon as it's available
                }
            }
    }.flowOn(Dispatchers.IO)
}