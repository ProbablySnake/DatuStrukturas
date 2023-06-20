/*
 * Author: Arturs Volkausks
 * e-mail: arturs.volkausks@outlook.com
 * 
 * Datu Strukturas LD1: Veselo skaitļu masīva apstrādāšana
 * http://dszc.daugavpils.rtu.lv/elibrary/DATA/IT000019/Task1.pdf
 */

import java.io.*;
import java.util.*;

public class LD1 {
	public static int[] readArray(String fileName) {
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

	public static int[] randomArray(int size, int min, int max) {
		Random rand = new Random();
		int[] array = new int[size];
		for (int i = 0; i < array.length; i++) {
			array[i] = rand.nextInt(max - min + 1) + min;
		}
		return array;
	}

	public static boolean writeArray(int[] array, String fileName) {
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

	public static void showArray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.format("%7d", array[i]);
			if (i % 10 == 9)
				System.out.println();
		}
		if (array.length % 10 != 0)
			System.out.println();
		System.out.println();
	}

	static void showArrayMarkMinMax(int[] array) {
		int minId = getMinIndex(array);
		int maxId = getMaxIndex(array);
		for (int i = 0; i < array.length; i++) {
			if (i == minId || i == maxId)
				System.out.format("%6d*", array[i]);
			else
				System.out.format("%7d", array[i]);
			if (i % 10 == 9)
				System.out.println();
		}
		if (array.length % 10 != 0)
			System.out.println();
		System.out.println();
	}

	static double getAverage(int[] array) {
		int sum = 0;
		for (int i = 0; i < array.length; i++)
			sum += array[i];
		return (double) sum / array.length;
	}

	static int getMinIndex(int[] array) {
		int min = array[0], idx = 0;
		for (int i = 1; i < array.length; i++)
			if (array[i] < min) {
				min = array[i];
				idx = i;
			}
		return idx;
	}

	static int getMaxIndex(int[] array) {
		int max = array[0], idx = 0;
		for (int i = 1; i < array.length; i++)
			if (array[i] > max) {
				max = array[i];
				idx = i;
			}
		return idx;
	}

	public static boolean isAscending(int[] array) {
		for (int i = 1; i < array.length; i++)
			if (array[i - 1] > array[i])
				return false;
		return true;
	}

	public static void main(String[] args) {
		int[] a = readArray("source.txt");
		if (a == null) {
			try {
				System.out.print("Input array size: ");
				Scanner input = new Scanner(System.in);
				a = randomArray(input.nextInt(), -10000, 10000);
				input.close();
			} catch (Exception e) {
				System.out.println("IO Error");
				return;
			}
		}
		showArrayMarkMinMax(a);
		System.out.printf("Average = %.2f\n", getAverage(a));
		System.out.printf("Min id = %d\n", getMinIndex(a));
		System.out.printf("Max id = %d\n", getMaxIndex(a));
		System.out.printf("Is ascending: %b\n", isAscending(a));
		writeArray(a, "result.txt");
	}
}
