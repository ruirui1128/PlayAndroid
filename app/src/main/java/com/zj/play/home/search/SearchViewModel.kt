package com.zj.play.home.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import com.zj.core.view.base.BaseAndroidViewModel
import com.zj.model.room.entity.HotKey
import dagger.hilt.android.scopes.ActivityScoped

/**
 * 版权：Zhujiang 个人版权
 * @author zhujiang
 * 版本：1.5
 * 创建日期：2020/5/19
 * 描述：PlayAndroid
 *
 */
@ActivityScoped
class SearchViewModel @ViewModelInject constructor(
    private val searchRepository: SearchRepository
) : BaseAndroidViewModel<List<HotKey>, HotKey, Boolean>() {

    override fun getData(page: Boolean): LiveData<Result<List<HotKey>>> {
        return searchRepository.getHotKey()
    }

}