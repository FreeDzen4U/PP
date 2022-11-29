package Lab1;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
public class T1 extends Thread {
    private int a1;
    private int d1;
    private int p1;
    @Override
    public void run() {
        try {
            System.out.println("T1 is started.");
            //Введення MM
            for (int i = 0; i < Data.N; i++) {
                for (int j = 0; j < Data.N; j++) {
                    Data.MM[i][j] = 1;
                }
            }
            //Введення B
            for (int i = 0; i < Data.N; i++) {
                Data.B[i] = 1;
            }
            //Введення MX
            for (int i = 0; i < Data.N; i++) {
                for (int j = 0; j < Data.N; j++) {
                    Data.MX[i][j] = 1;
                }
            }
            // Сигнал задачі Т2, T3, T4 про введення MM, B, MX

            Data.Sem2.release(3);
            //Чекати на введення p в T3
            Data.Sem3.acquire(1);
            //Чекати на введення Z, MT, d в T4
            Data.Sem4.acquire(1);
            //Обчислення1 Xh = sort(d * Bh + Z * MMh)
            Data.calculation1(0, Data.H);
            //Чекати на завершення обчислень Xh в T3
            Data.Sem7.acquire(1);
            //Обчислення2 X2h = sort*(Xh, Xh)
            Data.mergeSort(Data.X, Data.X, Data.X, 0, Data.H*2, Data.H, Data.H*3);
            //Чекати на завершення обчислень X2h в T2
            Data.Sem6.acquire(1);
            //Обчислення3 X = sort*(X2h, X2h)
            Data.mergeSort(Data.X, Data.X, Data.X, 0, Data.H*2, Data.H*2, Data.H*4);
            //Сигнал задачі T2, T3, T4 про завершення обчислень X
            Data.Sem5.release(3);
            //Обчислення4 a1 = (Bh * Zh)
            a1 = Data.scalarProductPart(Data.B, Data.Z, 0, Data.H);
            //Обчислення5 a = a + a1
            Data.a.addAndGet(a1);
            //Сигнал задачі T2, T3, T4 про завершення обчислень a
            Data.Sem9.release(3);
            //Чекати на завершення обчислень a в T2
            Data.Sem10.acquire(1);
            //Чекати на завершення обчислень a в T3
            Data.Sem11.acquire(1);
            //Чекати на завершення обчислень a в T4
            Data.Sem12.acquire(1);
            //Копія a1 = a
            Data.Sem1.acquire();
            a1 = Data.a.get();
            Data.Sem1.release();
            //Копія d1 = d
            synchronized (Data.W) {
                d1 = Data.d;
            }
            //Копія p1 = p
            synchronized (Data.Y) {
                p1 = Data.p;
            }
            //Обчислення6 Ah = p1 * X * (MX * MTh) + a1 * Zh
            Data.calculateResultPart(a1, p1, 0, Data.H);
            //Сигнал задачі T4 про завершення обчислень Ah
            Data.Sem13.release(1);
            System.out.println("T1 is finished.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
