package com.muharram.test

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val TAG = "SecondFragment"
class SecondFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    interface Callbacks {
        fun onTaskSelected(taskId: UUID)
    }
    private var callbacks: Callbacks? = null
    private lateinit var inProgressRecyclerView: RecyclerView
    private var adapter: TaskAdapter? = TaskAdapter((emptyList()))

    private val taskListViewModel: TaskListViewModel by lazy {
        ViewModelProviders.of(this).get(TaskListViewModel::class.java)    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_second, container, false)
        inProgressRecyclerView = view.findViewById(R.id.in_progress_recycler_view) as RecyclerView
        inProgressRecyclerView.layoutManager = LinearLayoutManager(context)
        inProgressRecyclerView.adapter = adapter
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskListViewModel.taskListLiveData.observe(
            viewLifecycleOwner,
            Observer { tasks ->
                tasks?.let {
                    Log.i(TAG, "Got tasks ${tasks.size}")
                    updateUI(tasks)
                }
            })
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun updateUI(tasks: List<Task>) {
        adapter = TaskAdapter(tasks)
        inProgressRecyclerView.adapter=adapter
    }


    private inner class inProgressHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var task: Task

        private val titleTextView: TextView = itemView.findViewById(R.id.task_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.task_end_date)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(task: Task) {
            this.task = task
            titleTextView.text = this.task.title
            dateTextView.text = this.task.endDate.toString()
        }

        override fun onClick(v: View?) {
            callbacks?.onTaskSelected(task.id)
        }

    }

    private inner class TaskAdapter(var tasks: List<Task>)
        : RecyclerView.Adapter<inProgressHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : inProgressHolder {
            val view = layoutInflater.inflate(R.layout.task_details, parent, false)
            return inProgressHolder(view)
        }


        override fun onBindViewHolder(holder: inProgressHolder, position: Int) {
            val task = tasks[position]
            holder.bind(task)

        }

        override fun getItemCount() = tasks.size
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}