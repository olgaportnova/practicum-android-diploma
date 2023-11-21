package ru.practicum.android.diploma.root.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import ru.practicum.android.diploma.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val imageView = findViewById<ImageView>(R.id.splashImageView)
        val pulseAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.pulse)

        imageView.startAnimation(pulseAnimation)

        val delayMillis = 3000
        Handler().postDelayed({
            val intent = Intent(this, RootActivity::class.java)
            startActivity(intent)
            finish()
        }, delayMillis.toLong())
    }
}
