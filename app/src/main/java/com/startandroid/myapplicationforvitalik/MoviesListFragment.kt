package com.startandroid.myapplicationforvitalik


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_movies_list.*

/**
 * A simple [Fragment] subclass.
 */
class MoviesListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
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
