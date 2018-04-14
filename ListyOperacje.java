//Czajka Kamil - grupa nr 7
import java.util.Scanner;

class Wagon{
    String name;
    Wagon prev;
    Wagon next;
    
    public Wagon(String nazwaW){
        name = nazwaW;
        prev = null;
        next = null;
    }
}

class Pociag{
    String name;
    Pociag next;
    Wagon first;
    Wagon last;

    public Pociag(String nazwaP, String nazwaW){
        name = nazwaP;
        next = null;
        Wagon p = new Wagon(nazwaW);
        first = p;
        last = p;
    }
}

class ListaPociagow{
    Pociag pierwszy;
    
    ListaPociagow(){
        pierwszy = null;
    }

    void usunPociag(Pociag p){
        if (p == pierwszy){
            pierwszy = p.next;
        }
        else{
            Pociag walk = pierwszy;
            Pociag walkAfter = null;    // wskazanie na poprzednika usuwanego pociagu
            while (walk != null){
                if (walk == p){
                    break;
                }
                else{
                    walkAfter = walk;
                    walk = walk.next;
                }
            }
            walkAfter.next = walk.next;
        }
    }
    
    Pociag wyszukiwanie(String nazwaP){
        Pociag walk = pierwszy;
        while (walk != null){
            if (walk.name.equals(nazwaP)){
                return walk;
            }
            else{
                walk = walk.next;
            }
        }

       	return null;
    }
    
    void dodajPociag(String nazwaP, String nazwaW){
        Pociag p = new Pociag(nazwaP, nazwaW);
        if (pierwszy != null){
            p.next = pierwszy;
        }
            
        pierwszy = p;
    }

    void wstawWagonNaPoczatek(String nazwaP, String nazwaW){
        Pociag p = wyszukiwanie(nazwaP);     
        Wagon w = new Wagon(nazwaW);
        
        if (p.first == p.last){
            w.next = p.first;
            p.first.prev = w;
            p.first = w;
            return;
        }
        else{
            if (p.first.prev == null){        // normalnie
                w.next = p.first;
                p.first.prev = w;
                p.first = w;
                return;
            }  
            else{                           // odwrocony
                w.prev = p.first;
                p.first.next = w;
                p.first = w;
                return;
            }
        }
    }
    
    void wstawWagonNaKoniec(String nazwaP, String nazwaW){
        Pociag p = wyszukiwanie(nazwaP);        
        Wagon w = new Wagon(nazwaW);

        if (p.first == p.last){
            p.last.next = w;
            w.prev = p.last;
            p.last = w;
            return;
        }
        else{
            if (p.last.next == null){         // normalnie
                p.last.next = w;
                w.prev = p.last;
                p.last = w;
                return;
            }
            else{                           // odwrocony
                p.last.prev = w;
                w.next = p.last;
                p.last = w;
                return;
            }
        }

    }
    
    void wyswietlPociag(String nazwaP){
        Pociag p = wyszukiwanie(nazwaP);                 
        Wagon walk = p.first;
        boolean direction = true;
        System.out.print(p.name + ":");
        
        if (p.first.next == null){
            direction = false;
        }
       
        while(walk != null){
            System.out.print(" " + walk.name);
            
            if (direction == true){
                if (walk.next != null && walk.next.next == walk){
                    direction = false;
                }
                
                walk = walk.next;
            }
            else{
                if (walk.prev != null && walk.prev.prev == walk){
                    direction = true;
                }

                walk = walk.prev;
            }
        }
       
        System.out.println();
    }
    
    void wyswietlWszystkiePociagi(){
        Pociag walk = pierwszy;
        System.out.print("Trains:");
        while (walk != null){
            System.out.print(" " + walk.name);
            walk = walk.next;
        }
        System.out.println();
    }
    
    void odwracaniePociagu(String nazwaP){
        Pociag p = wyszukiwanie(nazwaP);
        Wagon tmp;

        tmp = p.first;
        p.first = p.last;
        p.last = tmp;    
    }
    
