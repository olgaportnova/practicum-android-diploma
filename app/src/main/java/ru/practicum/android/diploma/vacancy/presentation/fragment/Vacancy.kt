package ru.practicum.android.diploma.vacancy.presentation.fragment

import android.os.Bundle
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
import ru.practicum.android.diploma.search.domain.models.Contacts
import ru.practicum.android.diploma.search.domain.models.Phone
import ru.practicum.android.diploma.search.domain.models.Salary
import ru.practicum.android.diploma.util.DefaultFragment
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetailsScreenState
import ru.practicum.android.diploma.vacancy.presentation.view_model.VacancyDetailsViewModel
import ru.practicum.android.diploma.search.domain.models.Vacancy


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_VACANCY = "vacancy_id"

/**
 * A simple [Fragment] subclass.
 * Use the [Vacancy.newInstance] factory method to
 * create an instance of this fragment.
 */

class Vacancy : DefaultFragment<FragmentVacancyBinding>() {

    private val vacancyDetailsViewModel by viewModel<VacancyDetailsViewModel>()

    //private var vacancyModel: String? = null
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vacancyDetailsViewModel.screenState.collect {
                    updateUI(it)
                }
            }
        }*/

        /*if (paramVacancyId != null) {
            paramVacancyId?.let { id -> vacancyDetailsViewModel.getVacancyDetails(id) }
        }*/

        vacancyId = "85455501"

        vacancyDetailsViewModel.getVacancyDetails(vacancyId!!)

        /*vacancy = ru.practicum.android.diploma.search.domain.models.Vacancy(
            id = 111,
            vacancyName = "developer",
            companyName = "Yandex",
            logoUrl = "",
            city = "SPb",
            employment = "00.00-00.00",
            experience = "100",
            salary = Salary("Rub", 100, false, 500),
            description = "qeq3e3eqeqeqqqadaddadwdde",
            keySkills = mutableListOf("svfgrdhhhdh"),
            contacts = Contacts("szgssgsgg","dhdhhhdfh", Phone("182", "sss", "7", "000000")),
            comment = "comment"
        )*/

        fillVacancyDetails(vacancy)

        setUiListeners()

    }

    private fun updateUI(state: VacancyDetailsScreenState) {
        when (state) {
            is VacancyDetailsScreenState.Loading -> showLoading()
            is VacancyDetailsScreenState.Content -> showContent(state.foundVacancy)
            is VacancyDetailsScreenState.Error -> showError(state.errorMessage)

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
                findNavController().navigate(R.id.action_to_similar)
            }
        }
    }

    override fun exitExtraWhenSystemBackPushed() {
        findNavController().popBackStack()
    }


    private fun showLoading() {
        binding.apply {
            ProgressBar.isVisible = true
            tvPlaceholderServerError.isVisible = false
            ivPlaceholderServerError.isVisible = false
            tvVacancyName.isVisible = false
            tvSalary.isVisible = false
            EmployerCard.isVisible = false
            VacancyDetails.isVisible = false
        }
    }


    private fun showError(errorMessage: String) {
        binding.apply {
            tvPlaceholderServerError.isVisible = true
            ivPlaceholderServerError.isVisible = true
            ProgressBar.isVisible = false
            tvPlaceholderServerError.isVisible = false
            ivPlaceholderServerError.isVisible = false
            tvVacancyName.isVisible = false
            tvSalary.isVisible = false
            EmployerCard.isVisible = false
            VacancyDetails.isVisible = false
        }

    }


    private fun showContent(vacancy: Vacancy?) {
        binding.apply {
            tvPlaceholderServerError.isVisible = false
            ivPlaceholderServerError.isVisible = false
            ProgressBar.isVisible = false
            fillVacancyDetails(vacancy)
        }
    }

    private fun fillVacancyDetails(vacancy: Vacancy?) {

        binding.apply {

            tvVacancyName.text = vacancy?.vacancyName

            tvSalary.text = vacancy?.salary.toString()

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
                tvKeySkillsValue.isVisible = false
            } else {
                tvKeySkillsValue.text = vacancy?.keySkills.toString()
            }

            if (vacancy?.contacts == null) {
                ContactsContainer.isVisible = false
            } else {
                tvContactPersonValue.text = vacancy.contacts.name

                tvContactEmailValue.text = vacancy.contacts.email

                tvPhoneNumberValue.text = vacancy.contacts.phones?.number

            }

            tvCommentValue.text = vacancy?.comment

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