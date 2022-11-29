package Lab0;

// F2 (2.22): MF = (MG * MH) * (MK + ML)

import static Lab0.Lab0.N;

public class T2 extends Thread {
    private int[][] MG, MK, MH, MF, ML;
    public void run() {
        System.out.println("T2 started.");

// Введення даних

        MG = Data.initMatrix("(T2) MG");
        MH = Data.initMatrix("(T2) MH");
        MK = Data.initMatrix("(T2) MK");
        ML = Data.initMatrix("(T2) ML");

// Обчислення F2

        MF = Data.F2(MG, MH, MK, ML);

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

// Вивід матриці при малих значеннях N

        if (N <= 5) {
            System.out.println("(T2) MF: ");
            Data.printMatrix(MF);
        }
        System.out.println("T2 finished.");
    }
}