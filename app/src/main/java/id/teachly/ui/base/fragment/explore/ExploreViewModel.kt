package id.teachly.ui.base.fragment.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.teachly.data.Category
import id.teachly.repo.remote.firebase.auth.Auth
import id.teachly.repo.remote.firebase.firestore.FirestoreCategory
import id.teachly.repo.remote.firebase.firestore.FirestoreUser

class ExploreViewModel : ViewModel() {

    private val _favoriteCategory = MutableLiveData<List<Category>>()
    private val _allCategory = MutableLiveData<List<Category>>()

    fun getFavoriteCategories() {
        getUserInterest {
            FirestoreCategory.getCategoryByName(it) { favorite ->
                _favoriteCategory.value = favorite
            }
        }
    }

    fun getAllCategories() {
        getUserInterest {
            FirestoreCategory.getNotFavoriteCategory(it) { all ->
                _allCategory.value = all
            }
        }
    }

    private fun getUserInterest(onResult: (interest: List<String>) -> Unit) {
        FirestoreUser.getUserById(Auth.getUserId() ?: "") { onResult(it.interest ?: listOf()) }
    }

    val favoriteCategory: LiveData<List<Category>> = _favoriteCategory
    val allCategory: LiveData<List<Category>> = _allCategory
}