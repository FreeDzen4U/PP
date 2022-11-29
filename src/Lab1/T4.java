package Lab1;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
public class T4 extends Thread {
    private int a4;
    private int d4;
    private int p4;
    @Override
    public void run() {
        try {
            System.out.println("T4 is started.");
            //Введення Z
            for (int i = 0; i < Data.N; i++) {
                Data.Z[i] = 1;
            }
            //Введення MT
            for (int i = 0; i < Data.N; i++) {
                for (int j = 0; j < Data.N; j++) {
                    Data.MT[i][j] = 1;
                }
            }
            //Введення d
            Data.p = 1;
            //Сигнал задачі Т1, T2, T4 про введення Z, MT, d
            Data.Sem4.release(3);
            //Чекати на введення MM, B, MX в T1
            Data.Sem2.acquire(1);
            //Чекати на введення p в T3
            Data.Sem3.acquire(1);
            //Обчислення1 Xh = sort(d * Bh + Z * MMh)
            Data.calculation1(Data.H*2, Data.H*3);
            //Сигнал задачі T2 про завершення обчислень Xh
            Data.Sem8.release(1);
            //Обчислення4 a4 = (Bh * Zh)
            a4 = Data.scalarProductPart(Data.B, Data.Z, Data.H*3, Data.H*4);
            //Обчислення5 a = a + a4
            Data.a.addAndGet(a4);
            //Сигнал задачі T1, T2, T3 про завершення обчислень a
            Data.Sem12.release(3);
            //Чекати на завершення обчислень X в T1
            Data.Sem5.acquire(1);
            //Чекати на завершення обчислень a в T1
            Data.Sem9.acquire(1);
            //Чекати на завершення обчислень a в T2
            Data.Sem10.acquire(1);
            //Чекати на завершення обчислень a в T3
            Data.Sem11.acquire(1);
            //Копія a4 = a
            Data.Sem1.acquire();
            a4 = Data.a.get();
            Data.Sem1.release();
            //Копія d4 = d
            synchronized (Data.W) {
                d4 = Data.d;
            }
            //Копія p4 = p
            synchronized (Data.Y) {
                p4 = Data.p;
            }
            //Обчислення6 Ah = p4 * X * (MX * MTh) + a4 * Zh
            Data.calculateResultPart(a4, p4, Data.H*3, Data.H*4);
            // Чекати на закінчення обчислень Ah в T2
            Data.Sem13.acquire(1);
            // Чекати на закінчення обчислень Ah в T3
            Data.Sem14.acquire(1);
            // Чекати на закінчення обчислень Ah в T4
            Data.Sem15.acquire(1);
            // Виведення A
            Data.printVector(Data.A);
            System.out.println("T4 is finished.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
