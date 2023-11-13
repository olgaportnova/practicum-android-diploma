package ru.practicum.android.diploma.vacancy.data.impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.practicum.android.diploma.vacancy.domain.repository.ExternalNavigator

class ExternalNavigatorImpl(private val context: Context) : ExternalNavigator {

    override fun shareVacancy(vacancyUrl: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, vacancyUrl)
        }
        context.startActivity(shareIntent)
    }

    override fun openEmail(email: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        }
        context.startActivity(emailIntent)
    }

    override fun makeCall(phone: String) {
        val callIntent = Intent(Intent.ACTION_DIAL).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.parse("tel:$phone")
        }
        context.startActivity(callIntent)
    }

}