package com.abdulmanov.laovay.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.abdulmanov.laovay.R
import com.abdulmanov.laovay.ui.base.BaseFragment
import com.abdulmanov.laovay.ui.fragments.LibraryFragment
import com.abdulmanov.laovay.ui.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),
    ViewPager.OnPageChangeListener,
    BottomNavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemReselectedListener{

    private val fragments = listOf(
        BaseFragment.newInstance(R.layout.fragment_search_base, R.id.nav_host_search, SearchFragment::class.qualifiedName!!),
        BaseFragment.newInstance(R.layout.fragment_bookmark_base, R.id.nav_host_bookmark, LibraryFragment::class.qualifiedName!!)
    )

    private val indexToMenu = mapOf(0 to R.id.bottom_menu_search, 1 to R.id.bottom_menu_bookmark)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_view_pager.adapter = ViewPagerAdapter()
        main_view_pager.addOnPageChangeListener(this)
        main_view_pager.offscreenPageLimit = 2
        bottom_navigation_view.setOnNavigationItemSelectedListener(this)
        bottom_navigation_view.setOnNavigationItemReselectedListener(this)
    }

    override fun onBackPressed() {
        if (!fragments[main_view_pager.currentItem].onBackPressed()) {
            if (indexToMenu[main_view_pager.currentItem]!=R.id.bottom_menu_search) {
                main_view_pager.currentItem = indexToMenu.values.indexOf(R.id.bottom_menu_search)

            } else {
                super.onBackPressed()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        main_view_pager.currentItem = indexToMenu.values.indexOf(item.itemId)
        return true
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        fragments[indexToMenu.values.indexOf(item.itemId)].popToRoot()
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        val itemId = indexToMenu[position] ?: R.id.bottom_menu_search
        if (bottom_navigation_view.selectedItemId != itemId)
            bottom_navigation_view.selectedItemId = itemId
    }

    inner class ViewPagerAdapter: FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int) = fragments[position]

        override fun getCount(): Int = fragments.size
    }
}
