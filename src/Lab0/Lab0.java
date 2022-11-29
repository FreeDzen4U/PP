package Lab0;
/**
 * Parallel computing.
 * Labwork 02. Threads in Java
 * Sobchenko Yaroslav
 * IO-02
 * 24.09.2022
 * F1 (1.3): C = A - B * (MA * MC) * e
 * F2 (2.22): MF = (MG * MH) * (MK + ML)
 * F3 (3.23): s = MAX((MO * MP)(R + V))
 */

public class Lab0 {
    public static final int N = 5;
    public static void main(String[] args) {
// Для T1 використано інтерфейс Runnable
        Runnable t1Runnable = new T1();
        Thread T1 = new Thread(t1Runnable);
// Для T2, T3 використано клас Thread
        T2 T2 = new T2();
        T3 T3 = new T3();
// Запуск потоків
        T1.start();
        T2.start();
        T3.start();
// Синхронізація з потоками
        try {
            T1.join();
            T2.join();
            T3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Lab0 finished!");
    }
}
