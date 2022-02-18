package com.example.skinanalysis.ui.summary

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.skinanalysis.R
import com.example.skinanalysis.databinding.SummaryFragmentBinding


class SummaryFragment : Fragment() {

    private lateinit var binding: SummaryFragmentBinding
    private val viewModel: SummaryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.summary_fragment, container,
            false)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val imageStr = sharedPref.getString("Base64Image", "")

        val bitmap = imageStr!!.convertToBitmap()
        binding.selfieImageView.setImageBitmap(bitmap)
    }

    private fun String.convertToBitmap(): Bitmap? {
        val newImageStr = split(",")[1]
        val imageBytes = Base64.decode(newImageStr, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    companion object {
        private const val TAG = "SummaryFragment"
    }
}