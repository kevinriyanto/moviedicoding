package com.example.moviedicoding.fragment


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.moviedicoding.R
import com.example.moviedicoding.adapter.MyPagerAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_favorite.view.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        val fragmentAdapter = MyPagerAdapter(childFragmentManager,view.context)
        view.viewpager_favorite.adapter = fragmentAdapter
        view.tabs_main.setupWithViewPager(view.viewpager_favorite)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.main_menu,menu)
        val myMenu = menu.findItem(R.id.search)
        myMenu.setVisible(false)
    }

}
