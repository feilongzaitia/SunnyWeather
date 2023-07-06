package com.example.sunnyweather.ui.place

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunnyweather.R

class PlaceFragment:Fragment() {
    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }
    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragement_place,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val recyclerView = activity?.findViewById<RecyclerView>(R.id.recycleview)
        val searchPlaceEdit = activity?.findViewById<EditText>(R.id.searchPlaceEdit)
        val bgImageView = activity?.findViewById<ImageView>(R.id.bgImageView)
        val layouManager = LinearLayoutManager(activity)
        adapter = PlaceAdapter(this,viewModel.placeList)


        if (recyclerView != null) {
            recyclerView.layoutManager = layouManager
            recyclerView.adapter = adapter
        }

        searchPlaceEdit?.addTextChangedListener { editabel ->
            val content = editabel.toString()
            if (content.isNotEmpty()){
                viewModel.searchPlaces(content)
            }
            else{
                recyclerView?.visibility = View.GONE
                bgImageView?.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }

        }

        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->
            val place = result.getOrNull()
            Log.d("item",place.toString())
            if (place != null){
                recyclerView?.visibility = View.VISIBLE
                bgImageView?.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(place)
                adapter.notifyDataSetChanged()
            }
            else{
                Toast.makeText(activity,"未能查询到任何地点",Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })



    }
}