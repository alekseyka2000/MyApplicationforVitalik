package com.startandroid.myapplicationforvitalik

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_flow.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 */
class FlowFragment : BaseFragment() {

    var currentFragment: String = "one"

    override val layoutRes: Int
    get() = R.layout.fragment_flow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //create fragments
        childFragmentManager.inTransaction {
            add(R.id.container, MoviesListFragment(),"one")
        }

        fun createOtherFragment(fragment: Fragment, TAG: String ){
            childFragmentManager.inTransaction {
                add(R.id.container, fragment,TAG)
                hide(fragment)
            }
        }


        createOtherFragment(FavoriteActorsFragment(),"two")
        createOtherFragment(AboutProgramFragment(),"three")
        //first get request
        getData()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        nav_view.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.navigation_movies_list ->{
                    //getData()
                    showFragment("one")
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_favorite_actors ->{showFragment("two")
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_about_program ->{showFragment("three")
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }

    fun showFragment(s:String){
        childFragmentManager.inTransaction {
            hide(childFragmentManager.findFragmentByTag(currentFragment)!!)
            show(childFragmentManager.findFragmentByTag(s)!!)
        }
        currentFragment=s
    }



    fun getData() {
        //higher-older functions for GET request
        fun onShowMoviesList(list: List<MoviesData>) {
            Toast.makeText(getActivity(), "Success!",Toast.LENGTH_LONG).show()
        }
        fun onShowError() {
            Toast.makeText(getActivity(), getString(R.string.requestError), Toast.LENGTH_LONG).show()
        }
        MoviesRepository.getPopularMovies(
            onSuccess = ::onShowMoviesList,
            onError = ::onShowError
        )
    }
}

//
private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}



