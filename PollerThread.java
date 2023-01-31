import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * @author Deepak Mishra
 * 
 * @apiNote Just an object that we will use to pass as queue message
 *
 */
class Human {
	private int age;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}

/**
 * 
 * @author Deepak Mishra
 * 
 * @apiNote A utility class to maintain count of open producer thread
 *
 */
class Counter {

	Counter(int value) {
		this.value = value;
	}

	private int value;

	public int getValue() {
		return this.value;
	}

	public void decrement() {
		this.value--;
	}
}

/**
 * 
 * @author Deepak Mishra
 * 
 * @apiNote PollerProducer
 *          <p>
 *          PollerProducer threads makes 100000 Human objects with random age
 *          and put in the queue.
 *          </p>
 */
class PollerProducer implements Runnable {
	private final Queue<Human> queue;

	private final Counter counter;

	PollerProducer(Queue<Human> queue, Counter counter) {
		this.queue = queue;
		this.counter = counter;
	}

	public void run() {
		Random random = new Random();
		for (int i = 0; i < 100000; i++) {
			int age = random.nextInt(100);
			Human human = new Human();
			human.setAge(age);
			synchronized (queue) {
				queue.add(human);
				queue.notify();
			}
		}
		counter.decrement();
	}
}

/**
 * 
 * @author Deepak Mishra
 * 
 * @apiNote AnalyserConsumer
 *          <p>
 *          AnalyserConsumer threads have a shared Map in which we are counting
 *          people of every age.<br/>
 *          Each of these threads fetches Human objects from the queue and
 *          increment the counter of the age in the Map.
 *          </p>
 * 
 */
class AnalyserConsumer implements Runnable {
	private final Counter counter;
	private final Queue<Human> queue;

	private final Map<Integer, AtomicLong> countMap;

	AnalyserConsumer(Queue<Human> queue, Map<Integer, AtomicLong> ageCountMap, Counter counter) {
		this.queue = queue;
		this.countMap = ageCountMap;
		this.counter = counter;
	}

	public void run() {
		while (counter.getValue() > 0 || !queue.isEmpty()) {
			if (queue.isEmpty()) {
				synchronized (queue) {
					try {
						queue.wait();
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			}
			Human human = null;
			synchronized (queue) {
				if (!queue.isEmpty()) {
					human = queue.poll();
				}
			}

			if (human != null) {
				countMap.putIfAbsent(human.getAge(), new AtomicLong());
				countMap.get(human.getAge()).incrementAndGet();
			}
		}
	}
}

/**
 * @author Deepak Mishra
 * 
 * 
 * @apiNote Problem: Poller Thread
 *          <p>
 *          This is just an example to demonstrate multi-threading to trainees.
 *          There are shared resources across thread. Here queue is mainly the
 *          resource every thread is synchronizing on.<br/>
 * 
 *          PollerProducer will produce data in queue and AnalyserConsumer will
 *          consume data from the queue.
 *          </p>
 * 
 * @implNote PollerProducer
 *           <p>
 *           Each of these threads makes 100000 Human objects with random age
 *           and put in the queue.
 *           </p>
 * 
 * @implNote AnalyserConsumer
 *           <p>
 *           AnalyserConsumer threads have a shared Map in which we are counting
 *           people of every age.<br/>
 *           Each of these threads fetches Human objects from the queue and
 *           increment the counter of the age in the Map.
 *           </p>
 */
public class PollerThread {

	public static void main(String[] args) {
		Queue<Human> queue = new LinkedList<>();
		Map<Integer, AtomicLong> ageCountMap = new ConcurrentHashMap<>();
		Counter counter = new Counter(1);

		Thread[] pollers = new Thread[counter.getValue()];
		for (int i = 0; i < pollers.length; i++) {
			pollers[i] = new Thread(new PollerProducer(queue, counter));
		}
		for (Thread t : pollers) {
			t.start();
		}

		Thread[] analysers = new Thread[3];
		for (int i = 0; i < analysers.length; i++) {
			analysers[i] = new Thread(new AnalyserConsumer(queue, ageCountMap, counter));
		}
		for (Thread t : analysers) {
			t.start();
		}

		for (Thread t : pollers) {
			try {
				t.join();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		for (Thread t : analysers) {
			try {
				t.join();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		for (Entry<Integer, AtomicLong> ageCountEntry : ageCountMap.entrySet()) {
			System.out.println(ageCountEntry.getKey() + "=" + ageCountEntry.getValue());
		}

		int sum = ageCountMap.values().stream().mapToInt(AtomicLong::intValue).sum();
		System.out.println("total=" + sum);
	}
}
