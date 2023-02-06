import java.util.Stack;

/**
 * @author for the code, new Games 24x7 interns batch of 2023 - Radhika, Shyam,
 *         Kedarnath and Royston guided by Deepak Mishra
 * @author for the documentation and refactoring, Deepak Mishra
 * 
 * @apiNote Assignment 2
 *          <p>
 *          These are the collated solutions of the java coding assignments that
 *          were given to new interns at the end of first week of training.
 *          Description of the problems are in the docs of respective function.
 *          Signatures of the solutions were fixed as below.
 *          </p>
 * @apiNote Node convergingPoint(Node head1, Node head2)
 * @apiNote boolean detectCycle(Node head)
 * @apiNote Implement using array <br/>
 *          class Queue { void enQueue(Object item); Object deQueue(); }
 * @apiNote Implement using array <br/>
 *          class Stack { void push(Object item); Object pop(); Object peek(); }
 * @apiNote Implement a stack with minimum function <br/>
 *          class StackX { void push(T item); T pop(); T peek(); T minimum(); }
 * 
 */
public class InternAssignment2 {

	/**
	 * Problem: Given heads of 2 linked lists, we have to find the intersection
	 * points of the 2 linked lists. If they don't intersect, return null.
	 * 
	 * @implNote Traverse the linked lists one by one and find their lengths.<br/>
	 *           Move ahead the head of the bigger linked list by the difference of
	 *           2 lengths.<br/>
	 *           Then traverse both linked lists together and if they intersect, the
	 *           two head pointers will collide.
	 * 
	 * @param head1 head node of the first linked list
	 * @param head2 head node of the second linked list
	 * @return the intersection node of the 2 linked lists or null if they don't
	 *         intersect
	 */
	public static Node convergingPoint(Node head1, Node head2) {
		int size1 = 0, size2 = 0;
		Node temp1 = head1, temp2 = head2;
		while (temp1 != null) {
			size1++;
			temp1 = temp1.next;
		}
		while (temp2 != null) {
			size2++;
			temp2 = temp2.next;
		}
		int difference = Math.abs(size1 - size2);
		temp1 = head1;
		temp2 = head2;

		if (size1 > size2) {
			for (int i = 0; i < difference; i++) {
				temp1 = temp1.next;
			}
		} else {
			for (int i = 0; i < difference; i++) {
				temp2 = temp2.next;
			}
		}

		while (temp1 != temp2) {
			temp1 = temp1.next;
			temp2 = temp2.next;
		}

		return temp1;
	}

