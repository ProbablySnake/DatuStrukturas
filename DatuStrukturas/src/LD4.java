/*
 * Author: Arturs Volkausks
 * e-mail: arturs.volkausks@outlook.com
 * 
 * Datu Strukturas LD4: Atslēgas meklēšanas algoritmu salīdzināšana
 * http://dszc.daugavpils.rtu.lv/elibrary/DATA/IT000019/Task4.pdf
 */

 import java.util.Scanner;

public class LD4 {
    static void showArrayMark(int[] array, int markId) {
        for (int i = 0; i < array.length; i++) {
            if (i == markId)
                System.out.format("%7d^", array[i]);
            else
                System.out.format("%8d", array[i]);
            if (i % 10 == 9)
                System.out.println();
        }
        if (array.length % 10 != 0)
            System.out.println();
        System.out.println();
    }

    static int searchSequential(int[] array, int key) {
        for (int i = 0; i < array.length; i++)
            if (array[i] == key)
                return i;

        return -1;
    }

    static int searchBinary(int[] array, int key) {
        int l = 0, r = array.length - 1;

        while (l <= r) {
            int x = (l + r) / 2;

            if (array[x] == key)
                return x;
            else if (array[x] < key)
                l = x + 1;
            else
                r = x - 1;
        }

        return -1;
    }

    static int searchInterpolation(int[] array, int key) {
        int l = 0, r = array.length - 1;

        while (l <= r) {
            if (array[l] == array[r])
                if (array[l] == key)
                    return l;
                else
                    return -1;

            int x = l + (r - l) * (key - array[l]) / (array[r] - array[l]);

            if (x < l || x > r)
                return -1;

            if (array[x] == key)
                return x;
            else if (array[x] < key)
                l = x + 1;
            else
                r = x - 1;
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] array = LD1.readArray("source.txt");
        int key, id;
        int method;

        try {
            Scanner input = new Scanner(System.in);

            System.out.print("\nSelect search method (1: Sequential, 2: Binary, 3: Interpolation): ");
            method = input.nextInt();

            if (array == null) {
                System.out.print("\nInput random array size: ");
                array = LD1.randomArray(input.nextInt(), 100, 1000);
            }

            System.out.print("\nInput key to find: ");
            key = input.nextInt();

            input.close();
        } catch (Exception e) {
            System.out.println("IO Error");
            return;
        }

        LD3.sortQuick(array);

        switch (method) {
            case 1:
                id = searchSequential(array, key);
                break;
            case 2:
                id = searchBinary(array, key);
                break;
            case 3:
            default:
                id = searchInterpolation(array, key);
                break;
        }

        System.out.printf(id >= 0 ? "\nKey found at index: %d\n\n" : "\nKey not found\n\n", id);

        showArrayMark(array, id);

        System.out.printf("Is Ascending: %b", LD1.isAscending(array));

        LD1.writeArray(array, "result.txt");
    }
}
