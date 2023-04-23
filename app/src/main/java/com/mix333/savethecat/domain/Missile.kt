package com.mix333.savethecat.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.mix333.savethecat.R

import com.mix333.savethecat.ui.GameView
import java.util.*

class Missile(context: Context) {
    var spike = ArrayList<Bitmap>(12)
    var spikeFrame = 0
    var spikeX = 0
    var spikeY = 0
    var spikeVelocity = 0

    init {
        with(context.resources) {
            val m1 = BitmapFactory.decodeResource(this, R.drawable.missile1_1)
            val m2 = BitmapFactory.decodeResource(this, R.drawable.missile2_1)
            val m3 = BitmapFactory.decodeResource(this, R.drawable.missile3_3)
            val m4 = BitmapFactory.decodeResource(this, R.drawable.missile4_4)
            spike.add(Bitmap.createScaledBitmap(m1, 150, 225, false))
            spike.add(Bitmap.createScaledBitmap(m2, 150, 225, false))
            spike.add(Bitmap.createScaledBitmap(m3, 150, 225, false))
            spike.add(Bitmap.createScaledBitmap(m4, 150, 225, false))
            resetPosition()
        }
    }

    fun resetPosition() {
        spikeX = Random().nextInt(GameView.dWidth - getSpikeWidth())
        spikeY =  Random().nextInt(600) * -1
        spikeVelocity = 30 + Random().nextInt(10)
    }

    fun getSpike(spikeFrame: Int): Bitmap = spike[spikeFrame]
    fun getSpikeWidth(): Int = spike[0].width
    fun getSpikeHeight(): Int = spike[0].height


}