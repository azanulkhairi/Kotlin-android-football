package com.dicoding.azanul.footballapps.fragment.mainfragment.teamdetailfragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView

import com.dicoding.azanul.footballapps.R
import com.dicoding.azanul.footballapps.adapter.TeamAdapter
import com.dicoding.azanul.footballapps.model.Event
import com.dicoding.azanul.footballapps.model.Team
import com.dicoding.azanul.footballapps.mvp.TeamPresenter
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.swipeRefreshLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private var teams : Team? = null

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentOverview.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentOverview : Fragment() {
    // TODO: Rename and change types of parameters
    private var teams: Team? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            teams = it.getParcelable("GET_OBJECT_TEAMS")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_overview, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var textDescription = find<TextView>(R.id.text_description)
        textDescription.text = teams?.teamDescription
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentOverview.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(team: Team) =
            FragmentOverview().apply {
                arguments = Bundle().apply {
                    putParcelable("GET_OBJECT_TEAMS", team)
                }
            }
    }
}
