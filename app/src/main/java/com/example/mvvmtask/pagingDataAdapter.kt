package com.example.mvvmtask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmtask.Data.Photo
import com.example.mvvmtask.databinding.ImgItemBinding

class pagingDataAdapter: PagingDataAdapter<Photo,pagingDataAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ImgItemBinding.inflate((LayoutInflater.from(parent.context)),
                parent,false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if(currentItem != null){
            holder.bind(currentItem)
        }
    }


    class PhotoViewHolder(private val binding: ImgItemBinding):
    RecyclerView.ViewHolder(binding.root){
        fun bind(photo: Photo){
            Glide.with(itemView)
                .load(photo.url_s)
                .centerCrop()
                .into(binding.img)
        }
    }

    companion object{
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<Photo>(){
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo) =
                oldItem == newItem


        }
    }



}