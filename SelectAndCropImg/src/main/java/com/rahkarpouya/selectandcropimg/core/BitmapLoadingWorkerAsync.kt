package com.rahkarpouya.selectandcropimg.core

import android.content.Context
import android.net.Uri
import com.rahkarpouya.selectandcropimg.utils.BitmapUtils
import com.rahkarpouya.selectandcropimg.CropImageView
import com.rahkarpouya.selectandcropimg.ImageLoadingResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

class BitmapLoadingWorkerAsync(
    cropImageView: CropImageView,
    /** The Android URI of the image to load  */
    val uri: Uri
) : BaseCoroutine() {

    private val mCropImageViewReference: WeakReference<CropImageView> = WeakReference(cropImageView)
    /** The Android URI that this task is currently loading.  */
    /** The context of the crop image view widget used for loading of bitmap by Android URI  */
    private val mContext: Context = cropImageView.context

    /** required width of the cropping image after density adjustment  */
    private val mWidth: Int

    /** required height of the cropping image after density adjustment  */
    private val mHeight: Int

    init {
        val metrics = cropImageView.resources.displayMetrics
        val densityAdj =
            if (metrics.density > 1) (1 / metrics.density).toDouble() else 1.toDouble()
        mWidth = (metrics.widthPixels * densityAdj).toInt()
        mHeight = (metrics.heightPixels * densityAdj).toInt()
    }

    fun fetChData() {
        launch {
            val result = withContext(Dispatchers.Default) {
                try {
                    val decodeResult =
                        BitmapUtils.decodeSampledBitmap(mContext, uri, mWidth, mHeight)
                    val rotateResult =
                        BitmapUtils.rotateBitmapByExif(decodeResult.bitmap, mContext, uri)
                    ImageLoadingResult(
                        uri,
                        rotateResult.bitmap,
                        decodeResult.sampleSize,
                        rotateResult.degrees
                    )
                } catch (e: Exception) {
                    ImageLoadingResult(uri, e)
                }
            }

            var completeCalled = false
            if (isActive) {
                val cropImageView = mCropImageViewReference.get()
                if (cropImageView != null) {
                    completeCalled = true
                    cropImageView.onSetImageUriAsyncComplete(result)
                }
            }
            if (!completeCalled && result.bitmap != null) { // fast release of unused bitmap
                result.bitmap.recycle()
            }
        }
    }

}