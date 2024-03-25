package com.parsonjson

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parsonjson.R
import com.parsonjson.model.DataItem
import com.parsonjson.model.ResponseUser
import com.parsonjson.network.ApiConfig
import okhttp3.Call
import okhttp3.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {


    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = UserAdapter(mutableListOf())

        rv_users.setHasFixedSize(true)
        rv_users.layoutManager = LinearLayoutManager( this)
        rv_users.adapter = adapter

        getUser()
    }

    private fun getUser(){
        val client = ApiConfig.getApiService().getListUsers( "1")

        client.enqueue(object : Callback<ResponseUser>, retrofit2.Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>){
                if (response.isSuccessful){
                    val dataArray = response.body()?.data as List<DataItem>
                    for (data in dataArray){
                        adapter.addUser(data)
                    }
                }
            }
            override fun onFailure(call: Call<ResponseUser>, t: Throwable){
                Toast.makeText( this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                t.stackTrace
            }
        })
    }
}