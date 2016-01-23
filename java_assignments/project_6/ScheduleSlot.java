public class ScheduleSlot {
	private Job job;
	private int startTime;

	public ScheduleSlot(Job job, int startTime) {
		this.job = job;
		this.startTime = startTime;
	}

	int getStartTime() {
		return startTime;
	}

	void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	Job getJob() {
		return job;
	}

	void setJob(Job job) {
		this.job = job;
	}
}
