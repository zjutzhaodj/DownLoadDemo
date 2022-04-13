package com.mobile.demo.demoapplication

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.demo.demoapplication.biz.download.bean.DownloadBean
import com.mobile.demo.demoapplication.biz.download.ui.ImgViewHolder
import com.mobile.demo.demoapplication.biz.download.viewmodel.DownloadViewModel
import java.util.concurrent.atomic.AtomicInteger

/**
 *
 * @Description:    描述
 * @Author:         菲克
 * @Modify:         xxx
 * @CreateDate:     2022/4/13 10:31 上午
 */
class MainActivity : AppCompatActivity() {

    lateinit var recycler: RecyclerView
    private lateinit var viewModel: DownloadViewModel
    var list: List<DownloadBean>? = null

    val STATE_INIT = 0
    val STATE_WAITING = 1
    val STATE_DOWLOADING = 2
    val STATE_SUCCESS = 3
    val STATE_FAILED = 4

    val DEFAULT_TASK_COUNT = 2

    lateinit var handler: Handler

    private var count: AtomicInteger = AtomicInteger()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        initView()
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DownloadViewModel::class.java)

        viewModel.init()
        viewModel.list.observe(this,
            Observer<List<DownloadBean>> {
                refreshView(it)
            })
    }

    private fun refreshView(data: List<DownloadBean>?) {
        list = data
        recycler.adapter?.notifyDataSetChanged()
    }

    private fun initView() {
        recycler = findViewById(R.id.recycler_view)
        recycler.adapter = TAdapter()
        recycler.layoutManager = LinearLayoutManager(this)
    }

    inner class TAdapter : RecyclerView.Adapter<ImgViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgViewHolder {
            val view =
                LayoutInflater.from(this@MainActivity)
                    .inflate(R.layout.item_dowload, parent, false)
            return ImgViewHolder(view)
        }

        override fun onBindViewHolder(holder: ImgViewHolder, position: Int) {
            val bean = list!![position]
            holder.title.text = bean.title ?: ""
            Glide.with(this@MainActivity)
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

    }

    private fun changeState(bean: DownloadBean, state: Int) {
        bean.state = state
    }

    fun cancel(bean: DownloadBean) {
        bean.state = STATE_INIT
        cancelDownload()

    }

    fun updateProcess(){
        handler.post {
            //
        }
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
            //download
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


