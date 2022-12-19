import java.util.Random;

public class Utility {

	public static long timeFunction(boolean print, Runnable runnable) {
		return timeFunction(print, null, runnable);
	}

	public static long timeFunction(boolean print, String functionName, Runnable runnable) {
		long startTime = System.nanoTime();
		runnable.run();
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		if (print) {
			if (functionName == null) {
				System.out.println(duration + " nanoseconds");
			} else {
				System.out.println(functionName + ": " + duration + " nanoseconds");
			}
		}
		return duration;
	}

	public static int[] makeRandomArray(int arrSize) {
		int[] list = new int[arrSize];
		Random random = new Random();
		for (int i = 0; i < arrSize; i++) {
			list[i] = random.nextInt(1000000);
		}
		return list;
	}

	public static void swap(int[] arr, int index1, int index2) {
		int temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
	}

	public static void printArray(int[] arr) {
		System.out.println("========");
		System.out.println("Array of length " + arr.length);
		for (int n : arr) {
			System.out.print(n + " ");
		}
		System.out.println();
	}

	public static void printTree(int[] arr, boolean isHeap, boolean isMaxHeap) {
		System.out.println("========");
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
		System.out.println("----");

		StringBuilder errors = new StringBuilder();

		for (int i = 0; i < arr.length / 2; i++) {
			System.out.println();
			System.out.print(arr[i] + " ==> ");
			checkAndPrintChild(arr, isHeap, isMaxHeap, errors, i, 2 * i + 1);
			checkAndPrintChild(arr, isHeap, isMaxHeap, errors, i, 2 * i + 2);
		}

		System.out.println();
		if (errors.length() > 0) {
			System.out.println("There is an error in the tree");
			System.out.println(errors);
		}
		System.out.println("========");
	}

	private static void checkAndPrintChild(int[] arr, boolean isHeap, boolean isMaxHeap, StringBuilder errors,
			int parentIndex, int childIndex) {
		if (childIndex < arr.length) {
			if (isHeap && isMaxHeap == arr[childIndex] > arr[parentIndex])
				errors.append("parentIndex=" + parentIndex + ", childIndex=" + childIndex + ", parentValue="
						+ arr[parentIndex] + ", childValue=" + arr[childIndex] + "\n");
			System.out.print(" " + arr[childIndex]);
		}
	}

}
