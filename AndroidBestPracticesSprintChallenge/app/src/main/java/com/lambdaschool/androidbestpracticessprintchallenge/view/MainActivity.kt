package com.lambdaschool.androidbestpracticessprintchallenge.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lambdaschool.androidbestpracticessprintchallenge.App
import com.lambdaschool.androidbestpracticessprintchallenge.R
import com.lambdaschool.androidbestpracticessprintchallenge.model.Makeup
import com.lambdaschool.androidbestpracticessprintchallenge.retrofit.MakeupService
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.makeup_list_item.view.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    var listMakeup = ArrayList<Makeup>()

    private lateinit var disposable: Disposable

    @Inject
    lateinit var makeupService: MakeupService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as App).appComponent.injectMainActivity(this)

        val makeupListAdapter = MakeupListAdapter(listMakeup)

        rv_list.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = makeupListAdapter
        }

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                progressBar.visibility = View.VISIBLE
                p0?.let {
                    disposable = makeupService.getMakeupBrand("maybelline")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe (
                            { makeup: List<Makeup> ->
                            progressBar.visibility = View.INVISIBLE
                            listMakeup.addAll(makeup)
                            makeupListAdapter.notifyDataSetChanged() },
                            { fail -> progressBar.visibility = View.INVISIBLE })
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0?.length == 0) {
                    listMakeup.clear()
                    makeupListAdapter.notifyDataSetChanged()
                }
                return true
            }
        })
    }

    inner class MakeupListAdapter(private val makeupList: ArrayList<Makeup>) :
        RecyclerView.Adapter<MakeupListAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.makeup_list_item, parent, false)
            )
        }

        override fun getItemCount() = makeupList.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val makeup = makeupList[position]
            Picasso.get().load(makeup.image_link).into(holder.image)
            holder.name.text = makeup.name
            holder.price.text = makeup.price
            holder.rating.text = makeup.rating
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val image = view.iv_makeup
            val name = view.tv_name
            val price = view.tv_price
            val rating = view.tv_rating
        }

    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}
