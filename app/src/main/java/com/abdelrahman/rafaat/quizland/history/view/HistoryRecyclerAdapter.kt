package com.abdelrahman.rafaat.quizland.history.view

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdelrahman.rafaat.quizland.R
import com.abdelrahman.rafaat.quizland.model.Question

class HistoryRecyclerAdapter : RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder>() {

    private var questions: List<Question> = emptyList()
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view =
            LayoutInflater.from(context).inflate(R.layout.custom_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentQuestion = questions[position]

        holder.questionTextView.text =
            Html.fromHtml(currentQuestion.question, Html.FROM_HTML_MODE_COMPACT)
        holder.answerTextview.text =
            Html.fromHtml(currentQuestion.correct_answer, Html.FROM_HTML_MODE_COMPACT)

    }

    override fun getItemCount(): Int {
        return questions.size
    }

    fun setList(questions: List<Question>) {
        this.questions = questions
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var questionTextView: TextView = itemView.findViewById(R.id.question_textView)
        var answerTextview: TextView = itemView.findViewById(R.id.question_answer)

    }

}