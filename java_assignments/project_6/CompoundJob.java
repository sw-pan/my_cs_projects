import java.util.ArrayList;

/**
 * 
 * 
 *
 */
public class CompoundJob {
	ArrayList<Job> subJobs;

	public CompoundJob(Job... subJobs) {
		this.subJobs = new ArrayList<Job>();
		for (Job job : subJobs) {
			this.subJobs.add(job);
		}
	}

	ArrayList<Job> getSubJobs() {
		return subJobs;
	}
}
