package test;

// F1 (1.15): d = MAX((A + B + C) * (MA * ME))
public class T1 implements Runnable {
    private Data data;
    public T1(Data data) {
        this.data = data;
    }
    public void run() {
        System.out.println("T1 started.");
        // Обчислення F1
        int d = data.f1(data.A, data.B, data.C, data.MA, data.ME);
        System.out.println("d = " + d);
        System.out.println("T1 finished.");
    }
}