package test;
/**
 * Parallel computing.
 * Labwork 02. Threads in Java
 * Volovyk Oleksandr
 * IO-02
 * 13.09.2022
 *
 * F1 (1.15): d = MAX((A + B + C) * (MA * ME))
 * F2 (2.8): MF = g * TRANS(MG) + f * (MK * ML)
 * F3 (3.9): O = SORT(P) * (MR * MS)
 */
public class Lab0 {
    public static void main(String[] args) {
        int N = 5;
        // random = false, тобто значення всіх елементів даних будуть 1
        Data data = new Data(N, false);
        // Для T1 використано інтерфейс Runnable
        Runnable t1Runnable = new T1(data);
        Thread t1 = new Thread(t1Runnable);
        // Для T2, T3 використано клас Thread
        T2 t2 = new T2(data);
        T3 t3 = new T3(data);
        // Запуск потоків
        t1.start();
        t2.start();
        t3.start();
        // Синхронізація з потоками
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Lab02 finished!");
    }
}