    void laczenie(Pociag c, Pociag p){
        if (c.first == c.last && p.first == p.last){        // dwa jednoelementowe
            c.first.next = p.first;
            p.first.prev = c.first;
            c.last = p.first;
            return;
        }
        if (c.first == c.last && p.first != p.last){        // jednoelementowy z wieloelementowy
            if (p.first.prev == null){      // nieodwrocony
                c.first.next = p.first;
                p.first.prev = c.first;
                c.last = p.last;
                return;
            }
            else{                           // odwrocony
                c.first.prev = p.first;
                p.first.next = c.first;
                c.last = p.last;
                return;
            }
        }
        if (c.first != c.last && p.first == p.last){        // wieloelementowy z jednoelementowym
            if (c.last.next == null){       // nieodwrocony
                c.last.next = p.first;
                p.first.prev = c.last;
                c.last = p.last;
                return;
            }
            else{                           // odwrocony
                c.last.prev = p.first;
                p.first.next = c.last;
                c.last = p.last;
                return;
            }
        }
        if (c.last.next == null && p.first.prev == null){       // dwa nieodwrocone
            c.last.next = p.first;
            p.first.prev = c.last;
            c.last = p.last;
            return;
        }

        if (c.last.next != null && p.first.prev == null){       // pierwszy odwocony drugi normalnie
            c.last.prev = p.first;
            p.first.prev = c.last;
            c.last = p.last;
            return;
        }

        if (c.last.next == null && p.first.prev != null){       // drugi odwrocony pierwszy normalnie
            c.last.next = p.first;
            p.first.next = c.last;
            c.last = p.last;
            return;
        }

        if (c.last.next != null && p.first.prev != null){       // dwa odwrocone
            c.last.prev = p.first;
            p.first.next = c.last;
            c.last = p.last;
            return;
        }
    }

    void laczeniePociagow(String nazwaC, String nazwaP){
        Pociag c = wyszukiwanie(nazwaC);
        Pociag p = wyszukiwanie(nazwaP);
        
        laczenie(c, p);
        usunPociag(p);
    }
    
    String usunPierwszy(Pociag p){
        String output;
        
        if (p.first == p.last){     // jeden wagon
            output = p.first.name;
            p.first = null;
            p.last = null;
            return output;
        }
        else{
            if (p.first.prev == null){  // normalnie
                if (p.first.next != null && p.first.next.next == p.first){
                    output = p.first.name;
                    p.first = p.first.next;
                    p.first.next = null;
                    return output;
                }
                else{
                    output = p.first.name;
                    p.first = p.first.next;
                    p.first.prev = null;
                    return output;
                }  
            }
            else{   // odwrocony
                if (p.first.prev != null && p.first.prev.prev == p.first){
                    output = p.first.name;
                    p.first = p.first.prev;
                    p.first.prev = null;
                    return output;
                }
                else{
                    output = p.first.name;
                    p.first = p.first.prev;
                    p.first.next = null;
                    return output;
                }
            }
        }
    }

    void nowyPociagZPierwszegoWagonu(String nazwaP, String nazwaNP){
        String name;
        Pociag p = wyszukiwanie(nazwaP);
        

        if (p.first == p.last){               
            name = usunPierwszy(p);
            dodajPociag(nazwaNP, name);
            usunPociag(p);
        }
        else{
            name = usunPierwszy(p);
            dodajPociag(nazwaNP, name);
        }
    }
    
    String usunOstatni(Pociag p){
        String output;

        if (p.first == p.last){     // jeden wagon
            output = p.first.name;
            p.first = null;
            p.last = null;
            return output;
        }
        else{
            if (p.last.next == null){   // normalnie
                if (p.last.prev != null && p.last.prev.prev == p.last){    // kolejny jest z innego segmentu
                    output = p.last.name;
                    p.last = p.last.prev;
                    p.last.prev = null;
                    return output;
                }
                else{
                    output = p.last.name;
                    p.last = p.last.prev;
                    p.last.next = null;
                    return output;
                }
            }
            else{   // odwrocony
                if (p.last.next != null && p.last.next.next == p.last){   // sprawdzenie czy kolejny wagon nie jest z innego segmentu
                    output = p.last.name;
                    p.last = p.last.next;
                    p.last.next = null;
                    return output;
                }
                else{
                    output = p.last.name;
                    p.last = p.last.next;
                    p.last.prev = null;
                    return output;
                }
            }
        }
    }

