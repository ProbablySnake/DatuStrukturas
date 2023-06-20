/*
 * Author: Arturs Volkausks
 * e-mail: arturs.volkausks@outlook.com
 * 
 * Datu Strukturas LD2: Masīvu šķirošanas vienkāršu metožu salīdzināšana
 * http://dszc.daugavpils.rtu.lv/elibrary/DATA/IT000019/Task2.pdf
 */

 import java.util.Arrays;
import java.util.Scanner;

public class LD2 {
	static int getMinIndexInRange(int[] array, int minId, int maxId) {
		int min = array[minId], idx = minId;
		for (int i = minId + 1; i < maxId; i++)
			if (array[i] < min) {
				min = array[i];
				idx = i;
			}
		return idx;
	}

	public static void swap(int[] array, int pos1, int pos2) {
		int temp = array[pos1];
		array[pos1] = array[pos2];
		array[pos2] = temp;
	}

	public static void sortBuble(int[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			boolean swapped = false;

			for (int j = 0; j < array.length - 1 - i; j++) {
				if (array[j] > array[j + 1]) {
					swap(array, j, j + 1);
					swapped = true;
				}
			}

			if (!swapped)
				break;
		}
	}

	public static void sortInsertion(int[] array) {
		for (int i = 1; i < array.length; i++)
			for (int j = i - 1; j >= 0; j--)
				if (array[j] > array[j + 1])
					swap(array, j, j + 1);
				else
					break;
	}

	public static void sortSelection(int[] array) {
		for (int i = 0; i < array.length - 1; i++)
			swap(array, i, getMinIndexInRange(array, i, array.length));
	}

	static void sortBenchmark(int size, int repeats) {
		long buble = 0, insertion = 0, selection = 0;
		for (int r = 0; r < repeats; r++) {
			int[] a = LD1.randomArray(size, -10000, 10000);
			int[] b = Arrays.copyOf(a, size), c = Arrays.copyOf(a, size);

			long start = System.currentTimeMillis();
			sortBuble(a);
			buble = buble + System.currentTimeMillis() - start;

			start = System.currentTimeMillis();
			sortInsertion(b);
			insertion = insertion + System.currentTimeMillis() - start;

			start = System.currentTimeMillis();
			sortSelection(c);
			selection = selection + System.currentTimeMillis() - start;
		}

		System.out.println();
		System.out.printf("Speed test (size: %d, repeats: %d):%n", size, repeats);
		System.out.printf("Buble:     %.2f ms%n", (float) buble / repeats);
		System.out.printf("Insertion: %.2f ms%n", (float) insertion / repeats);
		System.out.printf("Selection: %.2f ms%n", (float) selection / repeats);
	}

	public static void main(String[] args) {
		int[] a = LD1.readArray("source.txt");
		int method = 0;
		try {
			Scanner input = new Scanner(System.in);

			System.out.print("Select sort method (Buble: B, Insertion: I, Selection: S) (Def: Buble): ");
			String str = input.next();
			switch (str) {
				case "I":
				case "i":
					method = 1;
					break;
				case "S":
				case "s":
					method = 2;
					break;
				case "test":
					method = 3;
					break;
			}

			if (a == null && method != 3) {
				System.out.print("Input array size: ");
				a = LD1.randomArray(input.nextInt(), -10000, 10000);
			}

			input.close();
		} catch (Exception e) {
			System.out.println("IO Error");
			return;
		}

		switch (method) {
			case 1:
				sortInsertion(a);
				break;
			case 2:
				sortSelection(a);
				break;
			case 3:
				sortBenchmark(10000, 10);
				break;
			default:
				sortBuble(a);
		}

		if (method != 3) {
			System.out.println();
			LD1.showArray(a);

			System.out.println();
			System.out.println("Is Ascending: " + LD1.isAscending(a));
			LD1.writeArray(a, "result.txt");
		}
	}
}
