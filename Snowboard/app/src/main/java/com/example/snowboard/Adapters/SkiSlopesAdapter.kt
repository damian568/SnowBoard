package com.example.snowboard.Adapters

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.snowboard.Constants.Constants
import com.example.snowboard.Lists.SkiSlopesList
import com.example.snowboard.R
import com.google.android.material.card.MaterialCardView

class SkiSlopesAdapter(private val skiSlopesList: List<SkiSlopesList>) :
    RecyclerView.Adapter<SkiSlopesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_ski_slopes,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = skiSlopesList[position]

        holder.imageResort.setImageResource(currentItem.imgResort)
        holder.textTitle.text = currentItem.title
        holder.textDescription.text = currentItem.description
        holder.imageSkiSlopes.setImageResource(currentItem.imgSkiSlopes)
        holder.textLink.text = currentItem.link

        // 1. Set initial visibility of description
        holder.textDescription.visibility = if (currentItem.isExpanded) View.VISIBLE else View.GONE
        holder.imageSkiSlopes.visibility = if (currentItem.isExpanded) View.VISIBLE else View.GONE
        holder.textLink.visibility = if (currentItem.isExpanded) View.VISIBLE else View.GONE

        // 2. Set the initial rotation of the arrow (180 degrees if expanded, 0 if collapsed)
        holder.arrowButton.rotation = if (currentItem.isExpanded) 180f else 0f

        // 3. Handle the click event for the animation
        holder.cardView.setOnClickListener {
            // Toggle the boolean state
            currentItem.isExpanded = !currentItem.isExpanded

            // Animate the card expanding/collapsing
            TransitionManager.beginDelayedTransition(holder.cardView, AutoTransition())
            holder.textDescription.visibility =
                if (currentItem.isExpanded) View.VISIBLE else View.GONE
            holder.imageSkiSlopes.visibility =
                if (currentItem.isExpanded) View.VISIBLE else View.GONE
            holder.textLink.visibility =
                if (currentItem.isExpanded) View.VISIBLE else View.GONE

            val linkHtml = currentItem.link
            holder.textLink.text = HtmlCompat.fromHtml(linkHtml, HtmlCompat.FROM_HTML_MODE_LEGACY)
            // CRITICAL: This makes the link actually open the browser when clicked
            holder.textLink.movementMethod = android.text.method.LinkMovementMethod.getInstance()

            // 3. Animate the arrow flipping!
            val targetRotation = if (currentItem.isExpanded) 180f else 0f
            holder.arrowButton.animate()
                .rotation(targetRotation)
                .setDuration(Constants.SkiRotation)
                .start()
        }
    }

    override fun getItemCount(): Int {
        return skiSlopesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageResort: ImageView = itemView.findViewById(R.id.imgResort)
        val textTitle: TextView = itemView.findViewById(R.id.titleSkiSlopes)
        val textDescription: TextView = itemView.findViewById(R.id.descriptionSkiSlopes)
        val imageSkiSlopes: ImageView = itemView.findViewById(R.id.imgSkiSlopes)
        val textLink: TextView = itemView.findViewById(R.id.linkSkiSlopes)
        val cardView: MaterialCardView = itemView.findViewById(R.id.ski_slopes_cardView)
        val arrowButton: ImageView = itemView.findViewById(R.id.arrow_button)
    }
}