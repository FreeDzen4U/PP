package Lab0;

// F3 (3.23): s = MAX((MO * MP)(R + V))

public class T3 extends Thread {
    private int s;
    private int[] R, P;
    private int[][] MR, MS;
    public void run() {
        System.out.println("T3 started.");

// Введення даних

        R = Data.initVector("(T3) R");
        P = Data.initVector("(T3) P");
        MR = Data.initMatrix("(T3) MR");
        MS = Data.initMatrix("(T3) MS");

// Обчислення F3

        s = Data.F3(R, P, MR, MS);

        System.out.println("(T3) s = " + s);
        System.out.println("T3 finished.");
    }
}
