import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Problem: Design a data structure allowing duplicate integers with
 * void insert(int number), void remove(int number), and int random().
 * Each DISTINCT number currently present must have equal probability.
 * For example, after inserting 5, 5, and 9, random returns 5 or 9 with
 * probability 1/2 each, regardless of multiplicity.
 *
 * remove deletes one occurrence; removing an absent number does nothing.
 * random on an empty collection throws NoSuchElementException.
 *
 * Solution: Store multiplicities and an index into a dense list of distinct
 * values. On the last removal, swap the final list value into the removed
 * value's position. A uniformly sampled list index gives uniform values.
 * insert/remove take expected amortized O(1) time; random takes expected
 * O(1) time. Space is O(d), where d is the number of distinct values.
 * Instances are not thread-safe.
 */
public class RandomizedMultiset {
	private final List<Integer> values = new ArrayList<>();
	private final Map<Integer, Entry> entries = new HashMap<>();
	private final Random generator = new Random();

	private static final class Entry {
		int index;
		long count = 1;

		Entry(int index) {
			this.index = index;
		}
	}

	public void insert(int number) {
		Entry entry = entries.get(number);
		if (entry != null) {
			entry.count = Math.addExact(entry.count, 1);
			return;
		}
		entries.put(number, new Entry(values.size()));
		values.add(number);
	}

	public void remove(int number) {
		Entry entry = entries.get(number);
		if (entry == null) {
			return;
		}
		if (entry.count > 1) {
			entry.count--;
			return;
		}

		int lastIndex = values.size() - 1;
		int lastValue = values.get(lastIndex);
		if (entry.index != lastIndex) {
			values.set(entry.index, lastValue);
			entries.get(lastValue).index = entry.index;
		}
		values.remove(lastIndex);
		entries.remove(number);
	}

	public int random() {
		if (values.isEmpty()) {
			throw new NoSuchElementException("Collection is empty");
		}
		return values.get(generator.nextInt(values.size()));
	}

	public static void main(String[] args) {
		RandomizedMultiset numbers = new RandomizedMultiset();
		numbers.insert(5);
		numbers.insert(5);
		numbers.insert(9);
		numbers.remove(5);
		System.out.println(numbers.random()); // 5 or 9, equally likely
	}
}
