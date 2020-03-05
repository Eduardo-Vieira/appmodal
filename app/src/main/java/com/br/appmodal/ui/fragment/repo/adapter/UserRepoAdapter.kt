package com.br.appmodal.ui.fragment.repo.adapter

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.br.appmodal.R
import com.br.appmodal.model.UserRepo
import kotlinx.android.synthetic.main.item_user_repo.view.*

class UserRepoAdapter(
    private val itens: MutableList<UserRepo> = mutableListOf(),
    var onClickItem:(userRepo: UserRepo) -> Unit = {}
) : RecyclerView.Adapter<UserRepoAdapter.ViewHolder>(){

    private lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_repo, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = itens.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itens[position]
        holder.setItens(item, position)
    }

    fun update(itensNew: List<UserRepo>) {
        this.itens.clear()
        this.itens.addAll(itensNew)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var userRepo: UserRepo

        init {
            itemView.setOnClickListener {
                if (::userRepo.isInitialized) {
                    onClickItem(userRepo)
                }
            }
        }

        @TargetApi(Build.VERSION_CODES.M)
        fun setItens(item: UserRepo, position: Int){
            this.userRepo = item

            this.itemView.txtStargazersCount.text = item.stargazersCount.toString()
            this.itemView.txtName.text = item.name
            this.itemView.txtWatchers.text = item.watchers.toString()
            this.itemView.txtWatchersCount.text = item.watchersCount.toString()
            this.itemView.txtPushedAt.text = item.pushedAtToDay()

            if (position % 2 == 0) {
                this.itemView.painel1.setBackgroundColor(context.getColor(R.color.color_FFFFFF))
                this.itemView.painel2.setBackgroundColor(context.getColor(R.color.color_7E7E7E))
                this.itemView.txtName.setTextColor(context.getColor(R.color.color_000000))
                this.itemView.lblStargazersCount.setTextColor(context.getColor(R.color.color_000000))
                this.itemView.txtStargazersCount.setTextColor(context.getColor(R.color.color_000000))
            }else {
                this.itemView.painel1.setBackgroundColor(context.getColor(R.color.color_242528))
                this.itemView.painel2.setBackgroundColor(context.getColor(R.color.color_000000))
                this.itemView.txtName.setTextColor(context.getColor(R.color.color_FFFFFF))
                this.itemView.lblStargazersCount.setTextColor(context.getColor(R.color.color_FFFFFF))
                this.itemView.txtStargazersCount.setTextColor(context.getColor(R.color.color_FFFFFF))
            }
        }
    }
}