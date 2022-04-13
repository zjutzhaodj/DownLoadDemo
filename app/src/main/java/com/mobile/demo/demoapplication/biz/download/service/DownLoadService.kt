package com.mobile.demo.demoapplication.biz.download.service

import com.mobile.demo.demoapplication.biz.download.bean.DownloadBean
import io.reactivex.Observable
import retrofit2.http.GET

/**
 *
 * @Description:    描述
 * @Author:         菲克
 * @Modify:         xxx
 * @CreateDate:     2022/4/13 2:07 下午
 */
interface DownLoadService {

    @GET("/demo/1.0/apps")
    fun fetchList(): Observable<List<DownloadBean>>

}