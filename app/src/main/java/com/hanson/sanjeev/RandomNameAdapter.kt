//package com.hanson.sanjeev
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.hanson.sanjeev.databinding.ItemRandomNameBinding
//import android.content.Context
//import android.widget.ArrayAdapter
//
//class RandomNameAdapter(context: Context, private val randomNames: List<RandomName>) :
//    ArrayAdapter<RandomName>(context, 0, randomNames) {
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val binding: ItemRandomNameBinding
//        if (convertView == null) {
//            binding = ItemRandomNameBinding.inflate(LayoutInflater.from(context), parent, false)
//        } else {
//            binding = ItemRandomNameBinding.bind(convertView)
//        }
//
//        val randomName = getItem(position)
//        binding.randomName = randomName
//        binding.executePendingBindings()
//
//        return binding.root
//    }
//}
