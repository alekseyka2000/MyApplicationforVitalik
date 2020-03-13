package com.startandroid.myapplicationforvitalik

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_movies_list.*
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 */
class MoviesListFragment : BaseFragment() {
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override val layoutRes: Int
        get() = R.layout.fragment_movies_list

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fun getData() {
            //higher-older functions for GET request
            fun onShowMoviesList(list: List<MoviesData>) {
                viewManager = LinearLayoutManager(activity)
                viewAdapter = Adapter(list)
                recycler_view.apply {
                    setHasFixedSize(true)
                    layoutManager = viewManager
                    adapter = viewAdapter
                }
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

        //first get request
        getData()
    }

    override fun onStart() {
            //higher-older functions for GET request
            fun onShowMoviesList(list: List<MoviesData>) {
                Toast.makeText(getActivity(), "Success!", Toast.LENGTH_LONG).show()
            }

            fun onShowError() {
                Toast.makeText(getActivity(), getString(R.string.requestError), Toast.LENGTH_LONG)
                    .show()
            }

            swipeRefresh.setOnRefreshListener {
                MoviesRepository.getPopularMovies(
                    onSuccess = ::onShowMoviesList,
                    onError = ::onShowError
                )
                swipeRefresh.isRefreshing = false
            }
            super.onStart()
    }
}


class Adapter(private val moviesList: List<MoviesData>) :
    RecyclerView.Adapter<Adapter.MoviesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int = moviesList.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    inner class MoviesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val poster: ImageView = itemView.findViewById(R.id.movie_poster)
        val name: TextView = itemView.findViewById(R.id.movie_name)
        val description : TextView = itemView.findViewById(R.id.movie_description)

        fun bind(movie: MoviesData){
            name.setText(movie.original_title)
            description.setText(movie.overview)
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w780${movie.poster_path}")
                .into(poster)
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
