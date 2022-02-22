package com.example.ongraphtask6.ui.activities.ui.dashboard

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ongraphtask6.R
import com.example.ongraphtask6.databinding.ActivityPostPictureBinding
import com.example.ongraphtask6.rv.GridRVAdapter
import com.example.ongraphtask6.rv.HashTagRvAdapter
import com.example.uploadfeed.retrofit.BuilderInstanceUploadPic
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.Exception

class PostPictureActivity : AppCompatActivity() {
    private lateinit var mAdapter: HashTagRvAdapter
    val TAG = "jay upload pic"
    private lateinit var binding: ActivityPostPictureBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostPictureBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val path = intent.getStringExtra("1")
        val file = File(path!!)

        setupAutocompleteTV()
        setupRV()

        Glide.with(applicationContext)
            .load(file)
            .centerCrop()
            .into(findViewById<ImageView>(R.id.pictureToPost))

        postPic(file)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                // API 5+ solution
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRV() {
        val rv = findViewById<RecyclerView>(R.id.hashtag_rv)
        val list = mutableListOf<String>()
        mAdapter = HashTagRvAdapter(list, applicationContext)

        mAdapter.msetupOnClickListner(object : HashTagRvAdapter.MyOnItemClickListner {
            override fun onItemClick(position: Int) {
                mAdapter.dataset.removeAt(position)
                mAdapter.notifyDataSetChanged()
            }

        })

        rv.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }


    }

    private fun setupAutocompleteTV() {
        val autocomplete = findViewById<AutoCompleteTextView>(R.id.autoCompleteTVLanguages)
        val language = arrayOf(
            "Happy",
            "Love",
            "Morning",
            "Sunset",
            "Outfit",
            "Fashion",
            "Rain",
            "Dogs",
            "Cats",
            "Batman"
        )
        val languageAdapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, language)

        autocomplete.onItemClickListener = object : AdapterView.OnItemClickListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                mAdapter.dataset.add(autocomplete.text.toString())
                mAdapter.notifyDataSetChanged()
                autocomplete.setText("")
            }
        }

        autocomplete.setAdapter(languageAdapter)
    }

    private fun postPic(mFile: File) {
        binding.postThePicture.setOnClickListener {

            val filePart = MultipartBody.Part.createFormData(
                "image",
                mFile.name,
                RequestBody.create(MediaType.parse("image/*"), mFile)
            )

            val description: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                binding.postPicDescriptionet.text.toString()
            )

            val id: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                "211"
            )


            val hashtag: RequestBody = RequestBody.create(
                MediaType.parse("text/plain"),
                "[\"#frds\",\"#care\"]"
            )



            lifecycleScope.launchWhenCreated {
                val response = try {
                    BuilderInstanceUploadPic.builderAPI.addToFeed(
                        id,
                        description,
                        filePart,
                        hashtag
                    )
                } catch (e: Exception) {
                    Log.d(TAG, "onCreate: login Exception $e  , ${e.message}")
                    return@launchWhenCreated
                }

                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d(TAG, "onCreate: response successful ${response.body()}")
                } else {
                    Log.d(TAG, "onCreate: response unsuccessful")
                    Toast.makeText(applicationContext, "some Error Occurred", Toast.LENGTH_SHORT)
                        .show()

                }
            }
        }
    }
}