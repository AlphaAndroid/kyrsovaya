package com.alphan.mainactivity.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alphan.mainactivity.R
import com.alphan.mainactivity.core.BaseApplication
import com.alphan.mainactivity.models.CircleCheckBoxModel
import com.alphan.mainactivity.utils.UserPreferences
import kotlinx.android.synthetic.main.item_place_type.view.*
import javax.inject.Inject

class PlacesTypeAdapter constructor(types: List<CircleCheckBoxModel>, listener: OnPlaceTypeItemClickListener) : RecyclerView.Adapter<PlacesTypeAdapter.PlacesViewHolder>() {

    @Inject
    lateinit var userPreferences: UserPreferences

    private var mTypes = types
    private var mListener = listener

    init {
        BaseApplication.appComponent.inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_place_type, parent, false)
        return PlacesViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mTypes.size
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        val item = mTypes[holder.adapterPosition]
        holder.setInfo(item)
        holder.itemView.setOnClickListener {
            item.isChecked = !item.isChecked
            if (item.isChecked) userPreferences.selectedPlaceType = item.requestWord
            else userPreferences.selectedPlaceType = null
            holder.setStatus(item.isChecked)
                    /*mTypes.forEachIndexed { index, _ ->
                run {
                    if (index != holder.adapterPosition)
                        mTypes[index].isChecked = false
                }
            }*/
            mTypes.forEach { it.isChecked = false }
            notifyDataSetChanged()
        }
    }

    interface OnPlaceTypeItemClickListener {
        fun onItemClicked(placeType: String)
    }

    inner class PlacesViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setStatus(isChecked: Boolean) {
            if (isChecked) itemView.circleImageView.borderWidth = 20
            else itemView.circleImageView.borderWidth = 0
            itemView.circleImageView.borderColor = ContextCompat.getColor(itemView.context, R.color.colorPrimary)
        }

        fun setInfo(item: CircleCheckBoxModel) {
            itemView.titleTv.text = item.title
            itemView.circleImageView.setImageDrawable(ContextCompat.getDrawable(itemView.context, item.image))
            itemView.circleImageView.setCircleBackgroundColorResource(item.background)
            userPreferences.selectedPlaceType?.run { setStatus(this == item.requestWord) }
        }
    }
}