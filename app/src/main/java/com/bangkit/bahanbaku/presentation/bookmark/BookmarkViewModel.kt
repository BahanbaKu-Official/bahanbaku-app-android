package com.bangkit.bahanbaku.presentation.bookmark

import androidx.lifecycle.ViewModel
import com.bangkit.bahanbaku.core.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {
//    fun getBookmarks(token: String) =
//        LiveDataReactiveStreams.fromPublisher(profileUseCase.getBookmarks(token))

    fun getToken() = profileUseCase.getToken()

//    fun deleteBookmarks(token: String, id: Int) =
//        LiveDataReactiveStreams.fromPublisher(profileUseCase.deleteBookmarkByPosition(token, id))
}