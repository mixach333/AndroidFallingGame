package com.mix333.catch2048.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.Display
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.mix333.catch2048.R
import com.mix333.catch2048.domain.Missile
import kotlinx.coroutines.*
import java.util.*

private const val UPDATE_MILLIS: Long = 30
private const val TEXT_SIZE: Float = 120f

class GameView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    companion object {
        var dWidth = 0
        var dHeight = 0
    }
    private val background: Bitmap =
        BitmapFactory.decodeResource(context.resources, R.drawable.background)
    private val ground: Bitmap
    private val rabbit: Bitmap
    private val rectGround: Rect
    private val rectBackground: Rect
    private val textPaint = Paint()
    private val healthPaint = Paint()
    private var points = 0
    private var life = 3
    private var catX: Float = 0f
    private var catY: Float = 0f
    private var oldX: Float = 0f
    private var oldCatX: Float = 0f
    private val missiles: ArrayList<Missile>
    private val intentGameOverActivity = Intent(this.context, GameOverActivity::class.java)
    private val invalidateDelay = java.lang.Runnable {
        invalidate()
    }
    private val collisionDetector: java.lang.Runnable
    @Suppress("DEPRECATION")
    private val thisDisplay: Display?
    private val size: Point = Point()


    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            thisDisplay = null
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val metrics = windowManager.maximumWindowMetrics
            dWidth = metrics.bounds.width()
            dHeight = metrics.bounds.height()
        } else {
            @Suppress("DEPRECATION")
            thisDisplay = (getContext() as Activity).windowManager.defaultDisplay
            @Suppress("DEPRECATION")
            thisDisplay?.getRealSize(size)
            dWidth = size.x
            dHeight = size.y
        }
        rabbit = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(context.resources, R.drawable.scared_cat_3),
            dWidth / 8,
            dWidth / 8,
            false
        )
        ground = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.platform_ground
            ), dWidth / 2, dHeight / 5, false
        )
        rectBackground = Rect(0, 0, dWidth, dHeight)
        rectGround = Rect(-100, dHeight - ground.height, dWidth + 100, dHeight)
        textPaint.color = Color.rgb(255, 165, 0)
        textPaint.textSize = TEXT_SIZE
        textPaint.textAlign = Paint.Align.LEFT
        //textPaint.typeface = ResourcesCompat.getFont(context, R.font.....)
        healthPaint.color = Color.GREEN
        catX = (dWidth / 2 - (rabbit.width / 2)).toFloat()
        catY = (dHeight - ground.height / 1.5 - rabbit.height).toFloat()
        missiles = ArrayList<Missile>()
        for (i in 0 until 5) {
            missiles.add(Missile(context))
        }
        collisionDetector = java.lang.Runnable {
            missiles.forEach { spike ->
                if (spike.spikeY + spike.getSpikeHeight() >= catY
                    && spike.spikeX + spike.getSpikeWidth() >= catX
                    && spike.spikeX <= catX + rabbit.width
                ) {
                    life--
                    spike.resetPosition()
                    if (life == 0) {
                        intentGameOverActivity.putExtra("points", points)
                        context.startActivity(intentGameOverActivity)
                        (context as Activity).finish()
                    }
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(background, null, rectBackground, null)
        canvas.drawBitmap(ground, null, rectGround, null)
        canvas.drawBitmap(rabbit, catX, catY, null)
        drawSpikes(canvas)
        handler.postDelayed(collisionDetector, UPDATE_MILLIS)
        drawLifeAndScore(canvas)
        handler.postDelayed(invalidateDelay, UPDATE_MILLIS)
    }

    // MotionEvent?
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y
        if (touchY >= catY - rabbit.height * 0.5) {
            val action = event.action
            if (action == MotionEvent.ACTION_DOWN) {
                oldX = event.x
                oldCatX = catX
            }
            if (action == MotionEvent.ACTION_MOVE) {
                val shift = oldX - touchX
                val newRabbitX = oldCatX - shift
                catX = if (newRabbitX <= 0) 0f
                else if (newRabbitX >= dWidth - rabbit.width) (dWidth - rabbit.width).toFloat()
                else newRabbitX
            }
        }
        return true
    }

    private fun drawSpikes(canvas: Canvas) {
        missiles.forEach { spike ->
            canvas.drawBitmap(
                spike.getSpike(spike.spikeFrame),
                spike.spikeX.toFloat(),
                spike.spikeY.toFloat(),
                null
            )
            spike.spikeFrame++
            if (spike.spikeFrame > 2) spike.spikeFrame = 0
            spike.spikeY += spike.spikeVelocity
            if ((spike.spikeY + spike.getSpikeHeight()) >= (dHeight - ground.height * 0.75)) {
                points += 10
                spike.resetPosition()
            }
        }
    }

    private fun drawLifeAndScore(canvas: Canvas){
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
    }
}
