package com.rahkarpouya.cropimage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.rahkarpouya.selectandcropimg.CropImage
import com.rahkarpouya.selectandcropimg.enumClass.CropGuidelines

class MainActivity : AppCompatActivity() {

    private val ivImage by lazy { findViewById<AppCompatImageView>(R.id.ivImage) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivImage.setOnClickListener {
            CropImage.activity()
                .setGuidelines(CropGuidelines.ON)
                .setActivityTitle("انتخاب عکس")
                .start(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri: Uri = result!!.uri!!
                val imagePath = resultUri.path!!
                ivImage.setImageURI(resultUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result!!.error
                Log.i("CropImage", error!!.message!!)
            }
        }
    }

}