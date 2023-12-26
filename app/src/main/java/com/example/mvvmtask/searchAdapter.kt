package com.example.mvvmtask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmtask.Data.Photo
import com.example.mvvmtask.databinding.ImgItemBinding

class searchAdapter(
    private val mList: List<Photo>
) : RecyclerView.Adapter<searchAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = ImgItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: searchAdapter.MainViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class MainViewHolder(val binding: ImgItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            Glide.with(itemView)
                .load(photo.url_s)
                .centerCrop()
                .into(binding.img)
//            Picasso.get().load(itemViewModel.url_s).into(binding.image)
//            binding.title.text = itemViewModel.title
//            binding.image.setOnClickListener { view ->
//                onImageClickListener.onImageClick(itemViewModel.url_s)
            }
        }
    }