	/**
	 * Problem: Given head of a linked list, check if the linked list has a cycle or
	 * loop.
	 * 
	 * @implNote Take 2 pointers one fast and one slow. Call them hare and tortoise
	 *           for fun.<br/>
	 *           Traverse with the 2 pointers together. Hare jumps 2 steps ahead and
	 *           tortoise jumps 1. <br/>
	 *           If hare reaches null, then no cycle or loop, so return false. <br/>
	 *           If hare and tortoise meet, then return true.<br/>
	 *           Assumption is that when tortoise will enter the loop, hare would
	 *           have already been in the loop for sometime, and so hare will catch
	 *           the tortoise in the loop from behind.
	 * 
	 * @param head first node of the linked list
	 * @return true, if linked list has cycle or loop, else false
	 */
	public static boolean detectCycle(Node head) {
		Node hare = head, tortoise = head;
		while (hare != null && hare.next != null) {
			tortoise = tortoise.next;
			hare = hare.next.next;
			if (tortoise == hare)
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
		Node n1 = new Node(100);
		Node n2 = new Node(200);
		Node n3 = new Node(300);
		Node n4 = new Node(400);
		Node n5 = new Node(500);
		Node n6 = new Node(600);
		Node n7 = new Node(700);
		Node n8 = new Node(800);
		Node n9 = new Node(900);
		Node n10 = new Node(1000);
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		n5.next = n6;
		n6.next = n7;
		n7.next = n8;
		n8.next = n9;
		n9.next = n10;
		n10.next = n5;

		System.out.println("DETECT CYCLE");
		System.out.println(detectCycle(n1));
		n10.next = null;
		System.out.println(detectCycle(n1));

		Node n11 = new Node(1100);
		Node n12 = new Node(1200);
		Node n13 = new Node(1300);
		Node n14 = new Node(1400);
		n11.next = n12;
		n12.next = n13;
		n13.next = n14;
		n14.next = n4;

		System.out.println("CONVERGING POINT");
		System.out.println(convergingPoint(n1, n11).data);

		// input for queue function
		System.out.println("Queue test starting");
		Queue q = new Queue(32);
		System.out.println(q.deQueue());
		q.enQueue(5);
		q.enQueue(7);
		q.enQueue(6);
		System.out.println(q.deQueue());
		q.enQueue(9);
		System.out.println(q.deQueue());

		// input for stack (array implementation)
		System.out.println("Stack test starting");
		StackA stackA = new StackA(32);
		stackA.push(10);
		stackA.push(20);
		stackA.push(30);
		stackA.push(40);
		System.out.println(stackA.peek());
		stackA.push(50);
		System.out.println(stackA.pop());
		stackA.push(10);
		System.out.println(stackA.pop());

		// input for stackX
		System.out.println("Stack X test starting");
		StackX<Integer> stackX = new StackX<>();
		stackX.push(50);
		stackX.push(40);
		stackX.push(30);
		System.out.println(stackX.peek());
		stackX.push(20);
		stackX.push(10);
		System.out.println(stackX.minimum());
		stackX.push(10);
		System.out.println(stackX.peek());
		System.out.println(stackX.pop());
		System.out.println(stackX.minimum());
		System.out.println(stackX.peek());
		System.out.println(stackX.pop());
		System.out.println(stackX.minimum());
		System.out.println(stackX.peek());
		System.out.println(stackX.pop());
		System.out.println(stackX.minimum());
		System.out.println(stackX.peek());
		System.out.println(stackX.pop());
		System.out.println(stackX.minimum());
		System.out.println(stackX.peek());
		System.out.println(stackX.pop());
		System.out.println(stackX.minimum());
		System.out.println(stackX.peek());
		System.out.println(stackX.pop());
		System.out.println(stackX.minimum());
		System.out.println(stackX.peek());
		System.out.println(stackX.pop());
		System.out.println(stackX.minimum());
	}

}

/**
 * 
 * @author for the code, new Games 24x7 interns batch of 2023 - Radhika, Shyam,
 *         Kedarnath and Royston guided by Deepak Mishra
 * @author for the documentation and refactoring, Deepak Mishra
 * 
 * @implNote Problem: Implement stack of fixed max size using array <br/>
 *           class Stack { void push(Object item); Object pop(); Object peek();
 *           }
 *
 */
class StackA {
	private int top = -1;
	private Object[] arr;

	public StackA(int capacity) {
		arr = new Object[capacity];
	}

	/**
	 * 
	 * @param item object to be pushed in the stack
	 */
	public void push(Object item) {
		if (top == arr.length - 1) {
			System.out.println("overflow");
			return;
		}
		arr[++top] = item;
	}

	/**
	 * 
	 * @return removes and returns the top item in the stack
	 */
	public Object pop() {
		if (top == -1) {
			System.out.println("underflow");
			return null;
		}
		return arr[top--];
	}

	/**
	 * 
	 * @return returns the top or latest item in the stack, but does not remove it
	 */
	public Object peek() {
		if (top == -1) {
			System.out.println("underflow");
			return null;
		}
		return arr[top];
	}
}

/**
 * 
 * @author for the code, new Games 24x7 interns batch of 2023 - Radhika, Shyam,
 *         Kedarnath and Royston guided by Deepak Mishra
 * @author for the documentation and refactoring, Deepak Mishra
 * 
 * @implSpec Problem: Implement stack of with additional minimum function<br/>
 *           All operations should be of constant order of time complexity<br/>
 *           class StackX { void push(T item); T pop(); T peek(); T minimum(); }
 * 
 * @implNote We have used generic to make it extensible to any class objects
 *           which implements Comparable
 * 
 * @implNote We are using 2 stacks. One to store elements and one to store
 *           minimum.<br/>
 *           Top of the minimum stack represents the minimum element currently
 *           in the stack.<br/>
 *           <br/>
 *           While push, <br/>
 *           If the item is greater than the minimum element, then it doesn't
 *           affect our minimum, so we safely ignore any minimum related
 *           changes.<br/>
 *           If the item is less than the minimum element, then we got a new
 *           minimum. We push it in the minimum stack as well.<br/>
 *           If the item is equal to the minimum element, then we still have to
 *           push it in the minimum stack, separately. Else while pop we might
 *           end up popping the minimum.<br/>
 *           Minimum element can be repetitive, so we are saving minimum element
 *           and its count as pair.<br/>
 *           <br/>
 *           While pop, <br/>
 *           If item to be removed is greater to the top of the minimum stack
 *           i.e. minimum element, then we can simply ignore the minimum
 *           stack.<br/>
 *           If item to be removed is equal to the minimum element, then we
 *           decrease the count of the item. If the count becomes 0, then we can
 *           pop it from the minimum stack. <br/>
 *           While peek, return the top of element stack. <br/>
 *           <br/>
 *           While minimum, return the value associated to the top of minimum
 *           stack.
 * 
 * 
 */
class StackX<T extends Comparable<T>> {
	private static class Pair<T extends Comparable<T>> {
		private T minimum;
		private int count;