    void nowyPociagZOstatniegoWagonu(String nazwaP, String nazwaNP){
        String name;
        Pociag p = wyszukiwanie(nazwaP);
        if (p.first == p.last){
            name = usunOstatni(p);
            dodajPociag(nazwaNP, name);
            usunPociag(p);
        }
        else{
            name = usunOstatni(p);
            dodajPociag(nazwaNP, name);
        }
    }
}

public class Source{
    public static Scanner scn = new Scanner(System.in);
    public static void main(String[] args) {
        String input;
        String in2, in3;     
        int numberOfSets, numberOfOperations;
        ListaPociagow list;

        numberOfSets = scn.nextInt(); 
        while (numberOfSets-- > 0){     // wczytywanie zestawu
            numberOfOperations = scn.nextInt();
            list = new ListaPociagow();
            while (numberOfOperations-- > 0){   // wczytywanie operacji do wykonania
                input = scn.next();
                if (input.equals("New")){
                    in2 = scn.next();
                    in3 = scn.next();
                    list.dodajPociag(in2, in3);
                }
                if (input.equals("InsertFirst")){
                    in2 = scn.next();
                    in3 = scn.next();
                    list.wstawWagonNaPoczatek(in2, in3);
                }
                if (input.equals("InsertLast")){
                    in2 = scn.next();
                    in3 = scn.next();
                    list.wstawWagonNaKoniec(in2, in3);
                }
                if (input.equals("Display")){
                    in2 = scn.next();
                    list.wyswietlPociag(in2);
                }
                if (input.equals("TrainsList")){
                    list.wyswietlWszystkiePociagi();
                }
                if (input.equals("Reverse")){
                    in2 = scn.next();
                    list.odwracaniePociagu(in2);
                }
                if (input.equals("Union")){
                    in2 = scn.next();
                    in3 = scn.next();
                    list.laczeniePociagow(in2, in3);
                }    
                if (input.equals("DelFirst")){
                    in2 = scn.next();
                    in3 = scn.next();
                    list.nowyPociagZPierwszegoWagonu(in2, in3);
                }
                if (input.equals("DelLast")){
                    in2 = scn.next();
                    in3 = scn.next();
                    list.nowyPociagZOstatniegoWagonu(in2, in3);
                }    
            }
     //       System.out.println();
        }
    }
}

