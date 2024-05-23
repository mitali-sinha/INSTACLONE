package com.example.cloneapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cloneapp.Models.Post
import com.example.cloneapp.Models.User
import com.example.cloneapp.R
import com.example.cloneapp.adapter.FollowAdapter
import com.example.cloneapp.adapter.PostAdapter
import com.example.cloneapp.databinding.FragmentHomeBinding
import com.example.cloneapp.utils.FOLLOW
import com.example.cloneapp.utils.POST
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: PostAdapter
    private  var postList=ArrayList<Post>()
    private var followList =ArrayList<User>()
    private lateinit var followAdapter: FollowAdapter

    override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater,container,  false)
        adapter= PostAdapter(requireContext(),postList)
        binding.postRv.layoutManager=LinearLayoutManager(requireContext())
        binding.postRv.adapter=adapter

        //follow
        followAdapter=FollowAdapter(requireContext(),followList)
        binding.followRv.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.followRv.adapter=followAdapter

        setHasOptionsMenu(true)          //to show like and share
        (requireContext() as AppCompatActivity).setSupportActionBar(binding.materialToolbar2)

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+FOLLOW).get().addOnSuccessListener {
            var tempList = ArrayList<User>()
            followList.clear()
            for(i in it.documents){
                var user: User = i.toObject<User>()!!
                tempList.add(user)
            }
            followList.addAll(tempList)
            followAdapter.notifyDataSetChanged()
        }
        Firebase.firestore.collection(POST).get().addOnSuccessListener {
        var tempList=ArrayList<Post>()
        postList.clear()
            for(i in it.documents )
            {
                var post:Post= i.toObject<Post>()!!
                tempList.add(post)
            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        return binding.root
}

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}