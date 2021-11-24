package kr.ac.kumoh.s20190101.gunplaapplication

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest

class GunplaViewModel(application: Application): AndroidViewModel(application) {
    companion object {
        const val QUEUE_TAG = "VolleyRequest"
    }

    private var mQueue: RequestQueue

    data class Mechanic (
        val id: Int,
        val name: String,
        val model: String,
        val manufacturer: String,
        val armor: String,
        val height: Double,
        val weight: Double
    )

    val list = MutableLiveData<ArrayList<Mechanic>>()
    private val gunpla = ArrayList<Mechanic>()

    init {
        list.value = gunpla
        mQueue = VolleyRequest.getInstance(application).requestQueue
    }

    override fun onCleared() {
        super.onCleared()
        mQueue.cancelAll(QUEUE_TAG)
    }

    fun getGunpla(i: Int) = gunpla[i]

    fun getSize() = gunpla.size


    fun requestMechanic() {
        // NOTE: 서버 주소는 본인의 서버 주소 사용할 것
        val url = "https://expressforvolley-ruslo.run.goorm.io/gunpladb/mechanic/"

        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {
                //binding.result.text = it.toString()
                //Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
            },
            {
                Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
                //binding.result.text = it.toString()
            }
        )

        request.tag = QUEUE_TAG
        mQueue.add(request)
    }
}