import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class JobScheduler {

	public static Schedule scheduleJobs(List<Job> jobs,
			Comparator<Job> compartor, ScheduleMetric scheduleMetric) {

		Collections.sort(jobs, compartor);
		Schedule schedule = new Schedule();

		for (Job job : jobs) {
			System.out.println(job.id + " " + job.earliestStart + " "
					+ job.deadline + " " + job.duration + " " + job.profit);
			scheduleMetric.scheduleJob(schedule, job);
		}

		return schedule;
	}

	public static void main(String[] args) {
		List<Job> list = new ArrayList<Job>();
		Schedule schedule;

		String line;

		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(
					"src/jobdata.txt"));

			while ((line = bufferedReader.readLine()) != null) {
				try {
					Scanner scanner = new Scanner(line);
					if (scanner != null) {
						int id = scanner.nextInt();
						int start = scanner.nextInt();
						int deadline = scanner.nextInt();
						int duration = scanner.nextInt();
						int profit = scanner.nextInt();
						Job job = new Job(id, start, deadline, duration, profit);
						list.add(job);
						System.out.println(id + " " + start + " " + deadline
								+ " " + duration + " " + profit);
					}
				}
				catch (NoSuchElementException e) {
				}
			}
			bufferedReader.close();

		}
		catch (IOException e) {
			System.out.println("Can't open file");
		}

		schedule = scheduleJobs(list, Job.getProfitComparator,
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

		schedule = scheduleJobs(list, Job.getProfitComparator,
				new ScheduleAsEarlyAsPossible());

		ListIterator<ScheduleSlot> listIterator2 = schedule.schedule
				.listIterator();
		profit = 0;
		while (listIterator2.hasNext()) {
			ScheduleSlot slot = listIterator2.next();
			profit += slot.getJob().getProfit();
			System.out.println(" job id:" + slot.getJob().getId()
					+ " slot start Time: " + slot.getStartTime() + " to "
					+ (slot.getStartTime() + slot.getJob().getDuration()));
		}
		System.out.println("Total Profit with ScheduleAsEarlyAsPossible: "
				+ profit);

	}

}
