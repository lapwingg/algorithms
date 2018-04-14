//Czajka Kamil - grupa nr 7

import java.util.Scanner;

class StackElem {
    private String value;
    private int priority;
    private boolean rightCommunication;
    public StackElem next;

    public StackElem(String v) {
        value = v;
        next = null;

        char c = value.charAt(0);
        boolean isDone = false;

        if (c == '=') {
            priority = 0;
            rightCommunication = true;
            isDone = true;
        }

        if (c == '<' || c == '>') {
            priority = 1;
            rightCommunication = false;
            isDone = true;
        }

        if (c == '+' || c == '-') {
            priority = 2;
            rightCommunication = false;
            isDone = true;
        }

        if (c == '*' || c == '/' || c == '%') {
            priority = 3;
            rightCommunication = false;
            isDone = true;
        }

        if (c == '^') {
            priority = 4;
            rightCommunication = true;
            isDone = true;
        }

        if (c == '~') {
            priority = 5;
            rightCommunication = true;
            isDone = true;
        }

        if (isDone == false) {
            priority = -1;
            rightCommunication = false;
        }
    }

    public StackElem(String v, int p, boolean b) {
        value = v;
        priority = p;
        rightCommunication = b;
        next = null;
    }

    public char value() {
        return value.charAt(0);
    }

    public String valueS() {
        return value;
    }

    public int priority() {
        return priority;
    }

    public boolean rightCommunication() {
        return rightCommunication;
    }
}

class Stack {
    public StackElem top;

    public Stack() {
        top = null;
    }

    public char top() {
        if (isEmpty() == false) {
            return top.value();
        }

        return ' ';
    }

    public String topS() {
        if (isEmpty() == false) {
            return top.valueS();
        }

        return "";
    }

    public void push(String v) {
        StackElem nowy = new StackElem(v);

        if (isEmpty()) {
            top = nowy;
        } else {
            nowy.next = top;
            top = nowy;
        }
    }

    public void push(String v, int p, boolean b) {
        StackElem nowy = new StackElem(v, p, b);

        if (isEmpty()) {
            top = nowy;
        } else {
            nowy.next = top;
            top = nowy;
        }
    }

    public char pop() {
        StackElem tmp;
        char value = ' ';

        if (isEmpty() == false) {
            tmp = top.next;
            value = top.value();
            top.next = null;
            top = tmp;
        }

        return value;
    }

    public String popS() {
        StackElem tmp;
        String value = "";

        if (isEmpty() == false) {
            tmp = top.next;
            value = top.valueS();
            top.next = null;
            top = tmp;
        }

        return value;
    }

    public boolean isEmpty() {
        return (top == null);
    }
}

public class ONP {
    private static Stack stack;
    private static String input;
    private static String output;
    public static Scanner sc = new Scanner(System.in);

    public static int priority(char c) {
        if (c == '=') {
            return 0;
        }

        if (c == '<' || c == '>') {
            return 1;
        }

        if (c == '+' || c == '-') {
            return 2;
        }

        if (c == '*' || c == '/' || c == '%') {
            return 3;
        }

        if (c == '^') {
            return 4;
        }

        if (c == '~') {
            return 5;
        }

        return -1;
    }

    public static boolean rightCommunication(char c) {
        if (c == '=' || c == '^' || c == '~') {
            return true;
        }

        return false;
    }

    public static boolean isBinaryOperator(char c) {
        if (c == '=' || c == '<' || c == '>' || c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^') {
            return true;
        }

        return false;
    }

    public static int howManyOperandsRequired(char c) {
        if (c == '~') {
            return 1;
        }

        return 2;
    }

    public static void prepareString(boolean inf) {
        String tmp = "";
        char c;

        input = input.substring(5);

        for (int i = 0; i < input.length(); i++) {
            c = input.charAt(i);
            if (c >= 'a' && c <= 'z') {
                tmp = tmp + c;
            }
            if (c == '=' || c == '<' || c == '>' || c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^' || c == '~') {
                tmp = tmp + c;
            }


            if (inf == true) {
                if (c == '(' || c == ')') {
                    tmp = tmp + c;
                }
            }
        }

        input = tmp;
    }

