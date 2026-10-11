import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

/**
 * Problem: Design a data structure allowing duplicate integers with
 * void insert(int number), void remove(int number), and int random().
 * Each stored occurrence must have equal probability of being selected.
 * After inserting 2, 3, 4, 2, random returns 2 with probability 1/2,
 * and 3 and 4 with probability 1/4 each.
 *
 * remove deletes one occurrence; removing an absent number does nothing.
 * random on an empty collection throws NoSuchElementException.
 *
 * Solution: Store every occurrence in a dense list and map each number to
 * its set of list indices. Remove an occurrence by moving the last list
 * element into its position and updating both index sets. Sampling a
 * uniform list index makes each value's probability proportional to its
 * multiplicity. Insert/remove take expected amortized O(1) time; random
 * takes expected O(1) time. Space is O(n), including duplicate occurrences.
 * Instances are not thread-safe.
 */
public class RandomizedMultiset {
	private final List<Integer> values = new ArrayList<>();
	private final Map<Integer, Set<Integer>> indices = new HashMap<>();
	private final Random generator = new Random();

	public void insert(int number) {
		indices.computeIfAbsent(number, key -> new HashSet<>()).add(values.size());
		values.add(number);
	}

	public void remove(int number) {
		Set<Integer> positions = indices.get(number);
		if (positions == null) {
			return;
		}

		int removedIndex = positions.iterator().next();
		int lastIndex = values.size() - 1;
		int lastValue = values.get(lastIndex);
		positions.remove(removedIndex);

		if (removedIndex != lastIndex) {
			values.set(removedIndex, lastValue);
			Set<Integer> lastPositions = indices.get(lastValue);
			lastPositions.remove(lastIndex);
			lastPositions.add(removedIndex);
		}

		values.remove(lastIndex);
		// Check after moving: positions and lastPositions may be the same set.
		if (positions.isEmpty()) {
			indices.remove(number);
		}
	}

	public int random() {
		if (values.isEmpty()) {
			throw new NoSuchElementException("Collection is empty");
		}
		return values.get(generator.nextInt(values.size()));
	}

	public static void main(String[] args) {
		RandomizedMultiset numbers = new RandomizedMultiset();
		numbers.insert(2);
		numbers.insert(3);
		numbers.insert(4);
		numbers.insert(2);
		System.out.println(numbers.random()); // 2: 50%, 3: 25%, 4: 25%
	}
}
