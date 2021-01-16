package com.rahkarpouya.selectandcropimg

import android.graphics.Bitmap
import android.net.Uri

class ImageCroppingResult {
    /** The cropped bitmap  */
    val bitmap: Bitmap?

    /** The saved cropped bitmap uri  */
    val uri: Uri?

    /** The error that occurred during async bitmap cropping.  */
    val error: Exception?

    /** is the cropping request was to get a bitmap or to save it to uri  */
    private val isSave: Boolean

    /** sample size used creating the crop bitmap to lower its size  */
    val sampleSize: Int

    constructor(bitmap: Bitmap?, sampleSize: Int) {
        this.bitmap = bitmap
        uri = null
        error = null
        isSave = false
        this.sampleSize = sampleSize
    }

    constructor(uri: Uri?, sampleSize: Int) {
        bitmap = null
        this.uri = uri
        error = null
        isSave = true
        this.sampleSize = sampleSize
    }

    constructor(error: Exception?, isSave: Boolean) {
        bitmap = null
        uri = null
        this.error = error
        this.isSave = isSave
        sampleSize = 1
    }
} // endregion