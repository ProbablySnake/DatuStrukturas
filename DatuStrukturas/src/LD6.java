/*
 * Author: Arturs Volkausks
 * e-mail: arturs.volkausks@outlook.com
 * 
 * Datu Strukturas LD6: Saistītais saraksts
 * http://dszc.daugavpils.rtu.lv/elibrary/DATA/IT000019/Task6.pdf
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class LD6 {
    static class ListItem {
        String data;
        ListItem next;

        ListItem(String data, ListItem next) {
            this.data = data;
            this.next = next;
        }
    }

    static void show(ListItem head) {
        System.out.println("-".repeat(80));

        int count = 0;
        while (head != null) {
            System.out.printf("%2d: %s\n", count, head.data);
            head = head.next;
            count++;
        }

        if (count == 0)
            System.out.println("List is empty");

        System.out.println("-".repeat(80));
    }

    static int count(ListItem head) {
        return (head == null) ? 0 : (count(head.next) + 1);
    }

    static ListItem add(ListItem head, String data) {
        if (head == null)
            return new ListItem(data, null);

        head.next = add(head.next, data);
        return head;
    }

    static ListItem insert(ListItem head, String data, int pos) {
        if (pos == 0 || head == null)
            return new ListItem(data, head);

        head.next = insert(head.next, data, pos - 1);
        return head;
    }

    static ListItem delete(ListItem head, int pos) {
        if (pos == 0)
            return head.next;

        head.next = delete(head.next, pos - 1);
        return head;
    }

    static ListItem readFromFile(ListItem head, String fileName) {
        try {
            Scanner file = new Scanner(new File(fileName));

            while (file.hasNextLine())
                head = add(head, file.nextLine());

            file.close();

            System.out.printf("List is loaded from file %s\n", fileName);
        } catch (FileNotFoundException e) {
            System.out.printf("File %s not found\n", fileName);
        }

        return head;
    }

    static boolean writeToFile(ListItem head, String fileName) {
        try {
            PrintWriter file = new PrintWriter(new FileWriter(fileName));

            while (head != null) {
                file.println(head.data);
                head = head.next;
            }

            file.close();

            System.out.printf("List is saved to file %s\n", fileName);
            return true;
        } catch (IOException e) {
            System.out.printf("Error while saving to file %s\n", fileName);
            return false;
        }
    }

    static void sort(ListItem head) {
        if (head == null || head.next == null) {
            return;
        }

        for (ListItem i = head.next; i != null; i = i.next) {
            ListItem j = head;
            while (j != i) {
                if (j.data.compareTo(i.data) > 0) {
                    String temp = j.data;
                    j.data = i.data;
                    i.data = temp;
                }
                j = j.next;
            }
        }
    }

    static void printActions() {
        System.out.println("\nActions:");
        System.out.println("  1. Show the list");
        System.out.println("  2. Count items");
        System.out.println("  3. Add string");
        System.out.println("  4. Insert string");
        System.out.println("  5. Delete string");
        System.out.println("  6. Clear the list");
        System.out.println("  7. Append strings from a file");
        System.out.println("  8. Write the list to a file");
        System.out.println("  9. Sort alphabetically");
        System.out.println("  0. Exit\n");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ListItem list = readFromFile(null, "list.txt");
        show(list);
        System.out.printf("List contains %d strings\n", count(list));
        printActions();

        menu: while (true) {
            System.out.print("Choice action [0..9]: ");

            switch (input.next()) {
                case "1":
                    show(list);
                    break;
                case "2":
                    System.out.printf("\nList contains %d strings\n\n", count(list));
                    break;
                case "3":
                    System.out.print("String to add: ");
                    list = add(list, input.next());
                    break;
                case "4":
                    System.out.print("String to insert: ");
                    String data = input.next();
                    System.out.print("String position to insert: ");
                    list = insert(list, data, input.nextInt());
                    break;
                case "5":
                    System.out.print("String position to delete: ");
                    list = delete(list, input.nextInt());
                    break;
                case "6":
                    list = null;
                    break;
                case "7":
                    System.out.print("File name to read from: ");
                    list = readFromFile(list, input.next());
                    break;
                case "8":
                    System.out.print("File name to write to: ");
                    writeToFile(list, input.next());
                    break;
                case "9":
                    sort(list);
                    break;
                case "0":
                    break menu;
                default:
                    printActions();
                    break;
            }
        }

        input.close();
    }
}
