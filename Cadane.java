//Kamil Czajka - grupa nr 7

import java.util.Scanner;

public class Cadane {
    public static Scanner scn = new Scanner(System.in);

    public static void main(String[] args) {
        int numberOfSets;

        numberOfSets = scn.nextInt();
        while (numberOfSets-- > 0) {
            int n, m;
            long maxSum = 0, currentSum = 0;
            int lineX = 0, lineY = 0, columnX = 0, columnY = 0, betterColumn = 0, elem = 0, maxSumElem = 0;
            boolean zero = false, data = false;

            n = scn.nextInt();
            m = scn.nextInt();

            long array[][] = new long[n][m];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    array[i][j] = scn.nextLong();
                }
            }

            for (int k = 0; k < n; k++) {
                for (int i = k; i < n; i++) {
                    for (int j = 0; j < m; j++) {
                        if (k != i) {
                            array[i][j] += array[i - 1][j];
                        }

                        elem += (i - k) + 1;
                        currentSum += array[i][j];

                        if (currentSum >= 0) {
                            data = true;
                        }

                        if (currentSum <= 0) {
                            if (currentSum == 0 && elem == 1 && maxSum == 0) {
                                if (zero == false) {
                                    lineX = i;
                                    lineY = i;
                                    columnX = j;
                                    columnY = j;
                                    maxSumElem = elem;
                                    zero = true;
                                }
                                betterColumn = j + 1;
                                elem = 0;
                            } else {
                                currentSum = 0;
                                betterColumn = j + 1;
                                elem = 0;
                            }
                        } else {
                            if (currentSum > maxSum) {
                                maxSum = currentSum;
                                lineX = k;
                                lineY = i;
                                columnX = betterColumn;
                                columnY = j;
                                maxSumElem = elem;
                            } else {
                                if (currentSum == maxSum) {
                                    if (elem < maxSumElem) {
                                        lineX = k;
                                        lineY = i;
                                        columnX = betterColumn;
                                        columnY = j;
                                        maxSumElem = elem;
                                    } else {
                                        if (elem == maxSumElem) {
                                            if (k < lineX) {
                                                lineX = k;
                                                lineY = i;
                                                columnX = betterColumn;
                                                columnY = j;
                                            } else {
                                                if (k == lineX) {
                                                    if (i < lineY) {
                                                        lineY = i;
                                                        columnX = betterColumn;
                                                        columnY = j;
                                                    } else {
                                                        if (i == lineY && betterColumn < columnX) {
                                                            columnX = betterColumn;
                                                            columnY = j;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    currentSum = 0;
                    betterColumn = 0;
                    elem = 0;
                }

                for (int i = n - 1; i > k; i--) {
                    for (int j = 0; j < m; j++) {
                        array[i][j] -= array[i - 1][j];
                    }
                }
            }

            if (data == false) {
                System.out.println("0");
            } else {
                System.out.println("max_sum=" + maxSum);
                System.out.println("[" + lineX + ".." + lineY + ", " + columnX + ".." + columnY + "]");
            }
        }
    }
}

/*
Test:

11
2 5
-1 -2 -3 -4 -5
-5 -4 -3 -2 0
1 5
-1 -2 0 -2 -1
4 4
0 0 0 0
0 0 1000 0
0 0 0 0
0 0 0 0
4 4
-1 -2 -3 -4
-4 -3 10 -2
-5 -1 10 -6
-2 -3 -4 -7
4 4
0 2 -1 -5
0 4 -2 -4
-1 -3 -4 -6
0 0 0 -1
3 3
-1 -2 -3
-3 10 -1
0 0 0
2 4
1000 250 -1 -10
-500 -250 -2 -5
3 3
0 0 0
0 -1 0
0 0 0
2 2
1 1
1 -6
1 6
1 1 1 2 -20 5
1 4
-1 0 1 1

max_sum=0
[1..1, 4..4]
max_sum=0
[0..0, 2..2]
max_sum=1000
[1..1, 2..2]
max_sum=20
[1..2, 2..2]
max_sum=6
[0..1, 1..1]
max_sum=10
[1..1, 1..1]
max_sum=1250
[0..0, 0..1]
max_sum=0
[0..0, 0..0]
max_sum=2
[0..0, 0..1]
max_sum=5
[0..0, 5..5]
max_sum=2
[0..0, 2..3]
*/
