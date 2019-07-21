package com.tim24.investmentteam24.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tim24.investmentteam24.R
import com.tim24.investmentteam24.addUnit
import com.tim24.investmentteam24.data.Goal
import com.tim24.investmentteam24.toRupiah
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_recycleview_goal.*

class GoalAdapter(private val context: Context?,
                  private val items: MutableList<Goal>,
                  private val detailListener: (Goal) -> Unit)
    : RecyclerView.Adapter<GoalAdapter.ViewHolder>() {

    // Using XML:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_recycleview_goal, parent, false)
        val holder = ViewHolder(v)
        holder.itemView.setOnClickListener {
            if (holder.adapterPosition < items.size) {
                detailListener(items[holder.adapterPosition])
            }
        }

        holder.depositButton.setOnClickListener {
            if (holder.adapterPosition < items.size) {
                detailListener(items[holder.adapterPosition])
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(items: Goal) {
            val strRemaining = "${items.currentBalance.toRupiah()} / ${items.targetBalance.toRupiah()}"

            /*progressBar.progress = items.currentBalance
                .toBigInteger()
                .multiply(BigInteger.valueOf(100))
                .divide(items.targetBalance.toBigInteger())
                .toInt()*/
            strGoalName.text = items.title
            strAmount.text = strRemaining
            strPeriod.text = items.period.addUnit("years from now")
            strRecommendation.text = items.getRecommendation()
        }
    }
}