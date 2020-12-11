package id.teachly.ui.base.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.chip.Chip
import id.teachly.R
import id.teachly.databinding.FragmentProfileBinding
import id.teachly.repo.remote.firebase.auth.Auth
import id.teachly.repo.remote.firebase.firestore.FirestoreCategory
import id.teachly.repo.remote.firebase.firestore.FirestoreUser
import id.teachly.ui.welcome.WelcomeActivity
import id.teachly.utils.Helpers

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val model: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        binding.apply {
            FirestoreUser.getUserById(Auth.getUserId() ?: "") {
                ivAva.load(it.img) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }

                tvName.text = it.fullName
                tvUsername.text = buildString { append("@").append(it.username) }

                FirestoreCategory.getCategoryByName(it.interest ?: listOf()) { category ->
                    category.forEach {
                        chipGroup.addView(Chip(chipGroup.context).apply {
                            text = it.name
                            model.loadImage(it.img ?: "", requireContext()) { img ->
                                chipIcon = img
                            }
                        })
                    }
                }
            }

            btnLogout.setOnClickListener {
                Helpers.showLoadingDialog(requireContext())
                Auth.logout()
                startActivity(Intent(requireContext(), WelcomeActivity::class.java))
                requireActivity().finishAffinity()
            }
        }

    }

}