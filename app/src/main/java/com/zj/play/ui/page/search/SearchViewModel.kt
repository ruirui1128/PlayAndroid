package com.zj.play.ui.page.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zj.play.logic.repository.BaseArticlePagingRepository
import com.zj.play.logic.viewmodel.BaseArticleViewModel
import com.zj.model.HotkeyModel
import com.zj.model.PlayState
import com.zj.model.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * 版权：Zhujiang 个人版权
 * @author zhujiang
 * 版本：1.5
 * 创建日期：2021/5/17
 * 描述：PlayAndroid
 *
 */
class SearchViewModel(application: Application) : BaseArticleViewModel(application) {

    private var hotkeyJob: Job? = null

    override val repositoryArticle: BaseArticlePagingRepository
        get() = SearchRepository()
        
   private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        _hotkeyState.postValue(PlayError(RuntimeException(e.message)))
    }

    fun getSearchArticle(keyword: String) {
        searchArticle(Query(k = keyword))
    }

    private val _hotkeyState = MutableLiveData<PlayState<List<HotkeyModel>>>()

    val hotkeyState: LiveData<PlayState<List<HotkeyModel>>>
        get() = _hotkeyState

    fun getHotkeyList() {
        hotkeyJob?.cancel()
//        hotkeyJob = viewModelScope.launch(Dispatchers.Main) {
//            try {
//                (repositoryArticle as SearchRepository).getHotKey(_hotkeyState)
//            } catch (e: Exception) {
//                _hotkeyState.postValue(PlayError(RuntimeException(e.message)))
//            }
//        }

       hotkeyJob = viewModelScope.launch(
           CoroutineName("handlerExcept") + exceptionHandler
       ) {
           (repositoryArticle as SearchRepository).getHotKey(_hotkeyState)
       }
//           hotkeyJob = (repositoryArticle as SearchRepository)
//             .http(
//                 scope = viewModelScope,
//                 request = { PlayAndroidNetwork.getHotkeyModel() },
//                 state = _hotkeyState
//             )
        
    }

}
