package com.example.sub2.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.sub2.R
import com.example.sub2.view.FollowersFragment
import com.example.sub2.view.FollowingFragment

class SectionPagerAdapter(private val Ctx: Context, fm: FragmentManager, data: Bundle) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragmentBundle : Bundle = data

    override fun getCount(): Int = 2

    @StringRes
    private val Tab_Titles = intArrayOf(R.string.tab1 , R.string.tab2)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return Ctx.resources.getString(Tab_Titles[position])
    }
}