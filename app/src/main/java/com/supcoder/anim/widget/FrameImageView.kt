package com.supcoder.anim.widget

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.ImageView
import com.supcoder.anim.R

/**
 * FrameImageView
 *
 * @author lee
 * @date 2017/12/1
 */
class FrameImageView(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {


    /**
     * 播放的资源集合
     */
    private var animationDrawable: AnimationDrawable? = null
    /**
     * 是否只播放一次
     */
    private var isOneShot = false


    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.FrameImageView)
        val resId = array.getResourceId(R.styleable.FrameImageView_anim, 0)
        animationDrawable = ContextCompat.getDrawable(context, resId) as AnimationDrawable
        isOneShot = array.getBoolean(R.styleable.FrameImageView_is_one_shot, false)
        array.recycle()
        initAnimation()
    }


    private fun initAnimation(){
        if (animationDrawable != null) {
            setImageDrawable(animationDrawable)
            animationDrawable!!.isOneShot = isOneShot
        }
    }


    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (hasWindowFocus) {
            if (animationDrawable != null) {
                if (!animationDrawable!!.isRunning) {
                    animationDrawable!!.start()
                }
            }
        }
    }

    fun getCurrentDrawable(): Drawable {
        return if (animationDrawable != null) {
            animationDrawable!!.current
        } else {
            throw IllegalStateException("请检查xml中是否设置了帧动画！")
        }
    }


    fun getIndexDrawable(index: Int): Drawable {
        return if (animationDrawable != null) {
            animationDrawable!!.getFrame(index)
        } else {
            throw IllegalStateException("请检查xml中是否设置了帧动画！")
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (animationDrawable != null && animationDrawable!!.isRunning) {
            animationDrawable!!.stop()
        }
    }
}