    public static void goesToONP() {
        char c, c2, c3;
        String arg;
        boolean leftBracket = false;
        int operandToOperators = 0;
        stack = new Stack();
        prepareString(true);
        output = "";
        for (int i = 0; i < input.length(); i++) {
            c = input.charAt(i);
            arg = input.substring(i, i + 1);
            if (c >= 'a' && c <= 'z') {
                output = output + c;
                operandToOperators++;
                if (i < input.length() - 1) {
                    c2 = input.charAt(i + 1);
                    if (c2 >= 'a' && c2 <= 'z') {           // dwa operandy obok siebie
                        output = "ONP: error";
                        return;
                    }
                }
            } else {
                if (c == '(') {
                    stack.push(arg);
                } else {
                    if (c == ')') {
                        leftBracket = false;
                        if (stack.isEmpty() == false) {
                            while (stack.isEmpty() != true) {
                                if (stack.top() == '(') {
                                    stack.pop();
                                    leftBracket = true;
                                    break;
                                }

                                output = output + stack.pop();
                            }

                            if (leftBracket == false) {           // brak lewego nawiasu gdy pojawil sie prawy nawias na wejsciu
                                output = "ONP: error";
                                return;
                            }
                        } else {
                            output = "ONP: error";   // pojawienie sie prawego nawiasu gdy nie ma niczego na stosie
                            return;
                        }
                    } else {
                        if (isBinaryOperator(c)) {
                            operandToOperators--;
                        }
                        if (rightCommunication(c) == true) {
                            if (stack.isEmpty() == false) {
                                while (stack.isEmpty() != true) {
                                    if (stack.top.priority() > priority(c)) {
                                        output = output + stack.pop();
                                    } else {
                                        break;
                                    }
                                }
                            }

                            stack.push(arg);
                        } else {
                            if (stack.isEmpty() == false) {
                                while (stack.isEmpty() != true) {
                                    if (stack.top.priority() >= priority(c)) {
                                        output = output + stack.pop();
                                    } else {
                                        break;
                                    }
                                }
                            }

                            stack.push(arg);
                        }
                    }
                }
            }
        }

        while (stack.isEmpty() != true) {
            if (stack.top() == '(') {               // nieprawidlowa ilosc nawiasow
                output = "ONP: error";
                return;
            }

            output = output + stack.pop();
        }

        if (operandToOperators != 1) {              // zbyt duzo operatorow binarnych albo operandow
            output = "ONP: error";
            return;
        }

        output = "ONP: " + output;
    }

    public static void goesToINF() {
        char c;
        String arg;
        stack = new Stack();
        prepareString(false);
        for (int i = 0; i < input.length(); i++) {
            c = input.charAt(i);
            arg = input.substring(i, i + 1);
            if (c >= 'a' && c <= 'z') {
                stack.push(arg);
            } else {
                String tmp = "" + c;
                for (int j = 0; j < howManyOperandsRequired(c); j++) {
                    if (j == 0) {
                        if (stack.isEmpty() == true) {             // zbyt malo wyrazen na stosie dla operatora podanego na wejsciu
                            output = "INF: error";
                            return;
                        }

                        if (stack.topS().length() > 1) {
                            if (priority(c) >= stack.top.priority()) {
                                tmp = tmp + "(" + stack.popS() + ")";
                            } else {
                                tmp = tmp + stack.popS();
                            }
                        } else {
                            tmp = tmp + stack.popS();
                        }
                    } else {
                        if (stack.isEmpty() == true) {         // zbyt malo wyrazen na stosie dla operatora podanego na wejsciu
                            output = "INF: error";
                            return;
                        }

                        if (stack.topS().length() > 1) {
                            if (priority(c) > stack.top.priority()) {
                                tmp = "(" + stack.popS() + ")" + tmp;
                            } else {
                                tmp = stack.popS() + tmp;
                            }
                        } else {
                            tmp = stack.popS() + tmp;
                        }
                    }
                }

                stack.push(tmp, priority(c), false);
            }
        }

        output = "INF: " + stack.popS();

        if (stack.isEmpty() == false) {        // cale wejscie powinno skleic sie w jedno wyrazenie - w przeciwnym przypadku blad
            output = "INF: error";
        }
    }

