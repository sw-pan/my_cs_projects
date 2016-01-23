import java.util.Comparator;

/**
 * This class higher precision decimal numbers than are available with the
 * primitive double and long types.
 * 
 * 
 */
public class Job implements Comparable<Job> {
	// id a unique value that identifies the job
	int id;

	// earliestStart the earliest time at which the job may be started
	int earliestStart;

	// deadline the latest time at which the job must complete
	int deadline;

	// duration the length of time it takes to complete the job
	int duration;

	// profit the amount of profit to be earned if the job is completed
	int profit;

	public Job(int id, int earliestStart, int deadline, int duration, int profit) {
		this.id = id;
		this.earliestStart = earliestStart;
		this.deadline = deadline;
		this.duration = duration;
		this.profit = profit;
	}

	int getId() {
		return id;
	}

	int getEarliestStart() {
		return earliestStart;
	}

	int getDeadline() {
		return deadline;
	}

	int getDuration() {
		return duration;
	}

	int getProfit() {
		return profit;
	}

	void setId(int id) {
		this.id = id;
	}

	void setEarliestStart(int earliestStart) {
		this.earliestStart = earliestStart;
	}

	void setDeadline(int deadline) {
		this.deadline = deadline;
	}

	void setDuration(int duration) {
		this.duration = duration;
	}

	void setProfit(int profit) {
		this.profit = profit;
	}

	public static Comparator<Job> getStartComparator = new Comparator<Job>() {

		public int compare(Job j1, Job j2) {
			return j1.earliestStart - j2.earliestStart;
		}
	};

	public static Comparator<Job> getProfitComparator = new Comparator<Job>() {

		public int compare(Job j1, Job j2) {
			return j2.profit - j1.profit;
		}
	};

	@Override
	public int compareTo(Job j) {
		return earliestStart - j.earliestStart;
	}

}
