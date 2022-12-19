import java.util.Arrays;

public class HeapSort {

	public static void main(String[] args) {
		int maxArraySize = 1000000;

		System.out.println("\n\nHEAP SORT\n");
		for (int arrSize = 100000; arrSize <= maxArraySize; arrSize = arrSize + 100000) {
			int[] arr = Utility.makeRandomArray(arrSize);
			long time = Utility.timeFunction(false, () -> heapSort(arr.clone()));
			System.out.print("(" + arrSize + "," + time + "),");
		}

		System.out.println("\n\nQUICK SORT\n");
		for (int arrSize = 100000; arrSize <= maxArraySize; arrSize = arrSize + 100000) {
			int[] arr = Utility.makeRandomArray(arrSize);
			long time = Utility.timeFunction(false, () -> quickSort(arr));
			System.out.print("(" + arrSize + "," + time + "),");
		}
	}

	public static int[] heapSort(int[] arr) {
		MaxHeap.heapify(arr);
		int arrSize = arr.length;
		do {
			arrSize--;
			Utility.swap(arr, arrSize, 0);
			MaxHeap.shiftDown(arr, arrSize, 0);
		} while (arrSize > 0);
		return arr;
	}

	private static int[] quickSort(int[] arr) {
		Arrays.sort(arr);
		return arr;
	}

}
