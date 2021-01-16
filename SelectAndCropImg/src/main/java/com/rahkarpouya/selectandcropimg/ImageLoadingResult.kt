package com.rahkarpouya.selectandcropimg

import android.graphics.Bitmap
import android.net.Uri

class ImageLoadingResult {
    /** The Android URI of the image to load  */
    val uri: Uri

    /** The loaded bitmap  */
    val bitmap: Bitmap?

    /** The sample size used to load the given bitmap  */
    val loadSampleSize: Int

    /** The degrees the image was rotated  */
    val degreesRotated: Int

    /** The error that occurred during async bitmap loading.  */
    val error: Exception?

    internal constructor(
        uri: Uri,
        bitmap: Bitmap?,
        loadSampleSize: Int,
        degreesRotated: Int
    ) {
        this.uri = uri
        this.bitmap = bitmap
        this.loadSampleSize = loadSampleSize
        this.degreesRotated = degreesRotated
        error = null
    }

    internal constructor(uri: Uri, error: Exception?) {
        this.uri = uri
        bitmap = null
        loadSampleSize = 0
        degreesRotated = 0
        this.error = error
    }
} // endregion