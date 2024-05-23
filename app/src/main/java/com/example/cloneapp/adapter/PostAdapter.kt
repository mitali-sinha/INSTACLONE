package com.example.cloneapp.adapter


import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.cloneapp.Models.Post
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.cloneapp.Models.User
import com.example.cloneapp.R
import com.example.cloneapp.databinding.PostRvBinding
import com.example.cloneapp.utils.USER_NODE
import com.github.marlonlom.utilities.timeago.TimeAgo


class PostAdapter (var context: Context, var postList: ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.MyHolder>(){

    inner class MyHolder( var binding : PostRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var binding= PostRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }


    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        try {
            Firebase.firestore.collection(USER_NODE).document(postList.get(position).uid).get().addOnSuccessListener {
                var user = it.toObject<User>()
                Glide.with(context).load(user!!.image).placeholder(R.drawable.user).into(holder.binding.profileImage)
                holder.binding.name.text=user.name

            }

        }catch(e:Exception){

        }


        Glide.with(context).load(postList.get(position).postUrl).placeholder(R.drawable.loading).into(holder.binding.postImage)
        try{
            val text = TimeAgo.using(postList.get(position).time.toLong())
            holder.binding.time.text
        }catch (e:Exception){
            holder.binding.time.text=""
        }
        holder.binding.share.setOnClickListener {
            var i = Intent(Intent.ACTION_SEND)
            i.type="text/plain"
            i.putExtra(Intent.EXTRA_TEXT,postList.get(position).postUrl)
            context.startActivity(i)
        }
        holder.binding.caption.text=postList.get(position).caption
        holder.binding.like.setOnClickListener{
            holder.binding.like.setImageResource(R.drawable.red_heart)
        }
        holder.binding.save.setOnClickListener{
            holder.binding.save.setImageResource(R.drawable.saved)
        }

    }
}