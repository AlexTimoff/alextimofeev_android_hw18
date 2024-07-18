package com.example.alextimofeev_android_hw18.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.alextimofeev_android_hw18.R
import com.example.alextimofeev_android_hw18.data.App
import com.example.alextimofeev_android_hw18.data.PhotoDao
import com.example.alextimofeev_android_hw18.databinding.FragmentPhotoGalleryBinding
import com.example.alextimofeev_android_hw18.presentation.adapter.PhotoAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PhotoGalleryFragment : Fragment() {
    private var _binding: FragmentPhotoGalleryBinding? = null
    private val binding get() = _binding!!
    private val photoAdapter = PhotoAdapter()
    private val viewModel: PhotoViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val photoDao: PhotoDao = (requireActivity().application as App).db.photoDao()
                return PhotoViewModel(photoDao) as T
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoGalleryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = photoAdapter
        viewModel.photos.onEach {
            photoAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        binding.buttonMakePhoto.setOnClickListener {
            parentFragmentManager.commit {
                replace<NewPhotoFragment>(R.id.frame_container)
                addToBackStack(NewPhotoFragment::javaClass.name)
            }
        }
    }


}