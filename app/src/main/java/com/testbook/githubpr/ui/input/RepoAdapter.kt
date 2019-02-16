package com.testbook.githubpr.ui.input

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.testbook.githubpr.R
import com.testbook.githubpr.models.Repo

class RepoAdapter(private val repos: List<Repo>, private val listener: InteractionListener) : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_repo, parent, false))
    }

    override fun getItemCount(): Int = repos.count()

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.repo_name).text = repos[position].name
        if (repos[position].description != null && repos[position].description is String) {
            holder.itemView.findViewById<TextView>(R.id.repo_description).visibility = View.VISIBLE
            holder.itemView.findViewById<TextView>(R.id.repo_description).text = repos[position].description as String
        } else {
            holder.itemView.findViewById<TextView>(R.id.repo_description).visibility = View.GONE
        }

        holder.itemView.findViewById<TextView>(R.id.repo_language).text = repos[position].language
        holder.itemView.setOnClickListener {
            listener.openResult(repos[position].name)
        }
    }

    class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}