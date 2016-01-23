import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class ScheduleAsEarlyAsPossible implements ScheduleMetric {

	/*
	 * This is the index in schedule where a new job can be inserted
	 */
	private int curretInsertIndex;

	/**
	 * Search for empty slots. The slots in schedule are sorted in increasing
	 * order We will search from beging if we can find some place
	 * 
	 * @param schedule
	 * @param start
	 * @param end
	 * @return
	 */
	private int checkFree(Schedule schedule, int start, int end) {
		ScheduleSlot slot = schedule.schedule.getLast();
		// If the start time is beyond the current maximum occupied slot we add
		// at the highest index, which is current list size
		if ((slot.getStartTime() + slot.getJob().getDuration()) <= start) {
			curretInsertIndex = schedule.schedule.size();
			return start;
		}
		// If lower than first occupied slot add at index = 0, lowest slot
		slot = schedule.schedule.getFirst();
		if (end < slot.getStartTime()) {
			curretInsertIndex = 0;
			return start;
		}

		// We will start from the begining of of slots and search for any gap
		// between two jobs
		Iterator<ScheduleSlot> x = schedule.schedule.iterator();
		int requiredSlots = end - start;
		int lastSlot = 0;
		int index = 0;
		while (x.hasNext()) {

			slot = x.next();
			if (lastSlot > 0) {
				int slotEnd = slot.getStartTime();
				if ((slotEnd - lastSlot) > requiredSlots) {
					curretInsertIndex = index;
					return lastSlot;
				}
			}
			lastSlot = slot.getStartTime() + slot.getJob().getDuration();
			index++;
		}
		// We have no gap, but try at the higest slot index
		curretInsertIndex = schedule.schedule.size();
		return lastSlot;
	}

	/*
	 * 
	 * @see ScheduleMetric#scheduleJob(Schedule, Job)
	 */
	@Override
	public boolean scheduleJob(Schedule schedule, Job job) {
		ScheduleSlot slot;

		if (schedule.schedule.size() == 0) {
			slot = new ScheduleSlot(job, job.earliestStart);
			schedule.schedule.add(slot);
			return true;
		}

		int startSlot = checkFree(schedule, job.earliestStart,
				job.earliestStart + job.duration);
		// We have a star slot, but it should be more than the earlist start but
		// enough slots for duration of job.
		if (startSlot > job.earliestStart) {
			if ((startSlot + job.duration) < job.deadline) {
				slot = new ScheduleSlot(job, startSlot);
				schedule.schedule.add(curretInsertIndex, slot);
				return true;
			}
		}
		return false;
	}
}
