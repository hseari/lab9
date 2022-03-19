package com.company;

import java.util.*;
import java.util.regex.*;

class Fraction {
    private int num;
    private int den;

    //первая дробь
    public Fraction(int num, int den) throws Exception {
        if (num < 0 || den <= 0) {
            throw new Exception("Некорректный параметр");
        } else {
            this.num = num;
            this.den = den;
        }
    }

    //вторая дробь
    public Fraction() {
        this.num = 1;
        this.den = 1;
    }

    public int getNum() {
        return num;
    }

    public int getDen() {
        return den;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setDen(int den) {
        this.den = den;
    }

    public static int NOD(int a, int b) {
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }

    public static Fraction Just(Fraction a) {
        int n = a.getNum();
        int d = a.getDen();
        int div = NOD(n, d);
        n /= div;
        d /= div;
        a.setNum(n);
        a.setDen(d);
        return a;
    }

    //сумма
    public static Fraction sum(Fraction a, Fraction b) {
        int num1 = a.getNum();
        int den1 = a.getDen();
        int num2 = b.getNum();
        int den2 = b.getDen();
        Fraction c = new Fraction();

        c.setNum(num1 * den2 + num2 * den1);
        c.setDen(den1 * den2);

        return Just(c);
    }

    //вычитание
    public static Fraction diff(Fraction a, Fraction b) {
        int num1 = a.getNum();
        int den1 = a.getDen();
        int num2 = b.getNum();
        int den2 = b.getDen();
        Fraction c = new Fraction();

        c.setNum(num1 * den2 - num2 * den1);
        c.setDen(den1 * den2);

        return Just(c);
    }

    //умножение
    public static Fraction imp(Fraction a, Fraction b) {
        int num1 = a.getNum();
        int den1 = a.getDen();
        int num2 = b.getNum();
        int den2 = b.getDen();
        Fraction c = new Fraction();

        c.setNum(num1 * num2);
        c.setDen(den1 * den2);

        return Just(c);
    }

    //деление
    public static Fraction del(Fraction a, Fraction b) {
        int num1 = a.getNum();
        int den1 = a.getDen();
        int num2 = b.getNum();
        int den2 = b.getDen();
        Fraction c = new Fraction();

        c.setNum(num1 * den2);
        c.setDen(num2 * den1);

        return Just(c);
    }

    public String toString() {
        return num + "/" + den;
    }

    public static Fraction StringtoFraction(String h) {
        String[] H = h.split("/");
        Fraction f = new Fraction();
        f.setNum(Integer.parseInt(H[0]));
        f.setDen(Integer.parseInt(H[1]));
        return f;
    }
}

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner in = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        while (!in.hasNext("quit")) {
            String exp = in.nextLine();
            //String exp =  exp1.replace("-", "+0/1-");
            Pattern p = Pattern.compile("-?[0-9]+/-?[1-9]+(([+]|-|[*]|:)-?[0-9]+/-?[1-9]+)+");
            Matcher m = p.matcher(exp);

            while (!exp.matches(p.pattern())) {
                System.out.println("Выражение может содержать только цифры, знаки операций и /, без пробелов.");
                System.out.print("Введите выражение еще раз: ");
                exp = in.next();
            }

            String[] sign1 = exp.split("-?[0-9]+/-?[1-9]+");

            ArrayList<String> sign = new ArrayList<String>();

            for (int i = 0; i < sign1.length; i++) {
                sign.add(sign1[i]);
            }

            sign.remove(0);

            String[] frac1 = exp.split("[+*:-]");

            ArrayList<Fraction> frac = new ArrayList<Fraction>();

            for (int i = 0; i < frac1.length; i++) {
                frac.add(Fraction.StringtoFraction(frac1[i]));
            }

            while (!sign.isEmpty()) {
                for (int i = 0; i < sign.size(); i++) {
                    if (sign.get(i).equals(":")) {
                        sign.remove(i);
                        frac.set(i, Fraction.del(frac.get(i), frac.get(i + 1)));
                        frac.remove(i + 1);
                    } else if (sign.get(i).equals("*")) {
                        sign.remove(i);
                        frac.set(i, Fraction.imp(frac.get(i), frac.get(i + 1)));
                        frac.remove(i + 1);
                    } else if (sign.get(i).equals("+")) {
                        sign.remove(i);
                        frac.set(i, Fraction.sum(frac.get(i), frac.get(i + 1)));
                        frac.remove(i + 1);
                    } else if (sign.get(i).equals("-")) {
                        sign.remove(i);
                        frac.set(i, Fraction.diff(frac.get(i), frac.get(i + 1)));
                        frac.remove(i + 1);
                    }
                }
            }

            Pattern t = Pattern.compile("-?[0-9]+/0");
            Matcher k = t.matcher(exp);
            while (frac.get(0).toString().matches(t.pattern())) {
                System.out.print("Деление на ноль! ");
                break;
            }
            System.out.println(frac.get(0).toString());
            System.out.print("Введите выражение: ");
        }

        in.close();
    }
}