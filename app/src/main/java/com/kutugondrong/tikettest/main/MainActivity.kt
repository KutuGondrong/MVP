package com.kutugondrong.tikettest.main

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kutugondrong.network.model.ResponseItem
import com.kutugondrong.tikettest.R
import com.kutugondrong.tikettest.adapter.UserAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import androidx.recyclerview.widget.RecyclerView
import androidx.annotation.NonNull


class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainPresenter

    private lateinit var progressDialog: ProgressDialog

    private var adapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init() {
        progressDialog =  ProgressDialog(this)
        progressDialog.setMessage("loading")
        recycle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycle.adapter = adapter
        btnSearch.setOnClickListener{
            if (txtInputName.text.toString().isNullOrEmpty()) {
                empty()
            } else {
                presenter.search(txtInputName.text.toString())
            }
        }

    }
    override fun loading() {
        progressDialog.show()
    }

    override fun dismissLoading() {
        progressDialog.dismiss()
    }

    override fun failedNetwork() {
        Toast.makeText(this, "Silahkan coba lagi", Toast.LENGTH_SHORT).show()
    }

    override fun success(response: ResponseItem) {
        lytEmpty.visibility = View.GONE
        if (response.items.size > 0) {
            adapter.initData(response.items)
            recycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?

                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == response.items.size - 1) {
                        Toast.makeText(this@MainActivity, "Gak ada lagi bro", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        } else {
            empty()
        }
    }

    override fun empty() {
        lytEmpty.visibility = View.VISIBLE
    }
}
