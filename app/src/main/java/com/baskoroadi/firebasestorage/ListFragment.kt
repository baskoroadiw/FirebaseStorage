package com.baskoroadi.firebasestorage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment(),RecyclerViewClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference

    private lateinit var listData : ArrayList<Items>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_list.visibility = View.GONE

        listData = ArrayList()

        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference

        val listRef = storageRef.child("images")

        listRef.listAll()
            .addOnSuccessListener { listResult ->
                listResult.items.forEach { item ->
                    listData.add(Items(item.name))
                }
                viewManager = LinearLayoutManager(context)
                viewAdapter = RvAdapter(listData)
                (viewAdapter as RvAdapter).listener = this

                recyclerView = rv_list.apply {
                    setHasFixedSize(true)
                    layoutManager = viewManager
                    adapter = viewAdapter
                }

                progressBarRv.visibility = View.GONE
                rv_list.visibility = View.VISIBLE
            }
    }

    override fun onItemClicked(view: View, items: Items) {
        Toast.makeText(context, "dor ${items.nameFile}", Toast.LENGTH_SHORT).show()
    }
}