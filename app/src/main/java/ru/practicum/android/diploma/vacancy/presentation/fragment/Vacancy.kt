package ru.practicum.android.diploma.vacancy.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.search.domain.models.Phone
import ru.practicum.android.diploma.search.domain.models.Salary
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.DefaultFragment
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetailsScreenState
import ru.practicum.android.diploma.vacancy.presentation.view_model.VacancyDetailsViewModel
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
            paramVacancyId = it.getInt(ARG_VACANCY).toString()
        }

    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vacancyDetailsViewModel.screenState.collect {
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

            is VacancyDetailsScreenState.Content -> showContent(
                state.foundVacancy,
                state.favoriteStatus
            )

            is VacancyDetailsScreenState.Error -> showError()

            is VacancyDetailsScreenState.SimilarVacanciesButtonState -> changeButtonVisibility(state.visibility)
        }
    }

    override fun setUiListeners() {
        with(binding) {
            navigationBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            /*ibShare.setOnClickListener {
                if (paramVacancyId != null) {
                    vacancyDetailsViewModel.shareVacancy(vacancy?.logoUrl)
                }
            }*/

            ibFavorite.setOnClickListener {
                val vacancy = vacancyDetailsViewModel.currentVacancy.value
                if (vacancy != null) {
                    vacancyDetailsViewModel.onFavoriteClicked(vacancy)
                }
            }

            /*tvPhoneNumberValue.setOnClickListener {
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
            ibFavorite.isClickable = false
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
            ibFavorite.isClickable = false
            tvVacancyName.isVisible = false
            tvSalary.isVisible = false
            EmployerCard.isVisible = false
            VacancyDetails.isVisible = false
        }
    }

    private fun showContent(vacancy: Vacancy?, isFavotite: Boolean) {
        binding.apply {
            ivPlaceholderServerError.isVisible = false
            tvPlaceholderServerError.isVisible = false
            ProgressBar.isVisible = false
            ibFavorite.isClickable = true
            tvVacancyName.isVisible = true
            tvSalary.isVisible = true
            EmployerCard.isVisible = true
            VacancyDetails.isVisible = true
            fillVacancyDetails(vacancy)

            if (isFavotite) ibFavorite.setImageResource(R.drawable.ic_vacancy_favorite_red)
            else ibFavorite.setImageResource(R.drawable.ic_vacancy_favorite)
        }
    }

    private fun fillVacancyDetails(vacancy: Vacancy?) {

        binding.apply {

            tvVacancyName.text = vacancy?.vacancyName

            tvSalary.text = formSalary(vacancy?.salary)

            val roundCorners =
                RoundedCorners(requireContext().resources.getDimensionPixelSize(R.dimen.corners_radius_art_work_vacancy))
            val options = RequestOptions().transform(CenterCrop(), roundCorners)

            Glide.with(requireContext())
                .load(vacancy?.logoUrl)
                .placeholder(R.drawable.placeholder_employer_logo)
                .apply(options)
                .into(ivEmployerLogo)

            tvEmployerName.text = vacancy?.companyName

            tvCity.text = if (vacancy?.address.isNullOrEmpty()) vacancy?.city else vacancy?.address

            tvRequiredExperienceValue.text = vacancy?.experience

            val employmentAndSchedule =
                StringBuilder().append(vacancy?.employment).append(", ").append(vacancy?.schedule)
                    .toString()
            tvEmploymentAndShedule.text = employmentAndSchedule

            tvVacancyDescriptionValue.text =
                vacancy?.description?.let {
                    HtmlCompat.fromHtml(
                        vacancy.description.addSpacesBetweenParagraphs(),
                        FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM
                    )
                }

            if (vacancy?.keySkills.isNullOrEmpty()) {
                tvKeySkills.isVisible = false
                tvKeySkillsValue.isVisible = false
            } else {
                var keySkills = ""
                vacancy?.keySkills?.forEach { keySkill ->
                    keySkills += "•  ${keySkill}\n"
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

            if (vacancy?.contacts?.phones?.isNotEmpty() == true) {
                tvPhoneNumberValue.text = createPhones(vacancy.contacts.phones).joinToString("\n")
                tvPhoneNumber.isVisible = true
                tvPhoneNumberValue.isVisible = true
            }

            if (vacancy?.comment.isNullOrEmpty()) {
                tvComment.isVisible = false
                tvCommentValue.isVisible = false
            } else {
                tvCommentValue.text = vacancy?.comment
            }

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

    private fun String.addSpacesBetweenParagraphs(): String {
        return this.replace(Regex("<li>\\s<p>|<li>"), "<li>\u00A0")
    }

    private fun createPhones(phones: List<Phone?>?): List<String> {
        if (phones == null) return emptyList()
        val phoneList = mutableListOf<String>()
        repeat(phones.size) { phone ->
            val contactComment = phones.getOrNull(0)?.comment ?: ""
            val number: String = "+" + phones[phone]?.country +
                    " (${phones[phone]?.city}) " + phones[phone]?.number + "\n" + contactComment
            phoneList.add(phone, number)
        }
        return phoneList
    }

    private fun changeButtonVisibility(visibility: Boolean) {
        binding.tvSimilarVacanciesButton.isVisible = visibility
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