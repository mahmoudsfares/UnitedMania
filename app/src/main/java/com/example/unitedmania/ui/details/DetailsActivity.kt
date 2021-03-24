package com.example.unitedmania.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.unitedmania.databinding.ActivityDetailsBinding
import com.squareup.picasso.Picasso

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Picasso.get().load(intent.getStringExtra("imageUrl")).into(binding.detailsImage)
        binding.detailsSource.text = intent.getStringExtra("source")
        binding.detailsTitle.text = intent.getStringExtra("title")
        var details = intent.getStringExtra("details")
        if(details == null)
            details = ""
        else{
            val detailsStopPosition = details.indexOf("[+")
            if(detailsStopPosition != -1)
                details = details.substring(0, detailsStopPosition)
        }
        binding.details.text = details
        binding.detailsFullArticle.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(intent.getStringExtra("url")))
            startActivity(i)
        }
    }
}