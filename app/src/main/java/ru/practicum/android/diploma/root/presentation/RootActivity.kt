package ru.practicum.android.diploma.root.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRootBinding

    private var navigateCurrentLevel = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView = binding.rootNavBar

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController


        //navView.setupWithNavController(navController)


        binding.rootNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.search -> {
                    navController.navigate(R.id.search, null, calculateAnimation(0,navigateCurrentLevel))
                    true
                }

                R.id.favourite -> {
                    navController.navigate(R.id.favourite, null, calculateAnimation(1,navigateCurrentLevel))
                    true
                }

                R.id.crew -> {
                    navController.navigate(R.id.crew, null, calculateAnimation(2,navigateCurrentLevel))
                    true
                }

                else -> false
            }
        }


        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.filters, R.id.workPlace, R.id.district, R.id.vacancy, R.id.country, R.id.industry, R.id.similar -> {
                    navView.isVisible = false
                    binding.divider.visibility = View.GONE
                }

                R.id.crew -> {
                    navigateCurrentLevel = 3
                    binding.rootNavBar.menu.findItem(destination.id).isChecked = true
                }
                R.id.favourite -> navigateCurrentLevel = 1
                R.id.search -> navigateCurrentLevel = 0

                else -> {
                    navView.isVisible = true
                    binding.divider.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun calculateAnimation(newDestinationLevel: Int,previousDestinationLevel:Int):NavOptions {
        if(newDestinationLevel>previousDestinationLevel){
            // Листаем слайд справа налево (перелистываем)
            return NavOptions.Builder()
                .setEnterAnim(R.anim.slide_out_right) // анимация для входа во фрагмент
                .setExitAnim(R.anim.slide_in_left) // анимация для выхода из фрагмента
                .setPopEnterAnim(R.anim.slide_out_right) // анимация для входа при возврате назад
                .setPopExitAnim(R.anim.slide_in_left) // анимация для выхода при возврате назад
                .build()
        }
        else{
            return NavOptions.Builder()
                .setEnterAnim(R.anim.slide_out_left) // анимация для входа во фрагмент
                .setExitAnim(R.anim.slide_in_right) // анимация для выхода из фрагмента
                .setPopEnterAnim(R.anim.slide_out_left) // анимация для входа при возврате назад
                .setPopExitAnim(R.anim.slide_in_right) // анимация для выхода при возврате назад
                .build()
        }
    }


}