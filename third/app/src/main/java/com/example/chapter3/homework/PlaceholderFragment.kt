package com.example.chapter3.homework
import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.airbnb.lottie.LottieAnimationView

class PlaceholderFragment: Fragment(){
    private var loadingView: LottieAnimationView? = null
    private var friendListView: ListView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //用于载入res/layout下的xml布局文件并实例化，类似findById()
        val view = inflater.inflate(R.layout.fragment_placeholder,container,false)
        val data = arguments?.getStringArray("data")!!//如果为空，就抛出异常
        loadingView = view.findViewById(R.id.loading_view)
        friendListView = ListView(context)//返回当前view运行在那个activity context当中
        friendListView!!.adapter = ArrayAdapter(context!!,android.R.layout.simple_expandable_list_item_1,data)//android.R即为系统预先定义的，第三个args传入listStrings即可
        friendListView!!.visibility = View.VISIBLE
        friendListView!!.alpha = 0f
        (view as ViewGroup).addView(friendListView)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view!!.postDelayed({
            val fadeoutAnimationView = AnimatorInflater.loadAnimator(context,R.animator.fade_out)
            val fadeinAnimationView = AnimatorInflater.loadAnimator(context,R.animator.fade_in)
            fadeoutAnimationView.setTarget(loadingView)
            fadeinAnimationView.setTarget(friendListView)
            fadeoutAnimationView.addListener(object : Animator.AnimatorListener{
                override fun onAnimationCancel(animator: Animator?) {}
                override fun onAnimationEnd(animator: Animator?) {
                    fadeinAnimationView.start()
                    val container = (loadingView?.parent as ViewGroup)
                    container.removeView(loadingView)
                }
                override fun onAnimationStart(animator: Animator?) {}
                override fun onAnimationRepeat(animator: Animator?) {}
            })
            fadeoutAnimationView.start()
        },5000)

    }
}