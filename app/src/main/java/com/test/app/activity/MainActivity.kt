package com.test.app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import test.ToDoList_app.R
import test.ToDoList_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.root.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                toggleVisibility(p0!!)
            }

            override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {
                binding.view4.visibility = View.VISIBLE
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {

            }

        })



    }
    private fun toggleVisibility(motionLayout: MotionLayout) {
        if (isExpanded) {
            // 현재 상태가 확장된 상태일 때의 처리
            motionLayout.transitionToStart()
            motionLayout.setBackgroundResource(R.color.white)

        } else {
            // 현재 상태가 축소된 상태일 때의 처리
            motionLayout.transitionToEnd()
            motionLayout.setBackgroundResource(R.color.black_semi_transparent)

        }
        // isExpanded 값을 반전시킴
        isExpanded = !isExpanded
    }
}