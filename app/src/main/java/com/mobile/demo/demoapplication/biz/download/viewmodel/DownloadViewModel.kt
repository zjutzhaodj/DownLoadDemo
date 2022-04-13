package com.mobile.demo.demoapplication.biz.download.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.demo.demoapplication.base.RetrofitHelper
import com.mobile.demo.demoapplication.biz.download.bean.DownloadBean
import com.mobile.demo.demoapplication.biz.download.service.DownLoadService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
 * @Description:    描述
 * @Author:         菲克
 * @Modify:         xxx
 * @CreateDate:     2022/4/13 2:15 下午
 */
class DownloadViewModel : ViewModel() {

    var list: MutableLiveData<List<DownloadBean>> = MutableLiveData()
    lateinit var api: DownLoadService

    fun fetchList() {
        api.fetchList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.i("Main", it.toString())
                    it?.let {
                        list.postValue(it)
                    }

                }, {
                    Log.e("Main", it.toString())
                }
            )
    }

    fun init() {
        api = RetrofitHelper.getRetrofit().create(DownLoadService::class.java)
        fetchList()
    }
}