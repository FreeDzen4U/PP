/**
* Паралельне програмування
* Лабораторна робота No2 C#
* Варіант 15
* Завдання: МА= MD*MC *d + max(Z)*(MX+MM)*p
* ПВВ1 – d, MA, MM;
* ПВВ2 – MX, MC;
* ПВВ4 – Z, MD, p;
* Собченко Ярослав ІО-02

* Дата 01.12.2022
**/

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Lab2
{
    class Lab2
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Lab2 started");
            Console.WriteLine("N = " + Data.N);

            Thread t1 = new Thread(() => T1());
            Thread t2 = new Thread(() => T2());
            Thread t3 = new Thread(() => T3());
            Thread t4 = new Thread(() => T4());

            t1.Start();
            t2.Start();
            t3.Start();
            t4.Start();

            t1.Join();
            t2.Join();
            t3.Join();
            t4.Join();

            Console.WriteLine("Lab2 finished");
        }

        static void T1()
        {
            Console.WriteLine("T1 started");
            int a1;
            int d1;
            int p1;

            // Введення d, MM
            Data.d = 1;
            for (int i = 0; i < Data.N; i++)
            {
                for (int j = 0; j < Data.N; j++)
                {
                    Data.MM[i, j] = 1;
                }
            }

            // Сигнал задачі T2, T3, T4 про введення d, MM
            Data.Sem1.Release(3);
            // Чекати на введення MX, MC в T2
            Data.Sem2.WaitOne();
            // Чекати на введення Z, MD, p в T4
            Data.Sem3.WaitOne();
            // Обчислення1 a1 = max(ZH)
            a1 = Data.GetMaxElementInVector(Data.Z, 0, Data.H);
            // Обчислення2 a = max(a, a1), КД1
            Interlocked.Exchange(ref Data.a, Data.max(Data.a, a1));
            // Сигнал задачі T2, T3, T4 про завершення обчислення a
            Data.Evn1.Set();
            // Чекати на завершення обчислень a в T2
            Data.Evn2.WaitOne();
            // Чекати на завершення обчислень a в T3
            Data.Evn3.WaitOne();
            // Чекати на завершення обчислень a в T4
            Data.Evn4.WaitOne();
            // Копія a1 = a, КД2
            Data.Mtx1.WaitOne();
            a1 = Data.a;
            Data.Mtx1.ReleaseMutex();
            // Копія d1 = d, КД3
            lock (Data.Lock1)
            {
                d1 = Data.d;
            }
            // Копія p2 = p, КД4
            lock (Data.Lock2)
            {
                p1 = Data.p;
            }
            // Обчислення3 МАH= MD*MC*d + a*(MX+MM)*p
            Data.CalculateResultPart(a1, d1, p1, 0, Data.H);

            // Чекати на завершення обчислень MAH в T2, T3, T4
            Data.Bar1.SignalAndWait();
            // Виведення результату MA
            Console.WriteLine("[{0}]", string.Join(", ", Data.MA));

            Console.WriteLine("T1 finished");
        }

        static void T2()
        {
            Console.WriteLine("T2 started");
            int a2;
            int d2;
            int p2;

            // Введення MX, MC

            for (int i = 0; i < Data.N; i++)
            {
                for (int j = 0; j < Data.N; j++)
                {
                    Data.MX[i, j] = 1;
                }
            }

            for (int i = 0; i < Data.N; i++)
            {
                for (int j = 0; j < Data.N; j++)
                {
                    Data.MC[i, j] = 1;
                }
            }

            // Сигнал задачі T1, T3, T4 про введення MX, MC
            Data.Sem2.Release(3);
            // Чекати на введення d, MM в T1
            Data.Sem1.WaitOne();
            // Чекати на введення Z, MD, p в T4
            Data.Sem3.WaitOne();
            // Обчислення1 a2 = max(ZH)
            a2 = Data.GetMaxElementInVector(Data.Z, Data.H , Data.H*2 );
            // Обчислення2 a = max(a, a2), КД1
            Interlocked.Exchange(ref Data.a, Data.max(Data.a, a2));
            // Сигнал задачі T1, T3, T4 про завершення обчислення a
            Data.Evn4.Set();
            // Чекати на завершення обчислень a в T1
            Data.Evn1.WaitOne();
            // Чекати на завершення обчислень a в T3
            Data.Evn2.WaitOne();
            // Чекати на завершення обчислень a в T4
            Data.Evn4.WaitOne();
            // Копія a2 = a, КД2
            Data.Mtx1.WaitOne();
            a2 = Data.a;
            Data.Mtx1.ReleaseMutex();
            // Копія d2 = d, КД3
            lock (Data.Lock1)
            {
                d2 = Data.d;
            }
            // Копія p2 = p, КД4
            lock (Data.Lock2)
            {
                p2 = Data.p;
            }
            // Обчислення3 МАH= MD*MC*d + a*(MX+MM)*p
            Data.CalculateResultPart(a2, d2, p2, Data.H * 3, Data.H * 4);
            // Сигнал задачі T1 про завершення обчислення MAH
            Data.Bar1.SignalAndWait();
            
            Console.WriteLine("T2 finished");
        }

        static void T3()
        {
            Console.WriteLine("T3 started");
            int a3;
            int d3;
            int p3;

            //Чекати на введення d, MM в T1
            Data.Sem1.WaitOne();
            //Чекати на введення MX, MC в T2
            Data.Sem2.WaitOne();
            //Чекати на введення Z, MD, p в T4
            Data.Sem3.WaitOne();
            //Обчислення1 a3 = max(ZH)
            a3 = Data.GetMaxElementInVector(Data.Z, Data.H*2, Data.H * 3);
            //Обчислення2 a = max(a, a3), КД1
            Interlocked.Exchange(ref Data.a, Data.max(Data.a, a3));
            //Сигнал задачі T1, T2, T4 про завершення обчислення a
            Data.Evn2.Set();
            //Чекати на завершення обчислень a в T1
            Data.Evn1.WaitOne();
            //Чекати на завершення обчислень a в T2
            Data.Evn2.WaitOne();
            //Чекати на завершення обчислень a в T4
            Data.Evn4.WaitOne();
            //Копія a3 = d, КД2
            Data.Mtx1.WaitOne();
            a3 = Data.a;
            Data.Mtx1.ReleaseMutex();
            //Копія d3 = d, КД3
            lock (Data.Lock1)
            {
                d3 = Data.d;
            }
            //Копія p3 = p, КД3
            lock (Data.Lock2)
            {
                p3 = Data.p;
            }
            //Обчислення3 МАH= MD*MC*d + a*(MX+MM)*p
            Data.CalculateResultPart(a3, d3, p3, Data.H, Data.H * 2);
            //Сигнал задачі T1 про завершення обчислення MAH

            Data.Bar1.SignalAndWait();

            Console.WriteLine("T3 finished");
        }

    static void T4()
    {
        Console.WriteLine("T4 started");
        int a4;
        int d4;
        int p4;

        // Введення Z, MD, p
        for (int i = 0; i < Data.N; i++)
        {
            Data.Z[i] = 1;
        }
        for (int i = 0; i < Data.N; i++)
            {
                for (int j = 0; j < Data.N; j++)
                {
                    Data.MD[i, j] = 1;
                }
            }
        Data.p = 1;
        // Сигнал задачі T1, T2, T3 про введення Z, MD, p,
        Data.Sem3.Release(3);
        // Чекати на введення d, MM в T1
        Data.Sem1.WaitOne();
        // Чекати на введення MX, MC в T2
        Data.Sem2.WaitOne();
        // Обчислення1 a4 = max(ZH)
        a4 = Data.GetMaxElementInVector(Data.Z, Data.H * 3, Data.H * 4);
        // Обчислення2 a = max(a, a4), КД1
        Interlocked.Exchange(ref Data.a, Data.max(Data.a, a4));
        // Сигнал задачі T1, T2, T3 про завершення обчислення a
        Data.Evn4.Set();
        // Чекати на завершення обчислень a в T1
        Data.Evn1.WaitOne();
        // Чекати на завершення обчислень a в T2
        Data.Evn2.WaitOne();
        // Чекати на завершення обчислень a в T3
        Data.Evn3.WaitOne();
        // Копія a4 = d, КД2
        Data.Mtx1.WaitOne();
        a4 = Data.a;
        Data.Mtx1.ReleaseMutex();
        // Копія d4 = d, КД3
        lock (Data.Lock1)
        {
            d4 = Data.d;
        }
         // Копія p4 = p, КД4
        lock (Data.Lock1)
        {
            p4 = Data.p;
        }
        // Обчислення3 МАH= MD*MC*d + a*(MX+MM)*p
        Data.CalculateResultPart(a4, d4, p4, Data.H * 3, Data.H * 4);
        // Сигнал задачі T1 про завершення обчислення RH
        Data.Bar1.SignalAndWait();

        Console.WriteLine("T4 finished");
    }

    class Data
    {
        public static int N = 4;
        public static int P = 4;
        public static int H = N / P;
        public static int d;
        public static int p;
        public static int[] Z = new int[N];
        public static int[,] MA = new int[N, N];
        public static int[,] MM = new int[N, N];
        public static int[,] MD = new int[N, N];
        public static int[,] MX = new int[N, N];
        public static int[,] MC = new int[N, N];

        // Керування доступом до СР
        public static int a;
        public static Mutex Mtx1 = new Mutex();
        public static object Lock1 = new Object();
        public static object Lock2 = new Object();
        // Засоби організації взаємодії потоків
        public static Semaphore Sem1 = new Semaphore(0, 3);
        public static Semaphore Sem2 = new Semaphore(0, 3);
        public static Semaphore Sem3 = new Semaphore(0, 3);
        public static EventWaitHandle Evn1 = new
EventWaitHandle(false, EventResetMode.ManualReset);
        public static EventWaitHandle Evn2 = new
EventWaitHandle(false, EventResetMode.ManualReset);
        public static EventWaitHandle Evn3 = new
EventWaitHandle(false, EventResetMode.ManualReset);
        public static EventWaitHandle Evn4 = new
EventWaitHandle(false, EventResetMode.ManualReset);
        public static Barrier Bar1 = new Barrier(participantCount: 4);

        public Data() { }

        public static int max(int x, int y)
        {
            if (x > y)
            {
                return x;
            }
            else
            {
                return y;
            }
        }

        // Буфер для зберігання результату MD * MCH
        public static int[,] ML = new int[N, N];
        //MD*MCH

        public static int[,] MultiplyMatrixAndSubMatrix(int[,] MX, int[,] MYH, int start, int end)
        {
            for (int i = start; i < end; i++)
            {
                for (int j = 0; j < N; j++)
                {
                    int s = 0;
                    for (int k = 0; k < N; k++)
                    {
                        s += MYH[i, k] * MX[k, j];
                    }
                    ML[i, j] = s;
                }
            }
            return ML;
        }

        //Множення матриці на скаляр
        private static int[,] MultiplySubMatrixAndScalar(int[,] MX, int y, int start, int end)
        {
            for (int i = start; i < end; i++)
            {
                for (int j = 0; j < N; j++)
                {
                    MX[i, j] = MX[i, j] * y;
                }
            }
            return MX;
        }

        // Додавання матриць
        public static int[,] SumMatrix(int[,] MX, int[,] MY, int start, int end)
        {
            for (int i = start; i < end; i++)
            {
                for (int j = 0; j < N; j++)
                {
                    MX[i, j] += MY[i, j];
                }
            }
            return MX;
        }

        // max(X)
        public static int GetMaxElementInVector(int[] X, int start, int end)
        {
            int maxElement = X[start];
            for (int index = start + 1; index < end; index++)
            {
            if (X[index] > maxElement)
                maxElement = X[index];
            }
            return maxElement;
        }

        // Вивід матриці
        public static void PrintMatrix(int[,] MX)
        {
            for (int i = 0; i < Data.N; i++)
            {
                for (int j = 0; j < Data.N; j++)
                {
                    Console.Write(string.Format("{0} ", MX[i, j]));
                }
                Console.Write(Environment.NewLine + Environment.NewLine);
            }
        }

        // Обчислення3 МАH = MD*MC*d + a*(MX+MM)*p
        public static void CalculateResultPart(int ai, int di, int pi, int start, int end)
        {
        PrintMatrix(
                SumMatrix(
                    MultiplySubMatrixAndScalar(
                        MultiplyMatrixAndSubMatrix(MD, MC, start, end), d, start, end),
                    MultiplySubMatrixAndScalar( 
                        MultiplySubMatrixAndScalar(SumMatrix(MX, MM, start, end), p, start, end), a, start, end), 
                    start, end), 
            start, end);
            }
        }
    }
}