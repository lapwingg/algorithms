//Czajka Kamil - grupa nr 7
import java.util.Scanner;

public class Source {
    public static Scanner scn = new Scanner(System.in);
    public static long[] a;
    public static long[] tmp;
    
    public static long merge(int left, int m, int right, long inv){
        for (int i = left; i <= right; i++) tmp[i] = a[i];
        int i = left , j = m + 1 , k = left;   
        
        while (i <= m  &&  j <= right) { 
            if (tmp[i] <= tmp[j]){ 
                a[k++] = tmp[i++]; 
            }
            else{  
            	inv += j - k;
                a[k++] = tmp[j++];
            }
        } 

        while (i <= m){    
            a[k++] = tmp[i++];
        } 
        while (j <= right){
            a[k++] = tmp[j++];
        }

        return inv;
    }
    
    public static long numInver(int left, int right, long inv){        
        if (left >= right){
             return inv;
        }
         
         int m = (left + right) >> 1;  
         inv = numInver(left, m, inv); 
         inv = numInver(m + 1, right, inv); 
         inv += merge(left, m, right, 0);
        
         return inv;
    }
    
    public static void main(String[] args){
        int sets, n;
        long inv;
        
        sets = scn.nextInt();
        while (sets-- > 0){
            n = scn.nextInt();
            a = new long[n];
            tmp = new long[n];
            inv = 0;
            for (int i = 0; i < n; i++){
                a[i] = scn.nextLong();
            }
            inv = numInver(0, n - 1, inv);
            System.out.println(inv);
        }
    }
}

/*
Test:

8 
10 
1 2 3 4 5 6 7 8 9 10 

10 
10 1 2 3 4 5 6 7 8 9  

10 
10 9 8 7 6 5 4 3 2 1 

10 
0 0 0 0 0 0 0 0 0 0

10
5 6 7 8 9 0 1 2 3 4

10
4 3 2 1 0 5 6 7 8 9

10
5 5 5 5 5 4 4 4 4 4

1
100

out:
0
9
45
0
25
10
25
0 
*/