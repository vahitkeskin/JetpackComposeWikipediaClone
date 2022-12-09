package com.vahitkeskin.jetpackcomposewikipediaclone

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.FlipperNetworkObject
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WikipediaApp: Application() {

    override fun onCreate() {
        super.onCreate()
        //Facebook Flipper
        SoLoader.init(this, false) //TODO Without this it will never work
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            val clint: FlipperClient = AndroidFlipperClient.getInstance(this)
            val networkFlipperPlugin = NetworkFlipperPlugin()
            FlipperNetworkObject.networkFlipperPlugin = networkFlipperPlugin
            clint.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            clint.addPlugin(networkFlipperPlugin)
            clint.start()
        }
    }

}