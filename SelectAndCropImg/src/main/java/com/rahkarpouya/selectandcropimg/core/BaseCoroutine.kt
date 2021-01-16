package com.rahkarpouya.selectandcropimg.core

import android.util.Log
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class BaseCoroutine : CoroutineScope {

    private val parentJob = Job()
    private val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            GlobalScope.launch {
                throwable.message?.apply {
                    Log.i("Response_Error", this)
                }
            }
        }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + parentJob + coroutineExceptionHandler

}