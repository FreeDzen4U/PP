package Lab3;

/*
 * Parallel programming
 * Lab_work_3. Java. Monitors
 * Variant 22
 * W=max(C*MD)+E*(MA*MB)*d
 * PVV2 - E, MB; PVV3 - C, MA, W; PVV4 - MD, d
 *
 * Sobchenko Yaroslav IO-02
 * 22.12.2022
 */

public class Lab3 {
    public static void main(String[] args) {
        System.out.println("Lab_work_3 started");
        System.out.println("N = " + Data.N);

        // Creating threads
        T1 T1 = new T1();
        T2 T2 = new T2();
        T3 T3 = new T3();
        T4 T4 = new T4();
        // Running threads
        T1.start();
        T2.start();
        T3.start();
        T4.start();
        // Finish the threads and then the main thread
    try {
        T1.join();
        T2.join();
        T3.join();
        T4.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.println("Lab_work_3 finished");
    }
}
