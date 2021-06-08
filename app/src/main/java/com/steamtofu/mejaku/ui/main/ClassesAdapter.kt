package com.steamtofu.mejaku.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.steamtofu.mejaku.database.Classes
import com.steamtofu.mejaku.databinding.ItemClassRowBinding
import com.steamtofu.mejaku.helper.ClassesDiffCallback
import com.steamtofu.mejaku.ui.classes.CreateClassActivity
import com.steamtofu.mejaku.uploadscore.UploadScoreActivity

class ClassesAdapter(private val activity:Activity): PagedListAdapter<Classes, ClassesAdapter.ClassesViewHolder>(
    DIFF_CALLBACK) {
    private val listClasses = ArrayList<Classes>()


    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Classes> = object : DiffUtil.ItemCallback<Classes>() {
            override fun areItemsTheSame(oldClasses: Classes, newClasses: Classes): Boolean {
                return oldClasses.className== newClasses.className && oldClasses.date == newClasses.date
            }
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldClasses: Classes, newClasses: Classes): Boolean {
                return oldClasses == newClasses
            }
        }
    }
    
    fun setListClasses(listClasses: List<Classes>) {
        val diffCallback = ClassesDiffCallback(this.listClasses, listClasses)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listClasses.clear()
        this.listClasses.addAll(listClasses)
        diffResult.dispatchUpdatesTo(this)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassesViewHolder {
        val binding = ItemClassRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClassesViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ClassesViewHolder, position: Int) {
//        holder.bind(listClasses[position])
        holder.bind(getItem(position) as Classes)
    }
//    override fun getItemCount(): Int {
//        return listClasses.size
//    }

    inner class ClassesViewHolder(private val binding: ItemClassRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(classes: Classes) {
            with(binding) {
                tvClassName.text = classes.className
                tvDate.text = classes.date
                btnEdit.setOnClickListener {
                    val intent = Intent(activity, CreateClassActivity::class.java)
//                    intent.putExtra(CreateClassActivity.EXTRA_POSITION, adapterPosition)
                    intent.putExtra(CreateClassActivity.EXTRA_CLASS, classes)
                    activity.startActivityForResult(intent,CreateClassActivity.REQUEST_UPDATE)
                }
                btnUpload.setOnClickListener {
                    val intent = Intent(activity,UploadScoreActivity::class.java)
                    activity.startActivity(intent)
                }
            }
        }
    }
}