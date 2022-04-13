package com.mobile.demo.demoapplication.biz.download.bean

import androidx.annotation.Keep

/**
 *
 * @Description:    描述
 * @Author:         菲克
 * @Modify:         xxx
 * @CreateDate:     2022/4/13 2:10 下午
 */
@Keep
data class DownloadBean(
    var pkgName: String? = null,
    var title: String? = null,
    var icon: String? = null,
    var downLoadLink: String? = null,
    var fileSize: String? = null,
    /**
     *  0 - init
     *  1 - waiting
     *  2 - downloading
     *  3 - success
     *  4 - failed
     */
    var state: Int = 0
)