package com.ibrahim.mytask.utis

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ibrahim.mytask.R

const val recipesEndPoint="android-test/recipes.json"
const val BASEURL="https://hf-android-app.s3-eu-west-1.amazonaws.com/"
const val SORT_Type:String="SortType"
fun checkInternetConnection(context: Context) :Boolean{
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    if( activeNetwork?.isConnectedOrConnecting == true){
        return true;
    }
    return false;

}
fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        centerRadius = 1f
        centerRadius = 50f
        start()
    }

}
fun ImageView.loadImage(url: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}
@BindingAdapter("android:imageUrl")
fun  loadImage(view: ImageView ,url: String?) {
    view.loadImage(url, getProgressDrawable(view.context))
}