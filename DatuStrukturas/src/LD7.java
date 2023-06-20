/*
 * Author: Arturs Volkausks
 * e-mail: arturs.volkausks@outlook.com
 * 
 * Datu Strukturas LD7: Binārais koks
 * Virknes meklēšanas algoritmu salīdzināšana
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LD7 {

  static class Node {

    public int data;
    public Node right, left;

    Node(int Data) {
      data = Data;
      right = null;
      left = null;
    }
  }

  static void showLNR(Node root) {
    if (root == null)
      return;

    showLNR(root.left);
    System.out.format("%4d ", root.data);
    showLNR(root.right);
  }

  static void showNLR(Node root) {
    if (root == null)
      return;

    System.out.format("%4d ", root.data);
    showNLR(root.left);
    showNLR(root.right);
  }

  static void showLRN(Node root) {
    if (root == null)
      return;

    showLRN(root.left);
    showLRN(root.right);
    System.out.format("%4d ", root.data);
  }

  static int count(Node root) {
    if (root == null)
      return 0;

    return 1 + count(root.left) + count(root.right);
  }

  static int leafsCount(Node root) {
    if (root == null)
      return 0;

    if (root.left == null && root.right == null)
      return 1;

    return leafsCount(root.left) + leafsCount(root.right);
  }

  static int height(Node root) {
    if (root == null)
      return 0;

    int lHeight = height(root.left);
    int rHeight = height(root.right);

    return 1 + Math.max(lHeight, rHeight);
  }

  static int min(Node root) {
    if (root == null)
      return Integer.MAX_VALUE;

    int lMin = min(root.left);
    int rMin = min(root.right);

    return Math.min(root.data, Math.min(lMin, rMin));
  }

  static int max(Node root) {
    if (root == null)
      return Integer.MIN_VALUE;

    int lMax = max(root.left);
    int rMax = max(root.right);

    return Math.max(root.data, Math.max(lMax, rMax));
  }

  static boolean isSorted(Node root) {
    if (root == null)
      return true;

    if (root.left != null && root.data < root.left.data)
      return false;

    if (root.right != null && root.data > root.right.data)
      return false;

    return isSorted(root.left) && isSorted(root.right);
  }

  static boolean isBalanced(Node root) {
    if (root == null)
      return true;

    if (Math.abs(count(root.left) - count(root.right)) > 1)
      return false;

    return isBalanced(root.left) && isBalanced(root.right);
  }

  static Node addSorted(Node root, int data) {
    if (root == null)
      return new Node(data);

    if (root.data > data)
      root.left = addSorted(root.left, data);
    else
      root.right = addSorted(root.right, data);

    return root;
  }

  static Node addBalanced(Node root, int data) {
    if (root == null)
      return new Node(data);

    if (count(root.left) < count(root.right))
      root.left = addBalanced(root.left, data);
    else
      root.right = addBalanced(root.right, data);

    return root;
  }

  static Node readSorted(Node root, String fileName) {
    try {
      Scanner file = new Scanner(new File(fileName));

      while (file.hasNextInt())
        addSorted(root, file.nextInt());

      file.close();

      System.out.printf("Values added to sorted tree from file %s\n", fileName);
    } catch (FileNotFoundException e) {
      System.out.printf("File %s not found\n", fileName);
    }

    return root;
  }

  static Node readBalanced(Node root, String fileName) {
    try {
      Scanner file = new Scanner(new File(fileName));

      while (file.hasNextInt())
        addBalanced(root, file.nextInt());

      file.close();

      System.out.printf("Values added to balanced tree from file %s\n", fileName);
    } catch (FileNotFoundException e) {
      System.out.printf("File %s not found\n", fileName);
    }

    return root;
  }

  static void drawTree(Node[] nodes, int space) {
    for (Node node : nodes)
      if (node == null)
        System.out.print(" ".repeat(space - 1) + "·" + " ".repeat(space));
      else
        System.out.printf("%" + space + "d" + " ".repeat(space), node.data);

    System.out.println();

    if (space >= 4) {
      for (int i = 0; i < nodes.length; i++)
        System.out.print(" ".repeat(space / 2 - 1) + "+" + "-".repeat(space / 2 - 1) + "^"
            + "-".repeat(space / 2 - 1) + "+" + " ".repeat(space / 2));

      Node[] newNodes = new Node[nodes.length * 2];

      for (int i = 0; i < newNodes.length; i++) {
        if (nodes[i / 2] == null)
          newNodes[i] = null;
        else
          newNodes[i] = i % 2 == 0 ? nodes[i / 2].left : nodes[i / 2].right;
      }

      System.out.println();

      drawTree(newNodes, space / 2);
    }
  }

  static void drawTree(Node root, int maxDepth) {
    Node[] nodes = { root };
    drawTree(nodes, 2 << (maxDepth - 1));
  }

  static void drawTree(Node root) {
    Node[] nodes = { root };
    drawTree(nodes, 32);
  }

  static void printTreeInfo(Node root) {
    System.out.println("-".repeat(80));
    if (root == null)
      System.out.println("The tree is empty");
    else {
      System.out.print("LNR traversal: ");
      showLNR(root);

      System.out.print("\nNLR traversal: ");
      showNLR(root);

      System.out.print("\nLRN traversal: ");
      showLRN(root);

      System.out.printf("""

               Volume: %d
               Height: %d
                Leafs: %d
                Range: %d..%d
               Sorted: %s
             Balanced: %s
        """,
        count(root),
        height(root),
        leafsCount(root),
        min(root), max(root),
        isSorted(root) ? "YES" : "NO",
        isBalanced(root) ? "YES" : "NO"
      );
    }
    System.out.println("-".repeat(80));
  }

  static Node actions(Node root, Scanner input) {
    System.out.print("""
      You can:
        1. Clear the tree
        2. Add value to the sorted tree
        3. Add value to the balanced tree
        4. print tree
        5. Read values from a file to the sorted tree
        6. Read values from a file to the balanced tree
        0. Exit
      Your choice [0..5]: """);
    
    switch (input.nextInt()) {
      case 1:
        return null;
      case 2:
        System.out.print("Value to add: ");
        return addSorted(root, input.nextInt());
      case 3:
        System.out.print("Value to add: ");
        return addBalanced(root, input.nextInt());
      case 4:
        System.out.println("-".repeat(80));
        System.out.print("""
          Chose print order:
            1. LNR
            2. NLR
            3. LRN
          Your choice [1..3]: """);

          switch (input.nextInt()) {
            case 1:
              showLNR(root);
              break;
            case 2:
              showNLR(root);
              break;
            case 3:
              showLRN(root);
              break;
            default:
              showLNR(root);
              break;
          }

          break;
      case 5:
        System.out.print("File name to read from: ");
        return readSorted(root, input.next());
      case 6:
        System.out.print("File name to read from: ");
        return readBalanced(root, input.next());
      case 0:
        System.exit(0);
    }

    return root;
  }

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    Node root = null;
  
    while (true) {
      System.out.println();
      System.out.println("-".repeat(80));
      System.out.println();
      drawTree(root, 5);
      System.out.println();

      printTreeInfo(root);
      
      root = actions(root, input);
    }
  }
}
