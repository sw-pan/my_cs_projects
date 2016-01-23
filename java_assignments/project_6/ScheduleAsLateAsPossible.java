import java.util.*;

public class ScheduleAsLateAsPossible implements ScheduleMetric {

	/*
	 * This is the index in schedule where a new job can be inserted
	 */
	private int curretInsertIndex;

	/**
	 * This method will search from back and see if from the job's deadline we
	 * can find enough free slots. Slots in schedule are sorted, the head will
	 * always be the current latest slot.
	 * 
	 * @param schedule
	 * @param start
	 * @param end
	 * @return
	 */
	private int checkFree(Schedule schedule, int start, int end) {
		ScheduleSlot slot = schedule.schedule.getLast();
		// This duration is beyond the current last slot. so we can add this
		// element
		// the new highest slot will be the newly inserted one
		if ((slot.getStartTime() + slot.getJob().getDuration()) <= start) {
			curretInsertIndex = schedule.schedule.size();
			return end;
		}
		// If the start time is lower than the very first job
		slot = schedule.schedule.getFirst();
		if (end < slot.getStartTime()) {
			curretInsertIndex = 0;
			return end;
		}

		// Descending iterator: we are starting from highest occupied slot
		Iterator<ScheduleSlot> x = schedule.schedule.descendingIterator();
		int requiredSlots = end - start;
		int lastSlot = 0;
		int index = schedule.schedule.size();

		// We will find a gap. between two jobs. this loop will find using the
		// following loop
		// Gap is the start time of previous job - current job's end time (start
		// + duration)
		while (x.hasNext()) {

			slot = x.next();
			if (lastSlot > 0) {
				int slotEnd = slot.getStartTime() + slot.getJob().getDuration();
				if ((lastSlot - slotEnd) > requiredSlots) {
					// we have enough free slots, the new job can be inserted in
					// the schedule at the currentInsertIndex
					curretInsertIndex = index;
					return lastSlot;
				}
			}
			lastSlot = slot.getStartTime();
			index--;
		}
		curretInsertIndex = 0;
		return lastSlot;
	}

	@Override
	public boolean scheduleJob(Schedule schedule, Job job) {
		ScheduleSlot slot;

		// If the size is == 0 then will add simply return
		if (schedule.schedule.size() == 0) {
			slot = new ScheduleSlot(job, job.getDeadline() - job.getDuration());
			schedule.schedule.add(slot);
			return true;
		}

		// Check if we can find a place
		int endSlot = checkFree(schedule, job.deadline - job.duration,
				job.deadline);
		if (endSlot > 0) {
			// We got a slot which accommodate our duration, but we need to
			// check if we are equal or later than earliest start
			if ((endSlot - job.getDuration()) >= job.earliestStart) {
				slot = new ScheduleSlot(job, endSlot - job.getDuration());
				schedule.schedule.add(curretInsertIndex, slot);
				return true;
			}
		}

		return false;
	}
}