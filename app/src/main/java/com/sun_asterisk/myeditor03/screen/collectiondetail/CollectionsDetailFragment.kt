package com.sun_asterisk.myeditor03.screen.collectiondetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun_asterisk.myeditor03.R
import com.sun_asterisk.myeditor03.utils.OnItemRecyclerViewClickListener

class CollectionsDetailFragment : Fragment(), OnItemRecyclerViewClickListener<String> {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_collection_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onItemClick(data: String) {
    }

    private fun initView() {
    }

    companion object {
        fun instance(): CollectionsDetailFragment {
            return CollectionsDetailFragment()
        }
    }
}
