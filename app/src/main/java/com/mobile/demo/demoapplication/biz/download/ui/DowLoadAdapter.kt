package com.mobile.demo.demoapplication.biz.download.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.demo.demoapplication.R
import com.mobile.demo.demoapplication.biz.download.bean.DownloadBean
import java.util.concurrent.atomic.AtomicInteger

/**
 *
 * @Description:    描述
 * @Author:         菲克
 * @Modify:         xxx
 * @CreateDate:     2022/4/13 3:35 下午
 */
class DowLoadAdapter(context: Context) : RecyclerView.Adapter<ImgViewHolder>() {

    val context = context
    var list: List<DownloadBean>? = null

    val STATE_INIT = 0
    val STATE_WAITING = 1
    val STATE_DOWLOADING = 2
    val STATE_SUCCESS = 3
    val STATE_FAILED = 4

    val DEFAULT_TASK_COUNT = 2

    private var count: AtomicInteger = AtomicInteger()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgViewHolder {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.item_dowload, parent, false)
        return ImgViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImgViewHolder, position: Int) {
        val bean = list!![position]
        holder.title.text = bean.title ?: ""
        Glide.with(context)
            .load(bean.icon)
            .into(holder.img)
        holder.btn.text = getBtnText(bean.state)
        holder.state.text = getStateText(bean.state)
        holder.btn.visibility = if (bean.state == STATE_SUCCESS) View.VISIBLE else View.GONE
        holder.btn.setOnClickListener {
            when (bean.state) {
                STATE_INIT -> {
                    downLoad(bean)
                }
                STATE_WAITING, STATE_DOWLOADING -> {
                    cancel(bean)
                }
                STATE_FAILED -> {
                    retry(bean)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


    private fun changeState(bean: DownloadBean, state: Int) {
        bean.state = state
    }

    fun cancel(bean: DownloadBean) {
        bean.state = STATE_INIT
        cancelDownload()
    }

    fun retry(bean: DownloadBean) {
        if (count.get() < DEFAULT_TASK_COUNT) {
            count.getAndAdd(1)
            changeState(bean, STATE_DOWLOADING)
        } else {
            changeState(bean, STATE_WAITING)
        }
    }

    /**
     *
     */
    fun downLoad(bean: DownloadBean) {
        if (count.get() < DEFAULT_TASK_COUNT) {
            count.getAndAdd(1)
            changeState(bean, STATE_DOWLOADING)
        } else {
            changeState(bean, STATE_WAITING)
        }
        //
    }

    fun cancelDownload() {

    }

    private fun getStateText(state: Int): String {
        return when (state) {
            0 -> {
                ""
            }
            1 -> {
                "Waiting"
            }
            2 -> {
                ""
            }
            3 -> {
                "DownLoad Successfully"
            }
            4 -> {
                "DowLoad Failed"
            }
            else -> {
                ""
            }
        }
    }


    fun getBtnText(state: Int): String {
        return when (state) {
            0 -> {
                "Download"
            }
            1 -> {
                "Cancel"
            }
            2 -> {
                "Cancel"
            }
            3 -> {
                ""
            }
            4 -> {
                "Retry"
            }
            else -> {
                ""
            }
        }
    }
}

class ImgViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.tv_title)
    val img: ImageView = view.findViewById(R.id.iv_img)
    val btn: TextView = view.findViewById(R.id.tv_btn)
    val state: TextView = view.findViewById(R.id.tv_state)
}