package com.johnmagdalinos.android.shopandcook2

import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors {
    companion object {
        private var appExecutors: AppExecutors? = null
    }

    private var diskIO: Executor? = null
    private var networkIO: Executor? = null
    private var mainThread: Executor? = null

    private constructor(diskIO: Executor, networkIO: Executor, mainThread: Executor) {
        this.diskIO = diskIO
        this.networkIO = networkIO
        this.mainThread = mainThread
    }

    fun getInstance(): AppExecutors? {
        appExecutors = AppExecutors(Executors.newSingleThreadExecutor(),
                Executors.newFixedThreadPool(3),
                MainThreadExecutor())

        return appExecutors
    }

    fun diskIO(): Executor = this.diskIO!!
    fun networkIO(): Executor = this.networkIO!!
    fun mainThread(): Executor = this.mainThread!!

    private inner class MainThreadExecutor: Executor {
        private var mainThreadHandler: android.os.Handler = android.os.Handler(Looper.getMainLooper())

        override fun execute(command: Runnable?) {
            mainThreadHandler.post(command)
        }

    }
}