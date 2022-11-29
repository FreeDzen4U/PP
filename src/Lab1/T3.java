package Lab1;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
public class T3 extends Thread {
    private int a3;
    private int d3;
    private int p3;
    @Override
    public void run() {
        try {
            System.out.println("T3 is started.");
            //Введення p
            Data.p = 1;
            //Сигнал задачі Т1, T2, T4 про введення p
            Data.Sem3.release(3);
            //Чекати на введення MM, B, MX в T1
            Data.Sem2.acquire(1);
            //Чекати на введення Z, MT, d в T4
            Data.Sem4.acquire(1);
            //Обчислення1 Xh = sort(d * Bh + Z * MMh)
            Data.calculation1(Data.H*2, Data.H*3);
            //Сигнал задачі T1 про завершення обчислень Kh
            Data.Sem7.release(1);
            //Обчислення4 a3 = (Bh * Zh)
            a3 = Data.scalarProductPart(Data.B, Data.Z, Data.H*2, Data.H*3);
            //Обчислення5 a = a + a3
            Data.a.addAndGet(a3);
            //Сигнал задачі T1, T2, T4 про завершення обчислень a
            Data.Sem11.release(3);
            //Чекати на завершення обчислень X в T1
            Data.Sem5.acquire(1);
            //Чекати на завершення обчислень a в T1
            Data.Sem9.acquire(1);
            //Чекати на завершення обчислень a в T2
            Data.Sem10.acquire(1);
            //Чекати на завершення обчислень a в T4
            Data.Sem12.acquire(1);
            //Копія a3 = a
            Data.Sem1.acquire();
            a3 = Data.a.get();
            Data.Sem1.release();
            //Копія d3 = d
            synchronized (Data.W) {
                d3 = Data.d;
            }
            //Копія p3 = p
            synchronized (Data.Y) {
                p3 = Data.p;
            }
            //Обчислення6 Ah = p3 * X * (MX * MTh) + a4 * Zh
            Data.calculateResultPart(a3, p3, Data.H*2, Data.H*3);
            //Сигнал задачі T4 про завершення обчислень Ah
            Data.Sem15.release(1);
            System.out.println("T3 is finished.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
