package com.hydra.starbucksmock.ui.view

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hydra.starbucksmock.R
import com.hydra.starbucksmock.ui.view.DetailListModel.Companion.Key_Title
import com.hydra.starbucksmock.ui.view.DetailListModel.Companion.Key_Value

class TitleValueListView : RecyclerView {

    interface OnTitleValueListListener {
        fun onItemClickedListener(index: Int)
    }

    companion object {
        const val TYPE_HEADER = 1
        const val TYPE_TWO_DESCRIPTION = 2
        const val TYPE_MORE = 3
    }

    private val myAdapter = ListAdapter()
    private var detailListModel = DetailListModel()
    var onTitleValueListListener: OnTitleValueListListener? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        init()
    }

    constructor(context: Context, attr: AttributeSet, defStyle: Int) : super(
        context,
        attr,
        defStyle
    ) {
        init()
    }

    private fun init() {
        val layoutManager = object : LinearLayoutManager(context, VERTICAL, false) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        setLayoutManager(layoutManager)
        this.adapter = myAdapter
        val dividerItemDecoration = object : DividerItemDecoration(
            context,
            layoutManager.orientation
        ) {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: State
            ) {
                val position: Int = parent.getChildAdapterPosition(view)
                if (position == state.itemCount - 1) {
                    outRect.setEmpty()
                } else {
                    super.getItemOffsets(outRect, view, parent, state)
                }
            }
        }
        this.addItemDecoration(dividerItemDecoration)
    }

    fun setModel(model: DetailListModel) {
        this.detailListModel = model
        this.adapter?.notifyDataSetChanged()
    }

    inner class ListAdapter : Adapter<BindableViewHolder>() {

        private val onClickListener: OnClickListener = OnClickListener { view ->
            val position: Int = view.tag as Int
            onTitleValueListListener?.onItemClickedListener(position)
        }

        override fun getItemCount(): Int {
            return detailListModel.getSize()
        }

        override fun getItemViewType(position: Int): Int {
            return detailListModel.getType(position)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder {
            return when (viewType) {

                TYPE_HEADER -> {
                    val view: View =
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_header, parent, false)
                    HeaderViewHolder(view)
                }

                TYPE_TWO_DESCRIPTION -> {
                    val view: View =
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_two_description, parent, false)
                    TwoDescriptionViewHolder(view)
                }

                TYPE_MORE -> {
                    val view: View =
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_more, parent, false)
                    HeaderViewHolder(view)
                }

                else -> throw RuntimeException("Unreachable code")
            }
        }

        override fun onBindViewHolder(
            viewHolder: BindableViewHolder,
            position: Int
        ) {
            viewHolder.bind(position)
        }

    }

    inner class HeaderViewHolder(view: View) : BindableViewHolder(view) {

        override fun bind(index: Int) {
        }
    }

    inner class TwoDescriptionViewHolder(view: View) : BindableViewHolder(view) {

        private val title = view.findViewById<TextView>(R.id.title)
        private val description = view.findViewById<TextView>(R.id.description)

        override fun bind(index: Int) {
            title.text = detailListModel.getString(context, index, Key_Title)
            description.text = detailListModel.getString(context, index, Key_Value)
        }
    }

}

abstract class BindableViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(index: Int)
}

class DetailListModel {

    private var dataMap = ArrayList<HashMap<String, Any>>()

    companion object {
        const val Key_Type = "type"
        const val Key_Title = "title"
        const val Key_Value = "value_one"
    }

    private val standardMap: HashMap<String, Any> = hashMapOf(
        Key_Type to "",
        Key_Title to "",
        Key_Value to ""
    )

    fun getStandardMap() = HashMap(standardMap)


    fun getSize(): Int = dataMap.size

    fun putData(map: HashMap<String, Any>) {
        dataMap.add(map)
    }

    private fun getDataAt(index: Int): HashMap<String, Any> {
        return dataMap[index]
    }

    private fun setDataAt(index: Int, element: HashMap<String, Any>) {
        dataMap[index] = element
    }

    fun clearData() {
        dataMap.clear()
    }

    fun getType(position: Int): Int {
        return dataMap[position][Key_Type] as Int
    }

    fun getString(context: Context, position: Int, key: String): CharSequence =
        when (val value = dataMap[position][key]) {
            is String -> value
            is CharSequence -> value
            is Int -> context.getString(value)
            else -> throw IllegalArgumentException("dataMap must initialize either string or resource id")
        }
}
