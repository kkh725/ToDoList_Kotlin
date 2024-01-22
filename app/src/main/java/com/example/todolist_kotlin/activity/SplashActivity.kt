package com.example.todolist_kotlin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.todolist_kotlin.R
import com.example.todolist_kotlin.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * delay handler
         */

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            /**
             * postdelayed는 1500초가 지난 이후에 수행될 내용이다.
             * 그저 ui 에서 진행될 사항을 예약하는정도의 메서드이다.
             */
            binding.view.setBackgroundResource(R.drawable.gradient)

            //1.5초 뒤에 진행
        },1500)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            /**
             * postdelayed는 1500초가 지난 이후에 수행될 내용이다.
             * 그저 ui 에서 진행될 사항을 예약하는정도의 메서드이다.
             */
            val intent = Intent(this, ListMainActivity::class.java)
            startActivity(intent)
            finish();
            //1.5초 뒤에 진행
        },3000)
    }
}