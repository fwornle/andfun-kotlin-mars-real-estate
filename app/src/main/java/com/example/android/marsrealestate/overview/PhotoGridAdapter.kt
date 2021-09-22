package com.example.android.marsrealestate.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.network.MarsProperty

// adapter to map List<MarsProperty> data to the View(Holder) associated w/h the RV grid layout
class PhotoGridAdapter(val onClickListener: OnClickListener) :
    ListAdapter<MarsProperty, PhotoGridAdapter.MarsPropertyViewHolder>(DiffCallback) {

    // ViewHolder for Items to display MarsProperty records (by their image)
    class MarsPropertyViewHolder(private val binding: GridViewItemBinding):
        RecyclerView.ViewHolder(binding.root) {

            // method to bind a property from the data source to the view property
            // data source:
            //    RV-selected item --> position --> index in List<MarsProperty> ==> MarsProperty
            // view item:
            //    data bound to a property w/h name 'property' ... which is the <data><variable> in
            //    the associated layout (grid_view_item.xml --> GridViewItemBinding)
            fun bind(marsProperty: MarsProperty) {
                binding.property = marsProperty
                binding.executePendingBindings()
            }
    }

    // allow RV to determine when items are the same or not
    companion object DiffCallback : DiffUtil.ItemCallback<MarsProperty>() {
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem.id == newItem.id
        }
    }

    //  adapter internal class 'OnClickListener'  (used in the constructor to the adapter to create
    //  a property 'onClickListener' (of type OnClickListener)
    //
    //  - this (internal) class defines in it's primary constructor a property 'clickListener' of
    //    type '(marsProperty: MarsProperty) -> Unit', i. e. a lambda function w/h parameter
    //    'MarsProperty'
    //  - instantiating this (internal) class yields an object (of type OnClickListener) with a
    //    method 'onClick' of the above described type, i. e. '(mp: MarsProperty) -> Unit', where
    //    the method's body is the provided lambda function
    //  - as the internal class is instantiated in the primary constructor (as property
    //    'onClickListener') the adapter class (PhotoGridAdapter) can make use of it in whatever
    //    adapter activity needs it
    //  - intended use: during binding of a newly scrolled-in RV view item, the provided lambda is
    //    adapted (call-up parameter) to the data associated with this particular view item and
    //    then registered as the OnClick listener of this view item
    //
    class OnClickListener(val clickListener: (marsProperty: MarsProperty) -> Unit) {
        fun onClick(marsProperty:MarsProperty) = clickListener(marsProperty)
    }

    /*
     *  PhotoGridAdapter methods
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarsPropertyViewHolder {
        return MarsPropertyViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MarsPropertyViewHolder, position: Int) {

        // get view in question
        val marsProperty = getItem(position)

        // set OnClick listener of the RV view item (at 'position') which is to be bound to it's
        // associated data element of list 'List<MarsProperty>'
        //
        // - the view item can be found from the ViewHolder via getItemView() or, in Kotlin,
        //   from property 'itemView' of the extended ViewHolder class (Kotlin extensions, ktx)
        // - setOnClickListener uses the lambda function registered as adapter class property
        //   'onClickListener' during instantiation of the adapter class
        // - ... to set the OnClick listener of the RV view item to the lambda while using
        //   the list data element associated with the RV view item as call-up parameter (marsProp)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(marsProperty)
        }

        // bind the data element to the RV view item
        // ... so that it can be used in the layout (DataBinding - <data><variable> ...)
        holder.bind(marsProperty)
    }

}