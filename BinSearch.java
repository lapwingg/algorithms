//Czajka Kamil - grupa nr 7
import java.util.Scanner;

public class BinSearch{
    public static int tab[];
    public static int tabSize;
    public static int key[];
    public static int tabKeySize;
    public static Scanner sc = new Scanner(System.in);

    public static int interpolSearch(int key){
        int cur = 0, low = 0, upp = tabSize - 1;
        
        while ((tab[low] <= key) && (key <= tab[upp])){
            if (tab[upp] - tab[low] == 0){    
                return cur;
            }

            cur = low + ((key - tab[low]) * (upp - low) / (tab[upp] - tab[low]));
                
            if (tab[cur] == key){
                return cur;
            }
            else{
                if (key < tab[cur]){
                    upp = cur - 1;
                }
                else{
                    low = cur + 1;
                }
            }
        }

        return -1;
    }

    public static int counting(int key){
        int left = 0, right = tabSize - 1;
        boolean keyWasFound = false;
        int low = 0, cur, upp = tabSize - 1;
        while (low <= upp){         // granica z lewej strony
            cur = (low + upp) / 2;
            if (tab[cur] == key){
                keyWasFound = true;
                if (cur == 0){
                    left = 0;
                    break;
                }

                if (tab[cur - 1] == key){
                    upp = cur - 1;
                }
                else{
                    left = cur;
                    break;
                }
            }
            else{
                if (tab[cur] < key){
                    low = cur + 1;
                }
                else{
                    upp = cur - 1;
                }
            }
        }

        if (keyWasFound == false){
            return 0;
        }

        low = 0; 
        upp = tabSize - 1;
        while (low <= upp){        // granica z prawej strony
            cur = (low + upp) / 2;
            if (tab[cur] == key){
                if (cur == tabSize - 1){
                    right = tabSize - 1;
                    break;
                }

                if (tab[cur + 1] == key){
                    low = cur + 1;
                }
                else{
                    right = cur;
                    break;
                }
            }
            else{
                if (tab[cur] < key){
                    low = cur + 1;
                }
                else{
                    upp = cur - 1;
                }
            }
        }

        return right - left + 1;
    }

    public static void delDuplicate(){
        int newTabSize = 0;
            
        for (int i = 0; i < tabSize; i++){
            if (i == 0){
                newTabSize++;
            }
            else{
                if (tab[i] != tab[newTabSize - 1]){
                    tab[newTabSize] = tab[i];
                    newTabSize++;
                }
            }
        }

        tabSize = newTabSize;        
    }

    public static void main(String[] args){
        int numberOfSets;

        numberOfSets = sc.nextInt();

        while (numberOfSets-- > 0){
            tabSize = sc.nextInt();
            tab = new int[tabSize];

            for (int i = 0; i < tabSize; i++){
                tab[i] = sc.nextInt();
            }

            tabKeySize = sc.nextInt();
            key = new int[tabKeySize];

            for (int i = 0; i < tabKeySize; i++){
                key[i] = sc.nextInt();  
            }

            for (int i = 0; i < tabKeySize; i++){
                System.out.print("(" + counting(key[i]) + " " + interpolSearch(key[i]) + ")");
            }
         
            delDuplicate();
            for (int i = 0; i < tabSize; i++){
                if (i == 200){
                    break;
                }

                if (i % 50 == 0){
                    System.out.println();
                    System.out.print(tab[i]);
                } 
                else{
                    System.out.print(" " + tab[i]);
                }
            }
            System.out.println();
        }
    }
} 

/*
Test:
8
10
0 1 2 3 4 5 6 7 8 9
10
9 8 7 6 5 4 3 2 1 0

10
0 1 2 3 4 5 6 7 8 9
9
-1 -2 -3 -4 -5 -6 -7 -8 -9

10
2 2 2 2 2 2 2 2 2 2
1
2

10
1 1 1 1 1 1 1 1 1 1
0

12 
-1 1 2 2 2 3 5 5 7 7 9 9 
10 
1 2 3 -1 4 9 5 6 7 8 

10 
1 2 2 2 2 2 3 3 3 3  
5 
1 2 3 4 0

10 
0 0 0 0 0 0 0 0 0 1 
4 
0 -1 1 2

9
-100 1 1 1 100 111 111 111 1000
3
-100 100 1000

(1 9)(1 8)(1 7)(1 6)(1 5)(1 4)(1 3)(1 2)(1 1)(1 0)
0 1 2 3 4 5 6 7 8 9
(0 -1)(0 -1)(0 -1)(0 -1)(0 -1)(0 -1)(0 -1)(0 -1)(0 -1)
0 1 2 3 4 5 6 7 8 9
(10 0)
2

1
(1 1)(3 3)(1 5)(1 0)(0 -1)(2 11)(2 6)(0 -1)(2 8)(0 -1)
-1 1 2 3 5 7 9
(1 0)(5 4)(4 9)(0 -1)(0 -1)
1 2 3
(9 0)(0 -1)(1 9)(0 -1)
0 1
(1 0)(1 4)(1 8)
-100 1 100 111 1000
*/