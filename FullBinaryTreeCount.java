/**
 * Problem: Count possible full binary tree shapes for a given node count.
 * Every node has either zero or two children.
 * Left and right child positions are distinct.
 * Different assignments of node labels are not counted.
 *
 * Examples: 1 -> 1, 3 -> 1, 5 -> 2, 7 -> 5.
 *
 * Solution: Dynamic programming.
 * dp[i] += dp[j] * dp[i - 1 - j] for each odd left-subtree size j.
 * The root consumes one node.
 *
 * Time: O(n^2). Space: O(n).
 * Throws ArithmeticException if the result exceeds long's range.
 */
public class FullBinaryTreeCount {

    public static long count(int nodeCount) {
        if (nodeCount < 0) {
            throw new IllegalArgumentException("Node count cannot be negative");
        }

        if (nodeCount % 2 == 0) {
            return 0;
        }

        if (nodeCount == 1 || nodeCount == 3) {
            return 1;
        }

        // For odd node counts above 71, the result exceeds long's range.
        if (nodeCount > 71) {
            throw new ArithmeticException("Tree count exceeds long range");
        }

        long[] dp = new long[nodeCount + 1];
        dp[1] = 1;
        dp[3] = 1;

        for (int i = 5; i <= nodeCount; i += 2) {
            for (int j = 1; j < i - 1; j += 2) {
                dp[i] += dp[j] * dp[i - 1 - j];
            }
        }

        return dp[nodeCount];
    }

    public static void main(String[] args) {
        for (int n = 0; n <= 11; n++) {
            System.out.println(n + " nodes: " + count(n));
        }
    }
}