    public static void main(String[] args) {
        int sets;

        sets = sc.nextInt();
        input = sc.nextLine();
        while (sets-- > 0) {
            input = sc.nextLine();
            if (input.charAt(0) == 'I') {
                goesToONP();
                System.out.println(output);
            } else {
                goesToINF();
                System.out.println(output);
            }
        }
    }
}

/*
/*
Testy:
- w poczatkowej fazie pisanie testowalem zachowanie samego stosu 
- testy INF -> ONP
in:
50
INF: a)+(b
INF: a+b+(~a-a)
INF: x=~~a+b*c
INF: t=~a<x<~b
INF: ~a-~~b<c+dpq
INF: a^b*c-d<xpq+x
INF: x=~a*b/c-d+e%~f
INF: x=a=b=c
INF: x=a^b^c
INF: x=a=b=c^d^e
INF: x=(a=(b=c^(d^e)))
INF: ((x=a^b^c)))
INF: x+=a
INF: (x-=a)*(x+=b)
INF: (x==c)*(x==c)
INF: x+a+b+c+d+e+f))))
INF: a===b
INF: a++
INF: ++a
INF: a--
INF: --a
INF: a(b+c)
INF: a+(+c)
INF: a**b
INF: a+b(~)b-c
INF: a~+b
INF: a+-b
INF: a+(b-)
INF: a-+b
INF: a-(b+)
INF: a<>b
INF: a*+b
INF: a^~b
INF: (a=~~b)*(b<~~c>~~d)
INF: a*=*b
INF: a++-b
INF: (a*~~b)^(~~~b)
INF: +(a+b)+c
INF: -(b+c)+d
INF: ((a+b))
INF: ~~a*(~~b+c+)+d
INF: a==*b
INF: a*==b
INF: ~~~-+-+-++a*b++
INF: +-+-~~~a+~~~~b
INF: ~+~+~+a
INF: ~+~+~++++a
INF: ++++~~a
INF: +++++++a+-b+++(~~~d*~~~e)
INF: ++(a+b)

out:
ONP: error
ONP: ab+a~a-+
ONP: xa~~bc*+=
ONP: ta~x<b~<=
ONP: error
ONP: error
ONP: xa~b*c/d-ef~%+=
ONP: xabc===
ONP: xabc^^=
ONP: xabcde^^===
ONP: xabcde^^===
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: a~b+
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: ab~^
ONP: ab~~=bc~~<d~~>*
ONP: error
ONP: error
ONP: ab~~*b~~~^
ONP: error
ONP: error
ONP: ab+
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error

- testy ONP -> INF
in:
8
ONP: ab+a~a-+
ONP: xabcdefg++++++=
ONP: ab+c+d+e+f+g+
ONP: abc++def++g++
ONP: xabc===
ONP: xabcde^^===
ONP: abc++def++g+++
ONP: vxabcdefg++++++=

out:
INF: a+b+(~a-a)
INF: x=a+(b+(c+(d+(e+(f+g)))))
INF: a+b+c+d+e+f+g
INF: a+(b+c)+(d+(e+f)+g)
INF: x=(a=(b=c))
INF: x=(a=(b=c^(d^e)))
INF: error
INF: error

- testy usuwania niepotrzebnych znakĂłw i konwersji
in:
7 
INF: t = ~ a < x < ~b
INF: ~a-~~b<c+d&!p|!!q
INF: a^b*c-d<xp|q+x
ONP: (((xabc+++)))
INF: !!x!!++!!a!!=!!b!!
ONP: a %% b ## c ++
INF: x!=!~!~!~b

out:
ONP: ta~x<b~<=
ONP: error
ONP: error
INF: x+(a+(b+c))
ONP: error
INF: error
ONP: xb~~~=
*/
