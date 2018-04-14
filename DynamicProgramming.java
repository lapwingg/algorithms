// Czajka Kamil - grupa nr 1

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Locale;

class BST {
    public String key;
    public int p;
    public int q;
    public BST left;
    public BST right;

    public BST(String s, int ip, int iq) {
        p = ip;
        q = iq;
        key = s;
    }
}

public class Source {
    public static int costs[][]; // macierz kosztow
    public static int weights[][]; // macierz wag
    public static int roots[][]; // macierz w ktorej poszukuje korzen
    public static int q[];  // wejscie
    public static int p[];
    public static String keys[];
    public static int numberOfkeys;
    public static final String regex = "[^a-zA-Z]*([a-zA-Z]+)[^a-zA-Z]*";   // usuwanie zaszumien
    public static int minimumCost;
    public static int a;

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        int z = scanner.nextInt();

        while (z-- > 0) {
            scanner.next();
            numberOfkeys = scanner.nextInt();
            costs = new int[numberOfkeys + 1][numberOfkeys + 1];
            weights = new int[numberOfkeys + 1][numberOfkeys + 1];
            roots = new int[numberOfkeys + 1][numberOfkeys + 1];
            q = new int[numberOfkeys + 1];
            p = new int[numberOfkeys + 1];
            keys = new String[numberOfkeys + 1];

            q[0] = scanner.nextInt();
            for (int i = 1; i <= numberOfkeys; i++) {
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(scanner.next());
                matcher.matches();
                keys[i] = matcher.group(1);
                p[i] = scanner.nextInt();
                q[i] = scanner.nextInt();
                scanner.nextLine();
            }

            computeBSTTree();
        }
    }

    public static void computeBSTTree() {
        for (int i = 0; i <= numberOfkeys; i++) {
            weights[i][i] = q[i];
            for (int j = i + 1; j <= numberOfkeys; j++) {
                weights[i][j] = weights[i][j - 1] + p[j] + q[j];
            }
        }

        for (int i = 0; i <= numberOfkeys; i++) {
            costs[i][i] = weights[i][i];
        }
        for (int i = 0; i <= numberOfkeys - 1; i++) {
            int j = i + 1;
            costs[i][j] = costs[i][i] + costs[j][j] + weights[i][j];
            roots[i][j] = j;
        }
        for (int h = 2; h <= numberOfkeys; h++) {
            for (int i = 0; i <= numberOfkeys - h; i++) {
                int j = i + h;
                int m = roots[i][j - 1];
                int min = costs[i][m - 1] + costs[m][j];
                for (int k = m + 1; k <= roots[i + 1][j]; k++) {
                    int x = costs[i][k - 1] + costs[k][j];
                    if (x < min) {
                        m = k;
                        min = x;
                    }
                }
                costs[i][j] = weights[i][j] + min;
                roots[i][j] = m;
            }
        }

        minimumCost = 0;
        a = 0;
        display(constructBST(0, numberOfkeys), 0);
//		display();
        System.out.format(Locale.ENGLISH, "%.4f%n", ((float) minimumCost / weights[0][numberOfkeys]));//avg
    }

    public static int min(int i, int j) {
        int min = Integer.MAX_VALUE;
        for (int x = 0; x < (j - i + 1); x++) {
            int tmp = costs[i][i - 1 + x] + costs[i + 1 + x][j];
            if (min > tmp) {
                min = tmp;
            }
        }
        return min;
    }

    public static BST constructBST(int i, int j) {
        if (i == j) {
            return null;
        }

        BST nowy = new BST(keys[roots[i][j]], p[roots[i][j]], q[roots[i][j]]);
        nowy.left = constructBST(i, roots[i][j] - 1);
        nowy.right = constructBST(roots[i][j], j);
        return nowy;
    }

    public static void display(BST root, int level) {
        if (root != null) {
            display(root.left, level + 1);

            for (int i = 0; i <= 2 * level; i++) {
                System.out.print(".");
            }

            System.out.println(root.key);

            minimumCost += root.p * (level + 1);
            display(root.right, level + 1);
        } else {
            minimumCost += q[a] * level;
            a++;
        }
    }

    public static void display() {
        System.out.println("costs");
        for (int i = 0; i <= numberOfkeys; i++) {
            for (int j = 0; j <= numberOfkeys; j++) {
                System.out.print(costs[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("weights");
        for (int i = 0; i <= numberOfkeys; i++) {
            for (int j = 0; j <= numberOfkeys; j++) {
                System.out.print(weights[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
