// Czajka Kamil - grupa nr 1 

import java.util.Scanner;

class BST {
    public char info;
    public BST left;
    public BST right;

    public BST(char c) {
        info = c;
        left = null;
        right = null;
    }
}

public class Source {
    public static Scanner sc = new Scanner(System.in);
    public static BST root;
    public static int leaf;
    public static int balancedNode;
    public static int unbalancedNode;

    public static String parse(String in) {
        String out = "";
        char tmp;
        boolean newCharacter = true;

        // do napisu koncowego dodawane sa znaki A-Z tylko te ktore nie powtorzyly sie wczesniej O(n^2)
        for (int i = 0; i < in.length(); i++) {
            tmp = in.charAt(i);
            if (tmp >= 'A' && tmp <= 'Z') {
                for (int j = 0; j < out.length(); j++) {
                    if (tmp == out.charAt(j)) {
                        newCharacter = false;
                        break;
                    }
                }
                if (newCharacter == true) {
                    out = out + tmp;
                }
                newCharacter = true;
            }
        }

        return out;
    }

    public static void construct(String in) {
        BST walk;
        BST node;
        char tmp;

        // O (log n)
        for (int i = 0; i < in.length(); i++) {
            tmp = in.charAt(i);
            if (i == 0) {
                root = new BST(tmp); // tworzenie korzenia
            } else {    // szukanie miejsca dla nowego wezla
                walk = root;
                while (true) {
                    if (tmp < walk.info) { // w lewo
                        if (walk.left != null) {
                            walk = walk.left;
                        } else {
                            node = new BST(tmp);
                            walk.left = node;
                            break;
                        }
                    } else {
                        if (walk.right != null) {    // w prawo
                            walk = walk.right;
                        } else {
                            node = new BST(tmp);
                            walk.right = node;
                            break;
                        }
                    }
                }
            }
        }
    }

    public static int max(int a, int b) {    // do obliczania wysokosci
        if (a >= b) {
            return a;
        } else {
            return b;
        }
    }

    public static int height(BST node) {
        if (node == null) {
            return -1;        // poziom nie istnieje dlatego trzeba odjac
        }

        return 1 + max(height(node.left), height(node.right));    // przejscie na kazdy kolejny poziom licznik ++
    }

    public static boolean isLeaf(BST node) {
        if (node.left != null || node.right != null) {    // istnieje dziecko
            return false;
        }

        return true;
    }

    public static boolean isBalancedNode(BST node) {
        int dh = height(node.left) - height(node.right);

        if (dh >= -1 && dh <= 1) {        // warunek wierzcholka zbalansowanego -1 <= dh <= 1
            return true;
        }

        return false;
    }

    public static void treeTraversal(BST node) {
        if (isLeaf(node) == true) {        // obliczanie ile jest lisci, wierzcholkow zbalansowanych, niezbalansowanych -> preorderBST
            leaf++;
        } else {
            if (isBalancedNode(node) == true) {
                balancedNode++;
            } else {
                unbalancedNode++;
            }
        }
        if (node.left != null) {
            treeTraversal(node.left);
        }
        if (node.right != null) {
            treeTraversal(node.right);
        }
    }

    public static long multiply(int i, int j) {
        long result = 1;

        for (int k = i; k <= j; k++) {
            result *= k;
        }

        return result;
    }

    public static int min(int a, int b) {
        if (a <= b) {
            return a;
        } else {
            return b;
        }
    }

    public static long newtonSymbol(int n, int k) {            // skrocenie pozwoli uniknac przekroczenia zakresu
        return multiply(max(n - k, k) + 1, n) / multiply(1, min(n - k, k));
    }

    public static int nodesInSubTree(BST node) {
        if (node == null) {
            return -1;
        }

        int left = 1 + nodesInSubTree(node.left);
        int right = 1 + nodesInSubTree(node.right);

        return left + right;
    }

    public static long permutationBST(BST node) {
        if (node.left == null && node.right == null) {
            return 1;
        }
        if (node.left != null && node.right == null) {
            return permutationBST(node.left);
        }
        if (node.right != null && node.left == null) {
            return permutationBST(node.right);
        }

        // Ilosc elementow wyznaczana jest dla wezla "node" dlatego trzeba dodac tez wezel zrodlowy
        int subTreeLeft = nodesInSubTree(node.left) + 1;
        int subTreeRight = nodesInSubTree(node.right) + 1;
        return permutationBST(node.left) * permutationBST(node.right) * newtonSymbol(subTreeLeft + subTreeRight, subTreeLeft);
    }

    public static void preOrderBST(BST node) {
        System.out.print(node.info);
        if (node.left != null) {
            preOrderBST(node.left);
        }
        if (node.right != null) {
            preOrderBST(node.right);
        }
    }

    public static void reversePreOrderBST(BST node) {
        System.out.print(node.info);
        if (node.right != null) {
            reversePreOrderBST(node.right);
        }
        if (node.left != null) {
            reversePreOrderBST(node.left);
        }
    }

    public static void reset() {
        root = null;
        leaf = 0;
        balancedNode = 0;
        unbalancedNode = 0;
    }

    public static void result() {
        treeTraversal(root);
        System.out.print(height(root) + "," + leaf + "," + balancedNode + "," + unbalancedNode + "," + permutationBST(root) + ",");
        preOrderBST(root);
        System.out.print(",");
        reversePreOrderBST(root);
        System.out.println();
    }

    public static void main(String[] args) {
        int z; // ilosc zestawow
        String input;

        z = sc.nextInt();
        input = sc.nextLine();  // enter
        for (int i = 0; i < z; i++) {
            if (i != 0) {
                reset();
            }

            construct(parse(sc.nextLine()));

            result();
        }
    }
}
