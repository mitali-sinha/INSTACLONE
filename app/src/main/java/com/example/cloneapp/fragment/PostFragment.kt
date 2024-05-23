package com.example.cloneapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cloneapp.databinding.FragmentPostBinding
import com.example.cloneapp.post.PostActivity
import com.example.cloneapp.post.ReelActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PostFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentPostBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentPostBinding.inflate(inflater,container,false)
        binding.post.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), PostActivity::class.java))
            activity?.finish()
        }
        binding.reel.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), ReelActivity::class.java))
        }
        return binding.root
    }
}