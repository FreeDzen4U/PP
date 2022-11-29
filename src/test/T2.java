package test;

// F2 (2.8): MF = g * TRANS(MG)+ f * (MK * ML)
public class T2 extends Thread {
    private Data data;
    public T2(Data data) {
        this.data = data;
    }
    public void run() {
        System.out.println("T2 started.");
        int N = data.getN();
        // Обчислення F2
        int[][] MF = data.f2(data.g, data.MG, data.f, data.MK, data.ML);
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Вивід матриці в термінал при малих значеннях N
        if (N <= 5) {
            System.out.println("MF: ");
            data.printMatrix(MF);
        }
        System.out.println("T2 finished.");
    }
}