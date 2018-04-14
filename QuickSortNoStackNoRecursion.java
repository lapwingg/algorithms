// Czajka Kamil - grupa nr 7

import java.util.Scanner;

public class Source {
    static Scanner in = new Scanner(System.in);
    static long[] tab;

    static void QuickSort(int left, int right) {
        int i = 0;

        while (left < right || i > 0) {
            if (left < right) {
                int q = Partition(left, right);
                if (q >= tab.length) {
                    return;
                }
                tab[right] = -tab[right];
                right = q - 1;
                i++;
            } else {
                right += 2;
                left = right;

                while (right < tab.length) {
                    if (tab[right] < 0) {
                        tab[right] = -tab[right];
                        break;
                    } else {
                        right++;
                    }
                }

                i--;
            }
        }
    }

    static long averageValue(int indA, int indB, int indC) {
        long a = tab[indA];
        long b = tab[indB];
        long c = tab[indC];
        long tmp;

        if ((a >= b && a <= c) || (a <= b && a >= c)) {
            return tab[indA];
        }
        if ((b >= a && b <= c) || (b <= a && b >= c)) {
            tmp = tab[indA];
            tab[indA] = tab[indB];
            tab[indB] = tmp;

            return tab[indA];
        }
        if ((c >= a && c <= b) || (c <= a && c >= b)) {
            tmp = tab[indA];
            tab[indA] = tab[indC];
            tab[indC] = tmp;

            return tab[indA];
        }

        return tab[indA];
    }

    static int Partition(int left, int right) {
        long x = averageValue(left, (left + right) / 2, right);
        int i = left;
        long tmp;
        boolean isEqualValue = true;

        for (int j = left + 1; j <= right; j++) {
            if (tab[j] != x) {
                isEqualValue = false;
            }

            if (tab[j] <= x) {
                i = i + 1;
                tmp = tab[i];
                tab[i] = tab[j];
                tab[j] = tmp;
            }
        }

        if (isEqualValue == true) {
            return right + 1;
        }

        tmp = tab[i];
        tab[i] = tab[left];
        tab[left] = tmp;

        return i;
    }

    public static void main(String[] args) {
        int sets, n;

        sets = in.nextInt();
        while (sets-- > 0) {
            n = in.nextInt();
            tab = new long[n];
            for (int i = 0; i < n; i++) {
                tab[i] = in.nextLong();
            }

            QuickSort(0, n - 1);
            for (int i = 0; i < n; i++) {
                if (tab[i] > 0) {
                    System.out.print(tab[i] + " ");
                } else {
                    System.out.print(-tab[i] + " ");
                }
            }
            System.out.println();
        }
    }
}