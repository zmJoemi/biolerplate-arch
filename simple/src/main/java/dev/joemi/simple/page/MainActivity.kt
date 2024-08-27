package dev.joemi.simple.page

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dev.joemi.arch.activity.BaseVMViewBindingActivity
import dev.joemi.simple.data.bean.HotSearch
import dev.joemi.simple.databinding.ActivityMainBinding
import dev.joemi.simple.page.adapter.BiliBiliHotSearchAdapter
import dev.joemi.simple.page.vm.MainViewModel


class MainActivity : BaseVMViewBindingActivity<MainViewModel, ActivityMainBinding>() {

    private val adapter: BiliBiliHotSearchAdapter by lazy {
        BiliBiliHotSearchAdapter(this, object : DiffUtil.ItemCallback<HotSearch>() {
            override fun areItemsTheSame(oldItem: HotSearch, newItem: HotSearch): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: HotSearch, newItem: HotSearch): Boolean {
                return oldItem == newItem
            }
        })
    }

    override fun beforeSetContentView() {
        enableEdgeToEdge()
        super.beforeSetContentView()
    }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun getViewModelClass(): Class<MainViewModel>? {
        return MainViewModel::class.java
    }

    override fun initView(savedInstanceState: Bundle?) {
        ViewCompat.setOnApplyWindowInsetsListener(bindingView.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        adapter.setOnItemClickListener { itemView, item, position ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(item.link))
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "No browser app found", Toast.LENGTH_SHORT).show()
            }
        }
        bindingView.recyclerView.layoutManager = LinearLayoutManager(this)
        bindingView.recyclerView.adapter = adapter
        bindingView.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun initObserver() {
        viewModel!!.hotSearchList.observe(this) {
            adapter.submitList(it)
        }
        viewModel!!.getHotSearchList()
    }
}