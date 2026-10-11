import java.util.NoSuchElementException;

/**
 * Problem: Implement an integer stack with push, pop, peek, and minimum,
 * each taking worst-case O(1) time. Duplicate values are allowed.
 *
 * Solution: Each linked node stores its value and the minimum of all values
 * at or below that node. Removing the top restores the previous minimum.
 * Space: O(n). Empty pop, peek, and minimum throw NoSuchElementException.
 */
public class MinimumStack {
	private Node top;

	private static final class Node {
		final int value;
		final int minimum;
		final Node next;

		Node(int value, Node next) {
			this.value = value;
			this.next = next;
			this.minimum = next == null ? value : Math.min(value, next.minimum);
		}
	}

	public void push(int number) {
		top = new Node(number, top);
	}

	public int pop() {
		requireNotEmpty();
		int value = top.value;
		top = top.next;
		return value;
	}

	public int peek() {
		requireNotEmpty();
		return top.value;
	}

	public int minimum() {
		requireNotEmpty();
		return top.minimum;
	}

	private void requireNotEmpty() {
		if (top == null) {
			throw new NoSuchElementException("Stack is empty");
		}
	}

	public static void main(String[] args) {
		MinimumStack stack = new MinimumStack();
		stack.push(4);
		stack.push(2);
		stack.push(2);
		System.out.println(stack.minimum()); // 2
		stack.pop();
		stack.pop();
		System.out.println(stack.minimum()); // 4
	}
}
