package Lab1;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
public class T2 extends Thread {
    private int a2;
    private int d2;
    private int p2;
    @Override
    public void run() {
        try {
            System.out.println("T2 is started.");
            //Чекати на введення MM, B, MX в T1
            Data.Sem2.acquire(1);
            //Чекати на введення p в T3
            Data.Sem3.acquire(1);
            //Чекати на введення Z, MT, d в T4
            Data.Sem4.acquire(1);
            //Обчислення1 Xh = sort(d * Bh + Z * MMh)
            Data.calculation1(Data.H, Data.H*2);
            //Чекати на завершення обчислень Xh в T4
            Data.Sem8.acquire(1);
            //Обчислення2 X2h = sort*(Xh, Xh)
            Data.mergeSort(Data.X, Data.X, Data.X, Data.H, Data.H*3, Data.H*2, Data.H*4);
            //Сигнал задачі T1 про завершення обчислень X2h
            Data.Sem6.release(1);
            //Обчислення4 a2 = (Bh * Zh)
            a2 = Data.scalarProductPart(Data.B, Data.Z, Data.H, Data.H*2);
            //Обчислення5 a = a + a2
            Data.a.addAndGet(a2);
            //Сигнал задачі T1, T3, T4 про завершення обчислень a
            Data.Sem10.release(3);
            //Чекати на завершення обчислень X в T1
            Data.Sem5.acquire(1);
            //Чекати на завершення обчислень a в T1
            Data.Sem9.acquire(1);
            //Чекати на завершення обчислень a в T3
            Data.Sem11.acquire(1);
            //Чекати на завершення обчислень a в T4
            Data.Sem12.acquire(1);
            //Копія a2 = a
            Data.Sem1.acquire();
            a2 = Data.a.get();
            Data.Sem1.release();
            //Копія d2 = d
            synchronized (Data.W) {
                d2 = Data.d;
            }
            //Копія p2 = p
            synchronized (Data.Y) {
                p2 = Data.p;
            }
            //Обчислення6 Ah = p2 * X * (MX * MTh) + a2 * Zh
            Data.calculateResultPart(a2, p2, Data.H, Data.H*2);
            //Сигнал задачі T4 про завершення обчислень Ah
            Data.Sem14.release(1);
            System.out.println("T2 is finished.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
