package Lab0;

import java.util.Arrays;

import static Lab0.Lab0.N;
import static java.lang.Thread.sleep;

// F1 (1.3): C = A - B * (MA * MC) * e
public class T1 implements Runnable {
    private int e;
    private int[] A, B, C;
    private int[][] MA, MC;
    public void run() {
        System.out.println("T1 started.");

// Введення даних

        A = Data.initVector("(T1) A");
        B = Data.initVector("(T1) B");
        MA = Data.initMatrix("(T1) MA");
        MC = Data.initMatrix("(T1) MC");
        e = Data.initNumber("(T1) e");

// Обчислення F1

        C = Data.F1(A, B, MA, MC, e);

// Виведення результату

        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

// Вивід вектора при малих значеннях N

        if (N <= 5) {
            System.out.println("(T1) C: ");
            System.out.println(Arrays.toString(C));
        }
        System.out.println("T1 finished.");
    }
}