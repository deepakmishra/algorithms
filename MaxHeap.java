import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MaxHeap {

	public static void heapify(int[] arr) {
		for (int i = arr.length - 1; i >= 0; i--) {
			shiftDown(arr, arr.length, i);
		}
	}

	public static void shiftDown(int[] arr, int arrSize, int nodeIndex) {

		while (arrSize > nodeIndex) {
			int leftChildIndex = 2 * nodeIndex + 1;
			int rightChildIndex = 2 * nodeIndex + 2;

			if (leftChildIndex >= arrSize) {
				return;
			}

			int biggerChildIndex;
			if (rightChildIndex >= arrSize) {
				biggerChildIndex = leftChildIndex;
			} else {
				biggerChildIndex = (arr[rightChildIndex] > arr[leftChildIndex]) ? rightChildIndex : leftChildIndex;
			}

			if (arr[nodeIndex] >= arr[biggerChildIndex]) {
				return;
			}

			Utility.swap(arr, nodeIndex, biggerChildIndex);

			nodeIndex = biggerChildIndex;
		}
	}

	public static void shiftUp(int[] arr, int nodeIndex) {
		while (nodeIndex > 0) {
			int parentIndex = (nodeIndex - 1) / 2;
			if (arr[parentIndex] > arr[nodeIndex]) {
				return;
			}

			Utility.swap(arr, nodeIndex, parentIndex);
			nodeIndex = parentIndex;
		}
	}

	public static void print(int[] arr) {
		Utility.printTree(arr, true, true);
	}

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			list.add(new Random().nextInt(10000));
		}

		int[] arr = list.stream().mapToInt(i -> i).toArray();
//		print(arr);
		heapify(arr);
		print(arr);
	}
}
