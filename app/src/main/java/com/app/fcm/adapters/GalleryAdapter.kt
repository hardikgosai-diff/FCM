package com.app.fcm.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.fcm.ads.NativeAds
import com.app.fcm.ads.NativeAds.populateNativeAdView
import com.app.fcm.databinding.AdUnifiedBinding
import com.app.fcm.databinding.AdapterGalleryBinding
import com.bumptech.glide.Glide

class GalleryAdapter(private val images: List<Uri>) :
    RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    open inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class NativeViewHolder(private val binding: AdUnifiedBinding) : ViewHolder(binding.root) {

        val context: Context = itemView.context

        fun bindUI() {
            NativeAds.nativeAd?.let {
                populateNativeAdView(it, binding)
            } ?: run {
                NativeAds.refreshNativeAd(context) {
                    populateNativeAdView(it, binding)
                }
            }
        }
    }

    inner class ImageViewHolder(private val binding: AdapterGalleryBinding) :
        ViewHolder(binding.root) {

        val context: Context = itemView.context

        fun bindUI() {
            Glide.with(context).load(images[adapterPosition]).centerCrop().into(binding.iv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == 0) {
            val binding =
                AdUnifiedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            NativeViewHolder(binding)
        } else {
            val binding =
                AdapterGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ImageViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is ImageViewHolder) {
            holder.bindUI()
        } else if (holder is NativeViewHolder) {
            holder.bindUI()
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun getItemViewType(position: Int): Int {
        val uri = images[position]
        return if (uri == Uri.EMPTY) 0 else 1
    }
}