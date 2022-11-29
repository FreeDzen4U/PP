package test;

// F3 (3.9): O = SORT(P) * (MR * MS)

import java.util.Arrays;
public class T3 extends Thread {
    private Data data;
    public T3(Data data) {
        this.data = data;
    }
    public void run() {
        System.out.println("T3 started.");
        int N = data.getN();
        // Обчислення F3
        int[] O = data.f3(data.P, data.MR, data.MS);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Вивід вектора в термінал при малих значеннях N
        if (N <= 5) {
            System.out.println("O: ");
            System.out.println(Arrays.toString(O));
        }
        System.out.println("T3 finished.");
    }
}
