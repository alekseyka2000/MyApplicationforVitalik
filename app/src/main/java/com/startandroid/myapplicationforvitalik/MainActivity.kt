package com.startandroid.myapplicationforvitalik

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import org.jetbrains.anko.longToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    var currentFragment: String = "one"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //create fragments
        supportFragmentManager.inTransaction {
            add(R.id.frame, MoviesListFragment(),"one")
        }

        fun createOtherFragment(fragment: Fragment, TAG: String ){
            supportFragmentManager.inTransaction {
                add(R.id.frame, fragment,TAG)
                hide(fragment)
            }
        }


        createOtherFragment(FavoriteActorsFragment(),"two")
        createOtherFragment(AboutProgramFragment(),"three")

        fun showFragment(s:String){
            supportFragmentManager.inTransaction {
                hide(supportFragmentManager.findFragmentByTag(currentFragment)!!)
                show(supportFragmentManager.findFragmentByTag(s)!!)
            }
            currentFragment=s
        }



        fun getData() {
            //higher-older functions for GET request
            fun onShowMoviesList(list: List<MoviesData>) {
                longToast("Success!")
            }
            fun onShowError() {
                longToast(getString(R.string.requestError))
            }
            MoviesRepository.getPopularMovies(
                onSuccess = ::onShowMoviesList,
                onError = ::onShowError
            )
        }
        //first get request
        getData()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val listener = navView.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
               R.id.navigation_movies_list ->{
                   getData()
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
}



// get request

val url = "https://api.themoviedb.org/3/"

object MoviesRepository {
    private val api: API

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(API::class.java)
    }

    fun getPopularMovies(
        page: Int = 1,
        onSuccess: (movies: List<MoviesData>) -> Unit,
        onError: () -> Unit
        ){
        api.getPopularMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful){
                        val responseBody = response.body()

                        if (responseBody != null){
                            onSuccess.invoke(responseBody.movies)
                        }else{
                            onError.invoke()
                        }
                    }else{
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }
}

//
private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

