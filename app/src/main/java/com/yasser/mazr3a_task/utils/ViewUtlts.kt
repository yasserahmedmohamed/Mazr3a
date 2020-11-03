package com.yasser.mazr3a_task.utils
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.yasser.mazr3a_task.R
import java.util.regex.Matcher
import java.util.regex.Pattern


fun String.IsEmail():Boolean{
    val format: String = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
    val pattern: Pattern = Pattern.compile(format, Pattern.CASE_INSENSITIVE)
    val matcher: Matcher = pattern.matcher(this)
    return matcher.matches()
}


fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
fun String.Format():String{
    if (this.isNullOrEmpty())
    {
        return "..."
    }
    else if (this.contains("<p>")){
        val str =  Html.fromHtml(this).toString()
        if (str.contains("[lang=\\\"en\\\"]"))
        {
            str.replace("[lang=\\\"en\\\"]","")

        }
        return str
    }
    else{
        return this
    }
}

fun View.snackbar(message: String) {
    val snack = Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }

//
//    val view= snack.view
//     val params = view.layoutParams as FrameLayout.LayoutParams
//
//     params.gravity = Gravity.CENTER
//     view.layoutParams = params
    snack.show()

}

fun View.HideWithAnimation() {

    this.animate()
        .translationY(this.getHeight().toFloat())
        .alpha(0.0f)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                this@HideWithAnimation.visibility = View.GONE
            }
        })
}


fun ImageView.LoadImage(url:String,context: Context){
    if (!url.isNullOrEmpty()) {
        Picasso.with(context)
            .load(url)
//            .placeholder(R.drawable.ic_app)
            .error(R.drawable.ic_app)
            .into(this)
    }
}

fun View.showWithAnimation() {
// Prepare the View for the animation



    if (!this.isShown) {
        this.setVisibility(View.VISIBLE);
        this.setAlpha(0.0f);
    }
    this.animate()
        .translationY(0.0f)
        .alpha(1.0f)
        .setListener(null)

}