import java.math.BigInteger;

/**
 * Problem: Count full binary trees using a fixed set of n unique node labels.
 * Every node has either zero or two children. Left and right child positions
 * are distinct, and assigning labels differently produces different trees.
 * This is not a binary-search-tree problem.
 *
 * Solution: A full tree has n = 2k + 1 nodes. There are Catalan(k) ordered
 * shapes and n! assignments of the unique labels, so the answer is
 * Catalan(k) * n!. Positive even n and zero return zero; negative n is invalid.
 * Examples: n=1 -> 1, n=3 -> 6, n=5 -> 240.
 *
 * BigInteger prevents numeric overflow. The algorithm uses O(n) arithmetic
 * operations and a constant number of BigInteger variables; actual time and
 * memory grow with the size of the numbers.
 */
public class FullBinaryTreeCount {
	public static BigInteger count(int nodeCount) {
		if (nodeCount < 0) {
			throw new IllegalArgumentException("Node count cannot be negative");
		}
		if (nodeCount == 0 || nodeCount % 2 == 0) {
			return BigInteger.ZERO;
		}

		int internalNodes = (nodeCount - 1) / 2;
		BigInteger catalan = BigInteger.ONE;
		// C(i+1) = C(i) * 2 * (2i+1) / (i+2).
		for (int i = 0; i < internalNodes; i++) {
			catalan = catalan.multiply(BigInteger.valueOf(2L * (2L * i + 1)))
					.divide(BigInteger.valueOf((long) i + 2));
		}

		BigInteger factorial = BigInteger.ONE;
		for (long i = 2; i <= nodeCount; i++) {
			factorial = factorial.multiply(BigInteger.valueOf(i));
		}
		return catalan.multiply(factorial);
	}

	public static void main(String[] args) {
		for (int n = 1; n <= 7; n += 2) {
			System.out.println(n + " nodes: " + count(n));
		}
	}
}
