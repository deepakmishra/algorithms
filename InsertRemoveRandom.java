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
public class InsertRemoveRandom {
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

	/**
	 * Demonstrates empirical probabilities before and after removing occurrences.
	 * Fixed seeds make the example reproducible. Sampling is a statistical
	 * sanity check, not a proof: results are compared with a six-standard-
	 * deviation binomial tolerance plus one sample for rounding.
	 */
	public static void main(String[] args) {
		InsertRemoveRandom numbers = new InsertRemoveRandom();
		Random inputGenerator = new Random(42);
		numbers.generator.setSeed(12345);
		Map<Integer, Integer> counts = new HashMap<>();

		for (int i = 0; i < 1000; i++) {
			int number = inputGenerator.nextInt(100) + 1;
			numbers.insert(number);
			counts.merge(number, 1, Integer::sum);
		}
		verifyProbabilities(numbers, counts, 10000, "After 1000 insertions");

		// Remove 200 randomly chosen existing occurrences, updating the reference
		// map independently. Rejection sampling skips numbers no longer present.
		for (int i = 0; i < 200; i++) {
			int number;
			do {
				number = inputGenerator.nextInt(100) + 1;
			} while (!counts.containsKey(number));

			numbers.remove(number);
			int remaining = counts.get(number) - 1;
			if (remaining == 0) {
				counts.remove(number);
			} else {
				counts.put(number, remaining);
			}
		}
		verifyProbabilities(numbers, counts, 10000, "After 200 removals");
	}

	private static void verifyProbabilities(InsertRemoveRandom numbers,
			Map<Integer, Integer> counts, int sampleCount, String phase) {
		int total = counts.values().stream().mapToInt(Integer::intValue).sum();
		if (total == 0 || sampleCount <= 0) {
			throw new IllegalArgumentException("Positive population and sample sizes required");
		}
		if (numbers.values.size() != total) {
			throw new AssertionError("Stored occurrence count differs from reference map");
		}

		Map<Integer, Integer> observedCounts = new HashMap<>();
		for (int i = 0; i < sampleCount; i++) {
			int number = numbers.random();
			if (!counts.containsKey(number)) {
				throw new AssertionError("Sampled an absent number: " + number);
			}
			observedCounts.merge(number, 1, Integer::sum);
		}

		System.out.println("\n" + phase + ": " + total + " occurrences");
		System.out.println("Number | Count | Expected % | Observed % | Result");
		boolean passed = true;
		for (int number = 1; number <= 100; number++) {
			int count = counts.getOrDefault(number, 0);
			int observed = observedCounts.getOrDefault(number, 0);
			double probability = (double) count / total;
			double expectedSamples = sampleCount * probability;
			double standardDeviation = Math.sqrt(sampleCount * probability * (1 - probability));
			boolean withinTolerance = Math.abs(observed - expectedSamples)
					<= 6 * standardDeviation + 1;
			passed &= withinTolerance;
			System.out.printf(java.util.Locale.ROOT, "%6d | %5d | %10.2f | %10.2f | %s%n",
					number, count, probability * 100,
					100.0 * observed / sampleCount, withinTolerance ? "PASS" : "FAIL");
		}
		if (!passed) {
			throw new AssertionError(phase + ": probability check failed");
		}
		System.out.println("PASS: all probabilities are within the sampling tolerance.");
	}
}
