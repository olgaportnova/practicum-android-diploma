package ru.practicum.android.diploma.vacancy.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.filter.presentation.sharedviewmodel.FilterSharedVm
import ru.practicum.android.diploma.search.domain.models.Phone
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.DefaultFragment
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetailsScreenState
import ru.practicum.android.diploma.vacancy.presentation.view_model.VacancyDetailsViewModel
import ru.practicum.android.diploma.util.SalaryUtil
import ru.practicum.android.diploma.vacancy.presentation.PhonesAdapter

class Vacancy : DefaultFragment<FragmentVacancyBinding>() {
    private val sharedVm:FilterSharedVm by activityViewModels()

    private val vacancyDetailsViewModel by viewModel<VacancyDetailsViewModel>()
    private var paramVacancyId: String? = null
    private var isClickAllowed = true
    private var phonesAdapter: PhonesAdapter? = null
    lateinit var onItemClickDebounce: (Phone) -> Unit

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
            is VacancyDetailsScreenState.Content -> showContent(state.foundVacancy, state.favoriteStatus)
            is VacancyDetailsScreenState.Error -> showError()
            is VacancyDetailsScreenState.SimilarVacanciesButtonState -> changeButtonVisibility(state.visibility)
        }
    }

    override fun setUiListeners() {
        with(binding) {
            navigationBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            ibShare.setOnClickListener {
                val vacancy = vacancyDetailsViewModel.currentVacancy.value
                if (clickDebounce() && vacancy?.alternateUrl != null)
                    vacancyDetailsViewModel.shareVacancy(vacancy.alternateUrl)
            }

            ibFavorite.setOnClickListener {
                val vacancy = vacancyDetailsViewModel.currentVacancy.value
                if (vacancy != null) {
                    vacancyDetailsViewModel.onFavoriteClicked(vacancy)
                }
            }

            tvContactEmailValue.setOnClickListener {
                val vacancy = vacancyDetailsViewModel.currentVacancy.value
                if (clickDebounce() && vacancy?.contacts?.email != null)
                    vacancyDetailsViewModel.openEmail(vacancy.contacts.email)
            }

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

            sharedVm.updateFavourite.value = "update"
        }
    }

    private fun fillVacancyDetails(vacancy: Vacancy?) {

        binding.apply {

            tvVacancyName.text = vacancy?.vacancyName

            tvSalary.text = SalaryUtil.formSalary(vacancy?.salary, requireContext())

            val roundCorners = RoundedCorners(requireContext().resources.getDimensionPixelSize(R.dimen.corners_radius_art_work_vacancy))
            val options = RequestOptions().transform(CenterInside(), roundCorners)

            Glide.with(requireContext())
                .load(vacancy?.logoUrl)
                .placeholder(R.drawable.logo_placeholder)
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
                tvPhoneNumber.isVisible = true
                if (clickDebounce()) {
                    setPhonesAdapter(vacancy)
                }
            }

            if (vacancy?.comment?.isNotEmpty() == true) {
                tvCommentValue.text = vacancy.comment
                tvComment.isVisible = true
                tvCommentValue.isVisible = true
            }
        }
    }

    private fun String.addSpacesBetweenParagraphs(): String {
        return this.replace(Regex("<li>\\s<p>|<li>"), "<li>\u00A0")
    }

    private fun changeButtonVisibility(visibility: Boolean) {
        binding.tvSimilarVacanciesButton.isVisible = visibility
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY_MILLIS)
                isClickAllowed = true
            }
        }
        return current
    }

    private fun setPhonesAdapter(vacancy: Vacancy) {
        phonesAdapter = vacancy.contacts?.phones?.let {
            PhonesAdapter(it) { phone -> onItemClickDebounce(phone) }
        }

        onItemClickDebounce = { phone -> vacancyDetailsViewModel.makeCall(phone) }

        binding.phonesList.adapter = phonesAdapter
    }

    companion object {
        fun newInstance(vacancyId: String) =
            Vacancy().apply {
                arguments = Bundle().apply {
                    putString(ARG_VACANCY, vacancyId)
                }
            }

        const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
        const val ARG_VACANCY = "vacancy_model"
    }
}