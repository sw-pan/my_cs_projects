import static org.junit.Assert.*;

import java.awt.List;
import java.util.ArrayList;
import java.util.ListIterator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JobSchedulerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testScheduleJobs() {
		ArrayList<Job> list = new ArrayList<Job>();
		Schedule schedule;

		Job j1 = new Job(1, 1, 6, 2, 10);
		list.add(j1);
		Job j2 = new Job(2, 3, 6, 3, 20);
		list.add(j2);
		Job j3 = new Job(3, 3, 9, 3, 15);
		list.add(j3);

		schedule = JobScheduler.scheduleJobs(list, Job.getProfitComparator,
				new ScheduleAsLateAsPossible());

		ListIterator<ScheduleSlot> listIterator = schedule.schedule
				.listIterator();
		int profit = 0;
		while (listIterator.hasNext()) {
			ScheduleSlot slot = listIterator.next();
			profit += slot.getJob().getProfit();
			System.out.println(" job id:" + slot.getJob().getId()
					+ " slot start Time: " + slot.getStartTime() + " to "
					+ (slot.getStartTime() + slot.getJob().getDuration()));
		}
		System.out.println("Total Profit with ScheduleAsLateAsPossible: "
				+ profit);

	}

	{
		ArrayList<Job> list = new ArrayList<Job>();
		Schedule schedule;

		Job j1 = new Job(1, 1, 10, 2, 10);
		list.add(j1);
		Job j2 = new Job(2, 1, 8, 2, 20);
		list.add(j2);
		Job j3 = new Job(3, 1, 6, 2, 30);
		list.add(j3);
		Job j4 = new Job(4, 1, 4, 2, 15);
		list.add(j4);
		schedule = JobScheduler.scheduleJobs(list, Job.getProfitComparator,
				new ScheduleAsLateAsPossible());

		ListIterator<ScheduleSlot> listIterator = schedule.schedule
				.listIterator();
		int profit = 0;
		while (listIterator.hasNext()) {
			ScheduleSlot slot = listIterator.next();
			profit += slot.getJob().getProfit();
			System.out.println(" job id:" + slot.getJob().getId()
					+ " slot start Time: " + slot.getStartTime() + " to "
					+ (slot.getStartTime() + slot.getJob().getDuration()));
		}
		System.out.println("Total Profit with ScheduleAsLateAsPossible: "
				+ profit);

	}
	{
		ArrayList<Job> list = new ArrayList<Job>();
		Schedule schedule;

		Job j1 = new Job(1, 1, 10, 2, 10);
		list.add(j1);
		Job j2 = new Job(2, 1, 8, 4, 20);
		list.add(j2);
		Job j3 = new Job(3, 1, 9, 6, 30);
		list.add(j3);
		Job j4 = new Job(4, 1, 6, 4, 15);
		list.add(j4);
		schedule = JobScheduler.scheduleJobs(list, Job.getProfitComparator,
				new ScheduleAsLateAsPossible());

		ListIterator<ScheduleSlot> listIterator = schedule.schedule
				.listIterator();
		int profit = 0;
		while (listIterator.hasNext()) {
			ScheduleSlot slot = listIterator.next();
			profit += slot.getJob().getProfit();
			System.out.println(" job id:" + slot.getJob().getId()
					+ " slot start Time: " + slot.getStartTime() + " to "
					+ (slot.getStartTime() + slot.getJob().getDuration()));
		}
		System.out.println("Total Profit with ScheduleAsLateAsPossible: "
				+ profit);

	}
	{
		ArrayList<Job> list = new ArrayList<Job>();
		Schedule schedule;

		Job j1 = new Job(1, 2, 6, 2, 10);
		list.add(j1);
		Job j2 = new Job(2, 3, 6, 3, 20);
		list.add(j2);
		Job j3 = new Job(3, 3, 9, 5, 15);
		list.add(j3);

		schedule = JobScheduler.scheduleJobs(list, Job.getProfitComparator,
				new ScheduleAsLateAsPossible());

		ListIterator<ScheduleSlot> listIterator = schedule.schedule
				.listIterator();
		int profit = 0;
		while (listIterator.hasNext()) {
			ScheduleSlot slot = listIterator.next();
			profit += slot.getJob().getProfit();
			System.out.println(" job id:" + slot.getJob().getId()
					+ " slot start Time: " + slot.getStartTime() + " to "
					+ (slot.getStartTime() + slot.getJob().getDuration()));
		}
		System.out.println("Total Profit with ScheduleAsLateAsPossible: "
				+ profit);
	}
}
