package com.startandroid.myapplicationforvitalik


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A simple [Fragment] subclass.
 */
abstract class BaseFragment : Fragment() {

    abstract val layoutRes: Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    )= inflater.inflate(layoutRes, container, false)
}
