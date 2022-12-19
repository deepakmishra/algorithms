import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinHeap {

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

			int smallerChildIndex;
			if (rightChildIndex >= arrSize) {
				smallerChildIndex = leftChildIndex;
			} else {
				smallerChildIndex = (arr[rightChildIndex] < arr[leftChildIndex]) ? rightChildIndex : leftChildIndex;
			}

			if (arr[nodeIndex] <= arr[smallerChildIndex]) {
				return;
			}

			Utility.swap(arr, nodeIndex, smallerChildIndex);

			nodeIndex = smallerChildIndex;
		}
	}

	public static void shiftUp(int[] arr, int arrSize, int nodeIndex) {
		while (nodeIndex > 0) {
			int parentIndex = (nodeIndex - 1) / 2;
			if (arr[parentIndex] < arr[nodeIndex]) {
				return;
			}

			Utility.swap(arr, nodeIndex, parentIndex);
			nodeIndex = parentIndex;
		}
	}

	public static void print(int[] arr) {
		Utility.printTree(arr, true, false);
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
