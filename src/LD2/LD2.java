package LD2;

import java.io.*;
import java.util.*;

public class LD2 {
	static int[] readArray(String fileName) {
		try {
			Scanner file = new Scanner(new File(fileName));
			int[] array = new int[10000];
			int i = 0;
			while (file.hasNextInt())
				array[i++] = file.nextInt();
			file.close();
			return Arrays.copyOf(array, i);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	static int[] randomArray(int size, int min, int max) {
		Random rand = new Random();
		int[] array = new int[size];
		for (int i = 0; i < array.length; i++) {
			array[i] = rand.nextInt(max - min + 1) + min;
		}
		return array;
	}

	static boolean writeArray(int[] array, String fileName) {
		try {
			PrintWriter file = new PrintWriter(new FileWriter(fileName));
			for (int i = 0; i < array.length; i++)
				file.println(array[i]);
			file.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	static void showArray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.format("%7d", array[i]);
			if (i % 10 == 9)
				System.out.println();
		}
		if (array.length % 10 != 0)
			System.out.println();
	}

	static boolean isAscending(int[] array) {
		for (int i = 1; i < array.length; i++)
			if (array[i - 1] > array[i])
				return false;
		return true;
	}

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
			int[] a = randomArray(size, -10000, 10000);
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
		int[] a = readArray("source.txt");
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
				a = randomArray(input.nextInt(), -10000, 10000);
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
			showArray(a);

			System.out.println();
			System.out.println("Is Ascending: " + isAscending(a));
			writeArray(a, "result.txt");
		}

	}

}
