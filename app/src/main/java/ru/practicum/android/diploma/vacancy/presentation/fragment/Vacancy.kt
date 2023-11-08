package ru.practicum.android.diploma.vacancy.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.filter.presentation.util.ARG_COUNTRY_ID
import ru.practicum.android.diploma.search.domain.models.Contacts
import ru.practicum.android.diploma.search.domain.models.Phone
import ru.practicum.android.diploma.search.domain.models.Salary
import ru.practicum.android.diploma.util.DefaultFragment
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetailsScreenState
import ru.practicum.android.diploma.vacancy.presentation.view_model.VacancyDetailsViewModel
import ru.practicum.android.diploma.search.domain.models.Vacancy
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_VACANCY = "vacancy_model"

/**
 * A simple [Fragment] subclass.
 * Use the [Vacancy.newInstance] factory method to
 * create an instance of this fragment.
 */

class Vacancy : DefaultFragment<FragmentVacancyBinding>() {

    private val vacancyDetailsViewModel by viewModel<VacancyDetailsViewModel>()

    private var vacancyId: String? = null
    private var vacancy: Vacancy? = null
    private var paramVacancyId: String? = null
    private var isClickAllowed = true

    override fun bindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentVacancyBinding {
        return FragmentVacancyBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            paramVacancyId = it.getString(ARG_VACANCY)
        }

    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vacancyDetailsViewModel.screenState.collect {
                    Log.d("VAC state", it.toString())
                    updateUI(it)

                }
            }
        }

        if (paramVacancyId != null) {
            paramVacancyId?.let { id -> vacancyDetailsViewModel.getVacancyDetails(id) }
        }

        setUiListeners()

    }

    private fun updateUI(state: VacancyDetailsScreenState) {
        when (state) {
            is VacancyDetailsScreenState.Loading -> showLoading()
            is VacancyDetailsScreenState.Content -> showContent(state.foundVacancy)
            is VacancyDetailsScreenState.Error -> showError()
        }
    }

    override fun setUiListeners() {
        with(binding) {
            navigationBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            /*ibShare.setOnClickListener {
                if (vacancy != null) {
                    vacancyDetailsViewModel.shareVacancy(vacancy?.logoUrl)
                }
            }

            ibFavorite.setOnClickListener {
                if (vacancy != null) {
                    vacancyDetailsViewModel.onFavoriteClicked(vacancy)
                }
            }

            tvPhoneNumberValue.setOnClickListener {
                if (clickDebounce() && vacancy?.contacts?.phones != null)
                    vacancyDetailsViewModel.makeCall(tvPhoneNumberValue.text.toString())
            }

            tvContactEmailValue.setOnClickListener {
                if (clickDebounce() && vacancy?.contacts?.email != null)
                    vacancyDetailsViewModel.openEmail(vacancy?.contacts?.email)
            }*/

            tvSimilarVacanciesButton.setOnClickListener {
                findNavController().navigate(R.id.action_to_similar, Bundle().apply {
                    putString(
                        ARG_VACANCY, paramVacancyId
                    )
                })
            }
        }
    }

    override fun exitExtraWhenSystemBackPushed() {
        findNavController().popBackStack()
    }


    private fun showLoading() {
        binding.apply {
            ProgressBar.isVisible = true
            ivPlaceholderServerError.isVisible = false
            tvPlaceholderServerError.isVisible = false
            tvVacancyName.isVisible = false
            tvSalary.isVisible = false
            EmployerCard.isVisible = false
            VacancyDetails.isVisible = false
        }
    }

    private fun showError() {
        binding.apply {
            ivPlaceholderServerError.isVisible = true
            tvPlaceholderServerError.isVisible = true
            ProgressBar.isVisible = false
            tvVacancyName.isVisible = false
            tvSalary.isVisible = false
            EmployerCard.isVisible = false
            VacancyDetails.isVisible = false
        }
    }

    private fun showContent(vacancy: Vacancy?) {
        binding.apply {
            ivPlaceholderServerError.isVisible = false
            tvPlaceholderServerError.isVisible = false
            ProgressBar.isVisible = false
            tvVacancyName.isVisible = true
            tvSalary.isVisible = true
            EmployerCard.isVisible = true
            VacancyDetails.isVisible = true
            fillVacancyDetails(vacancy)
        }
    }

    private fun fillVacancyDetails(vacancy: Vacancy?) {

        Log.d("VAC logo url", vacancy?.logoUrl.toString())

        binding.apply {

            tvVacancyName.text = vacancy?.vacancyName

            tvSalary.text = formSalary(vacancy?.salary)

            Glide.with(requireContext())
                .load(vacancy?.logoUrl)
                .placeholder(R.drawable.placeholder_employer_logo)
                .centerCrop()
                .transform(RoundedCorners(R.dimen.corners_radius_art_work_vacancy))
                .into(ivEmployerLogo)

            tvEmployerName.text = vacancy?.companyName

            tvCity.text = vacancy?.city

            tvRequiredExperienceValue.text = vacancy?.experience

            tvEmploymentAndShedule.text = vacancy?.employment

            tvVacancyDescriptionValue.text =
                vacancy?.description?.let {
                    HtmlCompat.fromHtml(
                        it,
                        FROM_HTML_MODE_COMPACT
                    )
                }

            if (vacancy?.keySkills.isNullOrEmpty()) {
                tvKeySkills.isVisible = false
                tvKeySkillsValue.isVisible = false
            } else {
                var keySkills = ""
                vacancy?.keySkills?.forEach { keySkill ->
                    keySkills += "• ${keySkill}\n"
                }
                tvKeySkillsValue.text = keySkills
            }

            if (
                vacancy?.contacts?.name?.isNotEmpty() == true ||
                vacancy?.contacts?.email?.isNotEmpty() == true ||
                vacancy?.contacts?.phones?.toString()?.isNotEmpty() == true
            ) {
                ContactsContainer.isVisible = true
            }
            if (vacancy?.contacts?.name?.isNotEmpty() == true) {
                tvContactPersonValue.text = vacancy.contacts.name
                tvContactPerson.isVisible = true
                tvContactPersonValue.isVisible = true
            }
            if (vacancy?.contacts?.email?.isNotEmpty() == true) {
                tvContactEmailValue.text = vacancy.contacts.email
                tvContactEmail.isVisible = true
                tvContactEmailValue.isVisible = true
            }

            tvCommentValue.text = vacancy?.comment

        }
    }

    private fun formSalary(salary: Salary?): String {
        salary ?: return requireContext().getString(R.string.salary_not_indicated)

        return when {
            salary.from != null && salary.to != null && salary.currency != null ->
                requireContext().getString(
                    R.string.salary_from_to,
                    formatNumberWithSpaces(salary.from),
                    formatNumberWithSpaces(salary.to),
                    getCurrencySymbol(salary.currency)
                )

            salary.from != null && salary.currency != null ->
                requireContext().getString(
                    R.string.salary_from,
                    formatNumberWithSpaces(salary.from),
                    getCurrencySymbol(salary.currency)
                )

            salary.to != null && salary.currency != null ->
                requireContext().getString(
                    R.string.salary_to,
                    formatNumberWithSpaces(salary.to),
                    getCurrencySymbol(salary.currency)
                )

            else -> requireContext().getString(R.string.salary_not_indicated)
        }
    }

    private fun formatNumberWithSpaces(number: Int): String {
        val formatter = NumberFormat.getNumberInstance(Locale.FRENCH)
        return formatter.format(number)
    }

    private fun getCurrencySymbol(currencyCode: String): String {
        return if (currencyCode == "RUR" || currencyCode == "RUB") {
            "₽"
        } else {
            try {
                Currency.getInstance(currencyCode).symbol
            } catch (e: IllegalArgumentException) {
                currencyCode
            }
        }
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }

    private fun changeFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.ibFavorite.setImageResource(R.color.red)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param vacancyModel Vacancy model
         * @return A new instance of fragment Vacancy.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(vacancyId: String) =
            Vacancy().apply {
                arguments = Bundle().apply {
                    putString(ARG_VACANCY, vacancyId)
                }
            }

        const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}