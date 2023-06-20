/*
 * Author: Arturs Volkausks
 * e-mail: arturs.volkausks@outlook.com
 * 
 * Datu Strukturas LD3: Masīvu šķirošanas metožu salīdzināšana
 * http://dszc.daugavpils.rtu.lv/elibrary/DATA/IT000019/Task3.pdf
 */

 import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class LD3 {
	static void sortShell(int[] array) {
		for (int h = array.length / 2; h > 0; h = h / 2)
			for (int i = h; i < array.length; i++)
				for (int j = i - h; j >= 0; j = j - h)
					if (array[j] > array[j + 1])
						LD2.swap(array, j, j + 1);
					else
						break;
	}

	static void sortBetterShell(int[] array) {
		int h = 1;
		while (true)
			if (h * 3 + 1 < array.length)
				h = h * 3 + 1;
			else
				break;

		for (; h > 0; h = (h - 1) / 3)
			for (int i = h; i < array.length; i++)
				for (int j = i - h; j >= 0; j = j - h)
					if (array[j] > array[j + 1])
						LD2.swap(array, j, j + 1);
					else
						break;
	}

	static void sortQuick(int[] array, int l, int r) {
		if (l >= r)
			return;
		int p = array[(l + r) / 2], li = l, ri = r;
		while (true) {
			while (array[li] < p)
				li++;
			while (array[ri] > p)
				ri--;
			if (li >= ri)
				break;
			LD2.swap(array, li, ri);
			li++;
			ri--;
		}
		sortQuick(array, l, ri);
		sortQuick(array, ri + 1, r);
	}

	static void sortQuick(int[] array) {
		sortQuick(array, 0, array.length - 1);
	}

	static void sortBenchmark() {
		int size = 100_000;
		long buble, insertion, selection, shell, betterShell, quick;

		int[] a = LD1.randomArray(size, -10000, 10000);
		int[] b = Arrays.copyOf(a, size),
				c = Arrays.copyOf(a, size),
				d = Arrays.copyOf(a, size),
				e = Arrays.copyOf(a, size),
				f = Arrays.copyOf(a, size);

		long start = System.currentTimeMillis();
		LD2.sortBuble(a);
		buble = System.currentTimeMillis() - start;

		start = System.currentTimeMillis();
		LD2.sortInsertion(b);
		insertion = System.currentTimeMillis() - start;

		start = System.currentTimeMillis();
		LD2.sortSelection(c);
		selection = System.currentTimeMillis() - start;

		start = System.currentTimeMillis();
		sortShell(d);
		shell = System.currentTimeMillis() - start;

		start = System.currentTimeMillis();
		sortBetterShell(e);
		betterShell = System.currentTimeMillis() - start;

		start = System.currentTimeMillis();
		sortQuick(f);
		quick = System.currentTimeMillis() - start;

		System.out.println();
		System.out.printf("Buble:        %.2f ms%n", (float) buble);
		System.out.printf("Insertion:    %.2f ms%n", (float) insertion);
		System.out.printf("Selection:    %.2f ms%n", (float) selection);
		System.out.printf("Shell:        %.2f ms%n", (float) shell);
		System.out.printf("Better Shell: %.2f ms%n", (float) betterShell);
		System.out.printf("Quick:        %.2f ms%n", (float) quick);

		try {
			PrintWriter file = new PrintWriter(new FileWriter("bencmark_results.txt", true));

			file.printf("%n%10.2f ms %10.2f ms %10.2f ms %10.2f ms %10.2f ms %10.2f ms",
					(float) buble,
					(float) insertion,
					(float) selection,
					(float) shell,
					(float) betterShell,
					(float) quick);

			file.close();
		} catch (IOException err) {
			err.printStackTrace();
		}
	}

	public static void main(String[] args) {
		int[] a = LD1.readArray("source.txt");
		String method = "";

		try {
			Scanner input = new Scanner(System.in);

			System.out.print("Select sort method (buble, insert, select, shell, shell2, quick, quick, test): ");
			method = input.next();

			if (a == null && !method.equals("test")) {
				System.out.print("Input random array size: ");
				a = LD1.randomArray(input.nextInt(), 100, 1000);
			}

			input.close();
		} catch (Exception e) {
			System.out.println("IO Error");
			return;
		}

		switch (method) {
			case "buble":
				LD2.sortBuble(a);
				break;
			case "insert":
				LD2.sortInsertion(a);
				break;
			case "select":
				LD2.sortSelection(a);
				break;
			case "shell":
				sortShell(a);
				break;
			case "shell2":
				sortBetterShell(a);
				break;
			case "quick":
				sortQuick(a);
				break;
			case "test":
				sortBenchmark();
				return;
			default:
				LD2.sortBuble(a);
				break;
		}

		System.out.println();
		LD1.showArray(a);

		System.out.println();
		System.out.println("Is Ascending: " + LD1.isAscending(a));
		LD1.writeArray(a, "result.txt");
	}
}
