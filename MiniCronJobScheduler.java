import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MiniCronJobScheduler {

	private static final MiniCronJobScheduler single_instance = new MiniCronJobScheduler();

	public static MiniCronJobScheduler getInstance() {
		return single_instance;
	}

	private MiniCronJobScheduler() {
		thread.start();
	}

	private final PriorityQueue<CronJob> jobQueue = new PriorityQueue<>();
	private final ExecutorService executor = Executors.newWorkStealingPool();

	private final Thread thread = new Thread() {

		@Override
		public void run() {
			while (true) {
				synchronized (jobQueue) {
					if (jobQueue.isEmpty()) {
						try {
							jobQueue.wait();
						} catch (InterruptedException e) {
						}
					}
					CronJob job = jobQueue.peek();
					if (job.getNextExecutionTime() > System.currentTimeMillis()) {
						try {
							jobQueue.wait(job.getNextExecutionTime() - System.currentTimeMillis());
						} catch (InterruptedException e) {
						}
					} else {
						jobQueue.poll();
						executor.execute(job);
						boolean rescheduleAllowed = job.scheduledNextExecutionTime();
						if (rescheduleAllowed) {
							addCronJob(job);
						}
					}
				}
			}
		}
	};

	public void addCronJob(CronJob job) {
		synchronized (jobQueue) {
			jobQueue.add(job);
			jobQueue.notify();
		}
	}

	public void addCronJobs(CronJob... jobs) {
		synchronized (jobQueue) {
			jobQueue.addAll(Arrays.asList(jobs));
			jobQueue.notify();
		}
	}

	public static void main(String[] args) {
		NameRandomPrinter.test();
	}
}

abstract class CronJob implements Runnable, Comparable<CronJob> {
	private long nextExecutionTime;
	private float periodInSeconds;
	private int remainingRuns;

	public CronJob(float delayInSeconds, float periodInSeconds) {
		reschedule(delayInSeconds, periodInSeconds, Integer.MAX_VALUE);
	}

	public CronJob(float delayInSeconds, float periodInSeconds, int maximumRuns) {
		reschedule(delayInSeconds, periodInSeconds, maximumRuns);
	}

	public final void reschedule(float delayInSeconds, float periodInSeconds) {
		reschedule(delayInSeconds, periodInSeconds, Integer.MAX_VALUE);
	}

	public final void reschedule(float delayInSeconds, float periodInSeconds, int maximumRuns) {
		if (delayInSeconds < 0) {
			throw new IllegalArgumentException("Delay can not be negative integer");
		}
		if (periodInSeconds <= 0) {
			throw new IllegalArgumentException("Period has to be positive integer");
		}
		this.periodInSeconds = periodInSeconds;
		this.nextExecutionTime = System.currentTimeMillis() + (long) (delayInSeconds * 1000);
		this.remainingRuns = maximumRuns > 0 ? maximumRuns : Integer.MAX_VALUE;
	}

	public final boolean scheduledNextExecutionTime() {
		remainingRuns--;
		if (remainingRuns <= 0) {
			return false;
		}
		this.nextExecutionTime = this.nextExecutionTime + (long) (periodInSeconds * 1000);
		return true;
	}

	public final long getNextExecutionTime() {
		return nextExecutionTime;
	}

	@Override
	public final int compareTo(CronJob other) {
		return this.nextExecutionTime < other.nextExecutionTime ? -1 : 1;
	}
}

class NameRandomPrinter extends CronJob {

	private static final Random random = new Random();
	private final String name;

	private NameRandomPrinter(String name, float periodInSeconds) {
		this(name, 0, periodInSeconds, 0);
	}

	private NameRandomPrinter(String name, float delayInSeconds, float periodInSeconds, int maximumRuns) {
		super(delayInSeconds, periodInSeconds, maximumRuns);
		this.name = name;
	}

	@Override
	public void run() {
		System.out.println(name + " " + random.nextInt(100000));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}

	static void test() {
		CronJob job1 = new NameRandomPrinter("Alfa", 3.5f, 0.30f, 10);
		CronJob job2 = new NameRandomPrinter("Bravo", 1, 0.50f, 100);
		CronJob job3 = new NameRandomPrinter("Charlie", 0, 0.80f, 1000);

		MiniCronJobScheduler cronjobScheduler = MiniCronJobScheduler.getInstance();
		cronjobScheduler.addCronJobs(job1, job2, job3);
	}
}
