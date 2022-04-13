package com.mobile.demo.demoapplication.base

import android.app.Application
import com.scwang.smart.refresh.footer.ClassicsFooter

import com.scwang.smart.refresh.layout.SmartRefreshLayout

import com.scwang.smart.refresh.header.ClassicsHeader

import android.R


/**
 *
 * @Description:    描述
 * @Author:         菲克
 * @Modify:         xxx
 * @CreateDate:     2022/4/13 11:10 上午
 */
class MApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitHelper.init()
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.holo_blue_light, R.color.white) //全局设置主题颜色
            ClassicsHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }
        //设置全局的Footer构建器
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }
}