/*
Test:
2
110
New T1 W0
New T2 W1
New T3 W2
New T4 W3
New T5 W4
New T6 W5
New T7 W6
New T8 W7
New T9 W8
New T0 W9
TrainsList
Union T0 T1
Reverse T0
Display T0
New T1 W11
TrainsList
Union T1 T9
Display T1
Union T1 T8
Union T1 T7
Union T1 T6
Union T1 T5
Union T1 T4
Union T1 T3
Union T1 T2
Union T1 T0
TrainsList
Display T1
Reverse T1
Display T1
New T2 Z0
InsertLast T2 Z1
InsertFirst T2 Z2 
Reverse T2
Union T1 T2
Display T1
New A1 A0
InsertLast A1 A1
InsertFirst A1 A2
Display A1
New A2 B0
New A3 C0
InsertLast A3 C1
InsertFirst A3 C2
Display A2
Display A3
Reverse A3
Union A1 A2
Display A1
Union A1 A3
Display A1
New B0 A0
InsertLast B0 A1
InsertFirst B0 A2
New B1 B0
New B2 C0
InsertLast B2 C1
InsertFirst B2 C2
Display B0
Reverse B0
Display B0
Display B1
Display B2
Reverse B2
Display B2
Union B0 B1
Display B0
Union B0 B2
Display B0
TrainsList
Union B0 T1
Union B0 A1
TrainsList
Display B0
Reverse B0
Display B0
Reverse B0
Display B0
DelFirst B0 F0
DelLast B0 F1
Reverse B0
DelFirst B0 F2
DelLast B0 F3
Reverse B0
TrainsList
Display F0
Display F1
Display F2
Display F3
InsertLast F0 G1
InsertFirst F1 G2
InsertLast F2 G3
InsertFirst F3 G4
Display F0
Display F1
Display F2
Display F3
New T9 G5
Union T9 F0
Reverse F1
Union T9 F1
Union T9 F2
Reverse T9
Union T9 F3
Display T9
TrainsList
Union T9 B0
Display T9
Reverse T9
Display T9
29
New T1 W0
InsertLast T1 W1
InsertLast T1 W2
New T2 Z0
InsertLast T2 Z1
InsertLast T2 Z2
Reverse T2
New T3 X0
Reverse T3
Display T1
Display T2
Display T3
Union T1 T3
Display T1
Union T1 T2
Display T1
DelLast T1 T2
DelLast T1 T3
DelLast T1 T4
DelLast T1 T5
DelLast T1 T6
DelLast T1 T7
Display T1
Display T2
Display T3
Display T4
Display T5
Display T6
Display T7

out:
Trains: T0 T9 T8 T7 T6 T5 T4 T3 T2 T1
T0: W0 W9
Trains: T1 T0 T9 T8 T7 T6 T5 T4 T3 T2
T1: W11 W8
Trains: T1
T1: W11 W8 W7 W6 W5 W4 W3 W2 W1 W0 W9
T1: W9 W0 W1 W2 W3 W4 W5 W6 W7 W8 W11
T1: W9 W0 W1 W2 W3 W4 W5 W6 W7 W8 W11 Z1 Z0 Z2
A1: A2 A0 A1
A2: B0
A3: C2 C0 C1
A1: A2 A0 A1 B0
A1: A2 A0 A1 B0 C1 C0 C2
B0: A2 A0 A1
B0: A1 A0 A2
B1: B0
B2: C2 C0 C1
B2: C1 C0 C2
B0: A1 A0 A2 B0
B0: A1 A0 A2 B0 C1 C0 C2
Trains: B0 A1 T1
Trains: B0
B0: A1 A0 A2 B0 C1 C0 C2 W9 W0 W1 W2 W3 W4 W5 W6 W7 W8 W11 Z1 Z0 Z2 A2 A0 A1 B0 C1 C0 C2
B0: C2 C0 C1 B0 A1 A0 A2 Z2 Z0 Z1 W11 W8 W7 W6 W5 W4 W3 W2 W1 W0 W9 C2 C0 C1 B0 A2 A0 A1
B0: A1 A0 A2 B0 C1 C0 C2 W9 W0 W1 W2 W3 W4 W5 W6 W7 W8 W11 Z1 Z0 Z2 A2 A0 A1 B0 C1 C0 C2
Trains: F3 F2 F1 F0 B0
F0: A1
F1: C2
F2: C0
F3: A0
F0: A1 G1
F1: G2 C2
F2: C0 G3
F3: G4 A0
T9: G3 C0 G2 C2 G1 A1 G5 G4 A0
Trains: T9 B0
T9: G3 C0 G2 C2 G1 A1 G5 G4 A0 A2 B0 C1 C0 C2 W9 W0 W1 W2 W3 W4 W5 W6 W7 W8 W11 Z1 Z0 Z2 A2 A0 A1 B0 C1
T9: C1 B0 A1 A0 A2 Z2 Z0 Z1 W11 W8 W7 W6 W5 W4 W3 W2 W1 W0 W9 C2 C0 C1 B0 A2 A0 G4 G5 A1 G1 C2 G2 C0 G3

T1: W0 W1 W2
T2: Z2 Z1 Z0
T3: X0
T1: W0 W1 W2 X0
T1: W0 W1 W2 X0 Z2 Z1 Z0
T1: W0
T2: Z0
T3: Z1
T4: Z2
T5: X0
T6: W2
T7: W1

*/