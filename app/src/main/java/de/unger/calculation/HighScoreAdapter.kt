import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.unger.calculation.R
import de.unger.domain.entities.ResultOfExercises
import java.time.temporal.ChronoUnit

class HighScoreAdapter(
    private val dataSet: List<ResultOfExercises>
) : ListAdapter<ResultOfExercises, HighScoreAdapter.ResultOfExercisesViewHolder>(
    HighscoreDiffCallback
) {


    class ResultOfExercisesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val time: TextView
        val numberOfExercises: TextView

        init {
            // Define click listener for the ViewHolder's View
            name = view.findViewById(R.id.card_view_name)
            time = view.findViewById(R.id.card_view_time_per_exercise)
            numberOfExercises = view.findViewById(R.id.card_view_number_of_exercises)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ResultOfExercisesViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_view_high_score, viewGroup, false)
        return ResultOfExercisesViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ResultOfExercisesViewHolder, position: Int) {
        viewHolder.name.text = dataSet[position].name
        viewHolder.time.text =
            ChronoUnit.MILLIS.between(dataSet[position].startTime, dataSet[position].endTime)
                .div(dataSet[position].exercises)
                .toString()
        viewHolder.numberOfExercises.text = (100 - (dataSet[position].mistakes.times(100)
            .div(dataSet[position].exercises))).toString()
    }

    override fun getItemCount() = dataSet.size

    object HighscoreDiffCallback : DiffUtil.ItemCallback<ResultOfExercises>() {
        override fun areItemsTheSame(
            oldItem: ResultOfExercises,
            newItem: ResultOfExercises
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ResultOfExercises,
            newItem: ResultOfExercises
        ): Boolean {
            return oldItem.index == newItem.index
        }
    }
}