import java.util.Arrays;

public class KthLargestElement {

	public static void main(String[] args) {
		int[] arrSize = { 100000, 1000000, 10000000, 100000000 };
		int[] ks = { 10, 100, 1000, 10000, 100000, 1000000, 10000000 };

		for (int n : arrSize) {
			for (int k : ks) {
				if (k >= n)
					continue;

				System.out.println();
				System.out.println("For n = " + n + " and k = " + k);
				int[] arr = Utility.makeRandomArray(n);
				Utility.timeFunction(true, "sortingAlgorithm", () -> sortingAlgorithm(arr.clone(), k));
				Utility.timeFunction(true, "maxHeapAlgorithm", () -> maxHeapAlgorithm(arr.clone(), k));
				Utility.timeFunction(true, "minHeapAlgorithm", () -> minHeapAlgorithm(arr.clone(), k));
			}
		}
	}

	private static int sortingAlgorithm(int[] arr, int k) {
		Arrays.sort(arr);
		return arr[arr.length - k];
	}

	private static int minHeapAlgorithm(int[] arr, int k) {
		int[] minHeap = Arrays.copyOfRange(arr, 0, k);
		MinHeap.heapify(minHeap);
		for (int i = k; i < arr.length; i++) {
			if (minHeap[0] < arr[i]) {
				minHeap[0] = arr[i];
			}
			MinHeap.shiftDown(minHeap, k, 0);
		}
		return minHeap[0];
	}

	private static int maxHeapAlgorithm(int[] arr, int k) {
		MaxHeap.heapify(arr);
		int arrSize = arr.length;
		for (int i = 0; i < k - 1; i++) {
			arrSize--;
			arr[0] = arr[arrSize];
			MaxHeap.shiftDown(arr, arrSize, 0);
		}
		return arr[0];
	}
}
