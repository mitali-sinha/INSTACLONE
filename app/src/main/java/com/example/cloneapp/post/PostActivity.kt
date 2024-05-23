package com.example.cloneapp.post


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.cloneapp.HomeActivity
import com.example.cloneapp.Models.Post
import com.example.cloneapp.Models.User
import com.example.cloneapp.databinding.ActivityPostBinding
import com.example.cloneapp.utils.POST
import com.example.cloneapp.utils.USER_NODE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding
    private var imageUrl: Uri? = null

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            imageUrl = uri
            binding.selectImage.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.materialToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.selectImage.setOnClickListener {
            openImagePicker()
        }

        binding.cancelButton.setOnClickListener {
            navigateToHome()
        }

        binding.postButton.setOnClickListener {
            uploadPost()
        }
    }

    private fun openImagePicker() {
        launcher.launch("image/*")
    }

    private fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

//    private fun uploadPost() {
//        val caption = binding.caption.editText?.text.toString().trim()
//        if (imageUrl == null && caption.isEmpty()) {
//            Toast.makeText(this, "Please add an image or write a caption", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val post = Post(
//            imageUrl.toString(),
//            caption
//        )
//
//
//
//
//        Firebase.firestore.collection(POST)
//            .add(post)
//            .addOnSuccessListener {
//                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document().set(post)
//                    .addOnSuccessListener {
//                        Toast.makeText(this, "Post uploaded successfully", Toast.LENGTH_SHORT)
//                            .show()
//                        navigateToHome()
//                    }
//                    .addOnFailureListener { e ->
//                        Toast.makeText(
//                            this,
//                            "Failed to upload post: ${e.message}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//            }
//    }
//}


    private fun uploadPost() {
        val caption = binding.caption.editText?.text.toString().trim()

        if (imageUrl == null && caption.isEmpty()) {
            Toast.makeText(this, "Please add an image or write a caption", Toast.LENGTH_SHORT)
                .show()
            return
        }

        Firebase.firestore.collection(USER_NODE)
            .document(Firebase.auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val user = documentSnapshot.toObject<User>()
                if (user != null) {
                    val time = System.currentTimeMillis()
                    val post = Post(
                        postUrl = imageUrl?.toString() ?: "",
                        caption = caption,
                        uid = Firebase.auth.currentUser!!.uid,
                        time = time.toString()
                    )

                    Firebase.firestore.collection(POST)
                        .add(post)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Post uploaded successfully", Toast.LENGTH_SHORT)
                                .show()
                            navigateToHome()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this,
                                "Failed to upload post: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Failed to retrieve user information: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}












