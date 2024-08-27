package dev.joemi.simple.page.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.joemi.arch.adapter.recyclerview.BaseRecyclerViewAdapter
import dev.joemi.simple.data.bean.HotSearch
import dev.joemi.simple.databinding.LayoutBilibiliHotSearchBinding

class BiliBiliHotSearchAdapter(context: Context, diffCallback: DiffUtil.ItemCallback<HotSearch>) :
    BaseRecyclerViewAdapter<HotSearch, LayoutBilibiliHotSearchBinding>(context, diffCallback) {
    override fun getViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup?,
        viewType: Int
    ): LayoutBilibiliHotSearchBinding {
        return LayoutBilibiliHotSearchBinding.inflate(layoutInflater, parent, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindItem(
        binding: LayoutBilibiliHotSearchBinding,
        item: HotSearch,
        holder: RecyclerView.ViewHolder?
    ) {
        binding.tvTitle.text = "${item.title} 【热度：${item.heat}】"
    }

}