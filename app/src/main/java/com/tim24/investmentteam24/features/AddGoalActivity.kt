package com.tim24.investmentteam24.features

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tim24.investmentteam24.NumberTextWatcherForThousand
import com.tim24.investmentteam24.R
import com.tim24.investmentteam24.data.Goal
import com.tim24.investmentteam24.repo.GoalRepoImpl
import com.tim24.investmentteam24.submitInput
import com.tim24.investmentteam24.trimCommaOfString
import kotlinx.android.synthetic.main.activity_add_goals.*
import org.jetbrains.anko.longToast

class AddGoalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_goals)

        inputTargetAmount.addTextChangedListener(NumberTextWatcherForThousand(inputTargetAmount))
        val goalRepo = GoalRepoImpl(this)
        saveButton.setOnClickListener {
            goalRepo.addGoal(Goal(
                inputTitle.submitInput(),
                "0",
                trimCommaOfString(inputTargetAmount.submitInput()),
                (inputTargetYear.submitInput().toInt() - 2019).toString()
            ))
            longToast("Goal added succesfully")
            finish()
        }
        cancelButton.setOnClickListener {
            finish()
        }
    }
}
