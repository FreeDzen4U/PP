package Lab1;

/**
 * Паралельне програмування
 * Лабораторна робота No1
 * Варіант 16
 * Завдання: A = p * sort(d * B + Z * MM) * (MX * MT) + (B * Z) * Z
 * • ПВВ1 – MM, B, MX
 * • ПВВ3 – p
 * • ПВВ4 – Z, MT, d
 * Cобченко Ярослав ІО-02
 * Дата 09.11.2022
 **/
public class Lab1 {

    public static void main(String[] args) {
        System.out.println("Lab1 is started.");
        System.out.print("N = " + Data.N + "\n");
        T1 T1 = new T1();
        T2 T2 = new T2();
        T3 T3 = new T3();
        T4 T4 = new T4();
        T1.start();
        T2.start();
        T3.start();
        T4.start();
        try {
            T1.join();
            T2.join();
            T3.join();
            T4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Lab1 is finished.");
    }
}
