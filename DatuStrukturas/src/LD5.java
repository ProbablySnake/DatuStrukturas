/*
 * Author: Arturs Volkausks
 * e-mail: arturs.volkausks@outlook.com
 * 
 * Datu Strukturas LD5: Virknes meklēšanas algoritmu salīdzināšana
 * http://dszc.daugavpils.rtu.lv/elibrary/DATA/IT000019/Task5.pdf
 */

 import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LD5 {
    static int searchSimple(String s, String p) {
        outer: for (int i = 0; i <= s.length() - p.length(); i++) {
            for (int j = 0; j < p.length(); j++)
                if (s.charAt(i + j) != p.charAt(j))
                    continue outer;

            return i;
        }

        return -1;
    }

    static int searchKMP(String s, String p) {
        int[] d = new int[p.length()];
        d[0] = 0;
        d[1] = 0;

        for (int i = 2; i < d.length; i++)
            for (int di = i - 1; di >= 0; di--)
                if (p.substring(0, di).equals(p.substring(i - di, i))) {
                    d[i] = di;
                    break;
                }

        int i = 0, j = 0;

        while (i <= s.length() - p.length()) {
            while (i < s.length() && j < p.length() && s.charAt(i) == p.charAt(j)) {
                i++;
                j++;
            }

            if (j >= p.length())
                return i - p.length();

            if (j == 0)
                i++;
            else
                j = d[j];
        }

        return -1;
    }

    static int searchBM(String s, String p) {
        int[] d = new int[128];
        for (int i = 0; i < d.length; i++)
            d[i] = -1;

        for (int i = 0; i < p.length(); i++) {
            d[p.charAt(i)] = i;
        }

        int i = p.length() - 1, j = i;

        while (i < s.length()) {
            while (j >= 0 && s.charAt(i) == p.charAt(j)) {
                i--;
                j--;
            }

            if (j < 0)
                return i + 1;

            if (d[s.charAt(i)] > j) {
                i = i + p.length() - j;
                j = p.length() - 1;
            } else {
                i = i + p.length() - d[s.charAt(i)] - 1;
                j = p.length() - 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        String fileName, pattern;
        int method;

        try {
            Scanner input = new Scanner(System.in);

            System.out.print("\nFile to search in: ");
            fileName = input.next();

            System.out.print("Pattern to search for: ");
            pattern = input.next();

            System.out.print("Method to search by (1 - simple, 2 - Knuth-Morris-Pratt, 3 - Boyer-Moore): ");
            method = input.nextInt();

            input.close();
        } catch (Exception e) {
            System.out.println("IO Error");
            return;
        }

        Scanner file;

        try {
            file = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        }

        int line = 1, found = 0;
        int pl = pattern.length();
        while (file.hasNext()) {
            String str = file.nextLine();

            int id = -1;
            switch (method) {
                case 1:
                    id = searchSimple(str, pattern);
                    break;
                case 2:
                    id = searchKMP(str, pattern);
                    break;
                case 3:
                    id = searchBM(str, pattern);
                    break;
            }

            if (id >= 0) {
                if (found == 0)
                    System.out.println(
                            "--------------------------------------------------------------------------------");
                System.out.printf("%2d:%2d => %s\n", line, id, str);
                System.out.println(" ".repeat(9 + id) + "~".repeat(pl));

                found++;
            }
            line++;
        }

        if (found > 0) {
            System.out.println("--------------------------------------------------------------------------------");
            System.out.printf("The pattern is found in %d lines", found);
        } else {
            System.out.println("Pattern not found");
        }
    }
}
