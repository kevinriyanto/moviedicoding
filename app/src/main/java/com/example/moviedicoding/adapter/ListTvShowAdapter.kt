package com.example.moviedicoding.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedicoding.R
import com.example.moviedicoding.model.TvShow
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_tv_show_row.view.*

class ListTvShowAdapter(private val listener:(
    TvShow?)-> Unit): RecyclerView.Adapter<ListTvShowAdapter.ListTvShowsViewHolder>(){
    private val tv_show = ArrayList<TvShow>()
    fun setData(items: ArrayList<TvShow>) {
        tv_show.clear()
        tv_show.addAll(items)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ListTvShowsViewHolder {
        return ListTvShowsViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_tv_show_row,p0,false))
    }

    override fun getItemCount(): Int = tv_show.size

    override fun onBindViewHolder(p0: ListTvShowAdapter.ListTvShowsViewHolder, p1: Int) {
        p0.bindItem(tv_show[p1], listener)
    }
    class ListTvShowsViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bindItem(tv_show: TvShow, listener: (TvShow?)-> Unit){
            tv_show.image?.let { Picasso.get().load(it).into(itemView.tv_show_img_item_photo) }
            itemView.tv_show_title.text = tv_show.title
            itemView.setOnClickListener {
                listener(tv_show)
            }
        }
    }

}

