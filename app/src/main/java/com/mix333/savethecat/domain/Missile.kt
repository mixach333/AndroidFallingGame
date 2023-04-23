package com.mix333.savethecat.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.mix333.savethecat.R
import com.mix333.savethecat.ui.GameView
import java.util.*

class Missile(context: Context) {
    var missile = ArrayList<Bitmap>(12)
    var missileFrame = 0
    var missileX = 0
    var missileY = 0
    var missileVelocity = 0

    init {
        with(context.resources) {
            val m1 = BitmapFactory.decodeResource(this, R.drawable.missile1_1)
            val m2 = BitmapFactory.decodeResource(this, R.drawable.missile2_1)
            val m3 = BitmapFactory.decodeResource(this, R.drawable.missile3_3)
            val m4 = BitmapFactory.decodeResource(this, R.drawable.missile4_4)
            missile.add(Bitmap.createScaledBitmap(m1, 150, 225, false))
            missile.add(Bitmap.createScaledBitmap(m2, 150, 225, false))
            missile.add(Bitmap.createScaledBitmap(m3, 150, 225, false))
            missile.add(Bitmap.createScaledBitmap(m4, 150, 225, false))
            resetPosition()
        }
    }

    fun resetPosition() {
        missileX = Random().nextInt(GameView.dWidth - getMissileWidth())
        missileY =  Random().nextInt(500) * -1
        missileVelocity = 30 + Random().nextInt(15)
    }

    fun getMissile(missileFrame: Int): Bitmap = missile[missileFrame]
    fun getMissileWidth(): Int = missile[0].width
    fun getMissileHeight(): Int = missile[0].height


}