		private Pair(T minimum, int count) {
			this.minimum = minimum;
			this.count = count;
		}
	}

	private Stack<T> elementStack = new Stack<>();
	private Stack<Pair<T>> minimumStack = new Stack<>();

	/**
	 * 
	 * @param item item to pushed in our element stack and corresponding minimum
	 *             stack
	 */
	public void push(T item) {
		elementStack.push(item);

		if (minimumStack.empty()) {
			minimumStack.push(new Pair<>(item, 1));
		} else {
			Pair<T> currentMinimum = minimumStack.peek();
			if (item.equals(currentMinimum.minimum)) {
				currentMinimum.count = currentMinimum.count + 1;
			} else if (item.compareTo(currentMinimum.minimum) < 0) {
				minimumStack.push(new Pair<>(item, 1));
			}
		}
	}

	/**
	 * 
	 * @return removes and returns the top of element stack and if condition
	 *         satisfies removes the top of the minimum stack as well
	 */
	public T pop() {
		if (elementStack.empty())
			return null;

		T item = elementStack.pop();

		Pair<T> currentMinimum = minimumStack.peek();

		if (item == currentMinimum.minimum) {
			currentMinimum.count--;
			if (currentMinimum.count == 0) {
				minimumStack.pop();
			}
		}

		return item;

	}

	/**
	 * 
	 * @return the top of the element stack
	 */
	public T peek() {
		if (elementStack.empty())
			return null;
		return elementStack.peek();
	}

	/**
	 * 
	 * @return the value of the top of the minimum stack
	 */
	public T minimum() {
		if (minimumStack.empty())
			return null;
		return minimumStack.peek().minimum;
	}

}

/**
 * 
 * @author for the code, new Games 24x7 interns batch of 2023 - Radhika, Shyam,
 *         Kedarnath and Royston guided by Deepak Mishra
 * @author for the documentation and refactoring, Deepak Mishra
 * 
 * @implNote Problem: Implement queue of fixed max size using array <br/>
 *           class Queue { void enQueue(Object item); Object deQueue(); }
 *
 */
class Queue {

	private int front;
	private int rear;
	private final Object[] queue;

	public Queue(int capacity) {
		this.front = this.rear = -1;
		this.queue = new Object[capacity];
	}

	/**
	 * 
	 * @param item item to be inserted in the queue
	 */
	public void enQueue(Object item) {
		if (size() == queue.length) {
			System.out.println("Overflow");
			return;
		}
		if (front == -1) {
			front = rear = 0;
			queue[rear] = item;
		} else if (rear == queue.length - 1 && front != 0) {
			rear = 0;
			queue[rear] = item;
		} else {
			rear++;
			queue[rear] = item;
		}
	}

	/**
	 * 
	 * @return removes and returns the oldest item in the queue
	 */
	public Object deQueue() {
		if (size() == 0) {
			System.out.println("Underflow");
			return null;
		}
		Object item = queue[front];
		if (front == rear) {
			front = rear = -1;
		} else if (front == queue.length - 1) {
			front = 0;
		} else {
			front = front + 1;
		}
		return item;
	}

	private int size() {
		if (front == -1)
			return 0;
		if (front > rear) {
			return queue.length - front + rear + 1;
		}
		return rear - front + 1;
	}
}

/**
 * 
 * This class is the utility class for Linked List problems
 *
 */
class Node {
	int data;
	Node next;

	public Node(int data) {
		this.data = data;
	}
}
