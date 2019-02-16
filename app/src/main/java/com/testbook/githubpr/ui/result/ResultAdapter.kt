package com.testbook.githubpr.ui.result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.testbook.githubpr.R
import com.testbook.githubpr.models.pr.PullRequest
import com.testbook.githubpr.ui.input.InteractionListener

class ResultAdapter(private val prs: List<PullRequest>, private val listener: InteractionListener) :
    RecyclerView.Adapter<ResultAdapter.PRViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PRViewHolder {
        return PRViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_pull_request, parent, false))
    }

    override fun getItemCount(): Int = prs.count()

    override fun onBindViewHolder(holder: PRViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.pr_title).text = prs[position].title
        if (prs[position].body != null && prs[position].body is String) {
            holder.itemView.findViewById<TextView>(R.id.pr_description).visibility = View.VISIBLE
            holder.itemView.findViewById<TextView>(R.id.pr_description).text = prs[position].body as String
        } else {
            holder.itemView.findViewById<TextView>(R.id.pr_description).visibility = View.GONE
        }

        holder.itemView.findViewById<TextView>(R.id.pr_author).text = prs[position].user.login
        holder.itemView.setOnClickListener {
            listener.openResult(prs[position].htmlUrl)
        }
    }

    class PRViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}