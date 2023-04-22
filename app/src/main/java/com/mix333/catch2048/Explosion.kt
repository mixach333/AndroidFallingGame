package com.mix333.catch2048

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Explosion(context: Context) {
    val explosion = ArrayList<Bitmap>()
    var explosionFrame = 0
    var explosionX = 0
    var explosionY = 0

    init {
        val options = BitmapFactory.Options()
        options.outWidth = 75
        options.outHeight = 75
        explosion.add(BitmapFactory.decodeResource(context.resources, R.drawable.confettie, options))
        explosion.add(BitmapFactory.decodeResource(context.resources, R.drawable.confettie, options))
        explosion.add(BitmapFactory.decodeResource(context.resources, R.drawable.confettie, options))
        explosion.add(BitmapFactory.decodeResource(context.resources, R.drawable.confettie, options))
    }

    fun getExplosion(explosionFrame: Int) = explosion[explosionFrame]
}