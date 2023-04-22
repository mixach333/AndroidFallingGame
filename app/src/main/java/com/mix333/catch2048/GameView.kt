package com.mix333.catch2048

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.Display
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import kotlinx.coroutines.*
import java.util.*


class GameView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    companion object {
        var dWidth = 0
        var dHeight = 0
    }

    private var background: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.background)
    private var ground: Bitmap
    private var rabbit: Bitmap
    private var rectGround: Rect
    private var rectBackground: Rect
    private val UPDATE_MILLIS: Long = 30
    private var textPaint = Paint()
    private var healthPaint = Paint()
    private val TEXT_SIZE: Float = 120f
    private var points = 0
    private var life = 0
    private var rabbitX: Float = 0f
    private var rabbitY: Float = 0f
    private var oldX: Float = 0f
    private var oldRabbitX: Float = 0f
    private var spikes: ArrayList<Spike>
    //private var explosions = ArrayList<Explosion>()
    private val r = java.lang.Runnable {
        invalidate()
    }

    @Suppress("DEPRECATION")
    private var thisDisplay: Display?

    private var size: Point = Point()

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            thisDisplay = null
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val metrics = windowManager.maximumWindowMetrics
            dWidth = metrics.bounds.width()
            dHeight = metrics.bounds.height()
        } else {
            thisDisplay = (getContext() as Activity).windowManager.defaultDisplay
            thisDisplay?.getRealSize(size)
            dWidth = size.x
            dHeight = size.y
        }
        rabbit = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(context.resources, R.drawable.d2),
            175,
            175,
            false
        )
        ground = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.platform_ground
            ), dWidth/2, dHeight/5, false
        )
        Log.d("Metrics", "Window metrics are: width = $dWidth, height = $dHeight")
        rectBackground = Rect(0, 0, dWidth, dHeight)
        rectGround = Rect(-100, dHeight - ground.height, dWidth+100, dHeight)
        textPaint.color = Color.rgb(255, 165, 0)
        textPaint.textSize = TEXT_SIZE
        textPaint.textAlign = Paint.Align.LEFT
        //textPaint.typeface = ResourcesCompat.getFont(context, R.font.....)
        healthPaint.color = Color.GREEN
        rabbitX = (dWidth / 2 - (rabbit.width / 2)).toFloat()
        rabbitY = (dHeight - ground.height/1.5 - rabbit.height).toFloat()
        spikes = ArrayList<Spike>()
        for (i in 0 until 11) {
            spikes.add(Spike(context))
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(background, null, rectBackground, null)
        canvas.drawBitmap(ground, null, rectGround, null)
        canvas.drawBitmap(rabbit, rabbitX, rabbitY, null)
        spikes.forEach { spike ->
            canvas.drawBitmap(
                spike.getSpike(spike.spikeFrame),
                spike.spikeX.toFloat(),
                spike.spikeY.toFloat(),
                null
            )
            spike.spikeFrame++
            if (spike.spikeFrame > 2) spike.spikeFrame = 0
            spike.spikeY += spike.spikeVelocity
            if ((spike.spikeY + spike.getSpikeHeight()) >= (dHeight - ground.height*0.75)){ points += 10
            //val explosion = Explosion(context)
//            explosion.explosionX = spike.spikeX
//            explosion.explosionY = spike.spikeY
//            explosions.add(explosion)
            spike.resetPosition()
            }
        }
        spikes.forEach { spike ->
            if (spike.spikeX + spike.getSpikeWidth() >= rabbitX
                && spike.spikeX <= rabbitX + rabbit.width
                && spike.spikeY + spike.getSpikeWidth() >= rabbitY
                && spike.spikeY + spike.getSpikeWidth() <= rabbitY + rabbit.height
            ) {
                life--
                spike.resetPosition()
                if (life == 0) {
                    Toast.makeText(context, "Intent should be called", Toast.LENGTH_LONG).show()
                    val intent = Intent(this.context, GameOver::class.java)
                    intent.putExtra("points", points)
                    context.startActivity(intent)
                    (context as Activity).finish()
                }
            }
        }
//        explosions.forEach {
//            canvas.drawBitmap(
//                it.getExplosion(it.explosionFrame),
//                (it.explosionX).toFloat(),
//                (it.explosionY).toFloat(),
//                null
//            )
//            it.explosionFrame++
//            if (it.explosionFrame > 3) explosions.remove(it)
//        }

        if (life == 2) healthPaint.color = Color.YELLOW
        else if (life == 1) healthPaint.color = Color.RED
        canvas.drawRect(
            (dWidth - 200).toFloat(),
            30.toFloat(),
            (dWidth - 200 + 60 * life).toFloat(),
            80.toFloat(),
            healthPaint
        )
        canvas.drawText("$points", 20.toFloat(), TEXT_SIZE, textPaint)
        handler.postDelayed(r, UPDATE_MILLIS)
    }

    // MotionEvent?
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y
        if (touchY >= rabbitY-rabbit.height*0.5) {
            val action = event.action
            if (action == MotionEvent.ACTION_DOWN) {
                oldX = event.x
                oldRabbitX = rabbitX
            }
            if (action == MotionEvent.ACTION_MOVE) {
                val shift = oldX - touchX
                val newRabbitX = oldRabbitX - shift
                rabbitX = if (newRabbitX <= 0) 0f
                else if (newRabbitX >= dWidth - rabbit.width) (dWidth-rabbit.width).toFloat()
                else newRabbitX
            }
        }
        return true
    }
}
