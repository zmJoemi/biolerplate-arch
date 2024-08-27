package dev.joemi.simple.page.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import dev.joemi.arch.data.DataResult
import dev.joemi.arch.vm.BaseViewModel
import dev.joemi.simple.data.DataRepository
import dev.joemi.simple.data.bean.HotSearch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : BaseViewModel(application) {

    val hotSearchList = MutableLiveData<List<HotSearch>>()

    public fun getHotSearchList() {
        CoroutineScope(Dispatchers.IO).launch {
            val dataResult = DataRepository.getBiliBiliRS()
            LogUtils.d("dataResult: ${dataResult.isSuccess} \t ${dataResult.message}")
            if (dataResult.isSuccess) {
                hotSearchList.postValue(dataResult.data!!)
            }
        }
    }

}