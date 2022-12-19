import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OceanView {

	public static void main(String[] args) {
		int[] heights1 = { 4, 2, 3, 1 };
		int[] heights2 = { 4, 3, 2, 1 };
		int[] heights3 = { 1, 3, 2, 4 };
		int[] heights4 = { 2, 2, 2, 2 };
		Utility.printArray(oceanViewBuilding(heights1));
		Utility.printArray(oceanViewBuilding(heights2));
		Utility.printArray(oceanViewBuilding(heights3));
		Utility.printArray(oceanViewBuilding(heights4));
	}

	private static int[] oceanViewBuilding(int[] heights) {
		List<Integer> indexListWithOceanView = new ArrayList<>();
		int currentMaximum = 0;

		for (int i = heights.length - 1; i >= 0; i--) {
			int height = heights[i];
			if (height > currentMaximum) {
				indexListWithOceanView.add(i);
				currentMaximum = height;
			}
		}

		Collections.reverse(indexListWithOceanView);
		int[] indexArrayWithOceanView = new int[indexListWithOceanView.size()];
		for (int i = 0; i < indexListWithOceanView.size(); i++) {
			indexArrayWithOceanView[i] = indexListWithOceanView.get(i);
		}
		return indexArrayWithOceanView;
	}

}
