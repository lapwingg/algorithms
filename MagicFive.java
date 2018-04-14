//Czajka Kamil - grupa nr 7

import java.util.Scanner;

public class MagicFive {
    public static Scanner in = new Scanner(System.in);
    public static int size;
    public static int[] value;
    public static int[] key;

    public static void sort(int[] array, int l, int r) {
        int str, j;

        for (int i = l + 1; i < r; i++) {
            j = i - 1;
            str = array[i];
            while (j >= l && str <= array[j]) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = str;
        }
    }

    public static int Select2(int[] array, int k, int n) {
        if (n < 50) {
            sort(array, 0, n);
            return array[k];
        } else {
            int N = n / 5;
            int l, r, m;
            int[] mediumElements = new int[N];
            int isLessThan = 0, isEqual = 0, isGreaterThan = 0;

            // sortowanie kolejnych piatek liczb
            for (int i = 0; i < N; i++) {
                if (i * 5 < n) {
                    l = i * 5;
                } else {
                    l = n - 1;
                }

                if ((i * 5) + 4 < n) {
                    r = i * 5 + 4;
                } else {
                    r = n - 1;
                }

                sort(array, l, r);

                if (r - l == 5) {
                    mediumElements[i] = array[i * 5 + 2];
                } else {
                    mediumElements[i] = array[(l + r) / 2];
                }
            }

            //wybor pivota
            if (N % 2 == 0) {
                m = Select2(mediumElements, N / 2 - 1, N);
            } else {
                m = Select2(mediumElements, (N / 2), N);
            }

            for (int i = 0; i < n; i++) {
                if (array[i] < m) {
                    isLessThan++;
                } else {
                    if (array[i] == m) {
                        isEqual++;
                    } else {
                        isGreaterThan++;
                    }
                }
            }

            // warunki koncowe - operacje nie-na-indexach
            int[] next;
            if (k + 1 <= isLessThan) {
                next = new int[isLessThan];
                for (int i = 0, j = 0; i < n; i++) {
                    if (array[i] < m) {
                        next[j++] = array[i];
                    }
                }

                return Select2(next, k, isLessThan);
            } else {
                if (k + 1 <= isLessThan + isEqual) {
                    return m;
                } else {
                    next = new int[isGreaterThan];
                    for (int i = 0, j = 0; i < n; i++) {
                        if (array[i] > m) {
                            next[j++] = array[i];
                        }
                    }

                    return Select2(next, k - (isLessThan + isEqual), isGreaterThan);
                }
            }
        }
    }

    public static void main(String[] args) {
        int z, n, m;

        z = in.nextInt();

        while (z-- > 0) {
            size = in.nextInt();
            value = new int[size];
            for (int i = 0; i < size; i++) {
                value[i] = in.nextInt();
            }

            m = in.nextInt();
            key = new int[m];
            for (int i = 0; i < m; i++) {
                key[i] = in.nextInt();
            }

            int result;
            for (int i = 0; i < m; i++) {
                if (key[i] - 1 < 0 || key[i] - 1 >= size) {
                    System.out.println(key[i] + " brak");
                } else {
                    result = Select2(value, key[i] - 1, size);
                    System.out.println(key[i] + " " + result);
                }
            }
        }
    }
}

/*
Input: // baza rekurencji n = 5
6  
5  
1 2 3 4 5 
3  
1 2 3
 
5  
5 3 4 4 3 
5 
2 5 1 3 4

10 
1 1 1 1 1 1 1 1 1 1 
5 
1 10 0 -1 11

20
1 5 7 9 3 2 10 121 22 33 4 5 67 7 5 4 3 2 0 1
10
0 1 2 3 4 5 6 7 8 9

1
0
10
0 1 2 3 4 5 6 7 8 9

10
4 6 2 3 6 0 9 1 2 3
12
0 1 2 3 4 5 6 7 8 9 10 11

Output:
1 1
2 2
3 3

2 3
5 5
1 3
3 4
4 4

1 1
10 1
0 brak
-1 brak
11 brak

0 brak
1 0
2 1
3 1
4 2
5 2
6 3
7 3
8 4
9 4

0 brak
1 0
2 brak
3 brak
4 brak
5 brak
6 brak
7 brak
8 brak
9 brak

0 brak
1 0
2 1
3 2
4 2
5 3
6 3
7 4
8 6
9 6
10 9
11 brak

*/
