package Lab3;
public class T3 extends Thread {
    private int a3;
    private int d3;
    @Override
    public void run() {
        System.out.println("T3 is started");

        System.out.println("T3 is started");
        // Введення MA
        int[][] MA = new int[Data.N][Data.N];
        for (int i = 0; i < Data.N; i++) {
            for (int j = 0; j < Data.N; j++) {
                MA[i][j] = 1;
            }
        }
        // Введення C
        int[] C = new int[Data.N];
        for (int i = 0; i < Data.N; i++) {
            C[i] = 1;
        }
        Data.resourcesMonitor.setMA(MA);
        Data.resourcesMonitor.setC(C);
        // Сигнал задачі T1, T2, T4 про введення MA, C  Data.inputOutputMonitor.inputSignal();
        // Чекати на введення MB, E в T2 та MD, d в T4  Data.inputOutputMonitor.waitForInputSignal();
        // Обчислення1 a3 = max(C*MDh)
        a3 = Data.resourcesMonitor.maxValue(Data.H*2, Data.H)*3;
        // Обчислення2 a = max(a, a3)
        Data.resourcesMonitor.compare_a(a3);
        // Сигнал задачі T2, T3, T4 про завершення обчислення a  Data.synchronizationMonitor1.a_CalculatedSignal();
        // Чекати на завершення обчислень a
        Data.synchronizationMonitor1.waitForCalculated_a();
        // Копія a3 = a
        a3 = Data.resourcesMonitor.copy_a();
        // Копія d1 = d
        d3 = Data.resourcesMonitor.copy_d();
        // Обчислення4 Обчислення3 Wh = a3 + E * (MA * MBh) * d3  Data.resourcesMonitor.calculateResultPart(a3, d3, Data.H*2,  Data.H*3);
        // Чекати на завершення обчислень Wh
        Data.inputOutputMonitor.waitForOutputSignal();  // Вивід результату W
        Data.resourcesMonitor.printVector(Data.resourcesMonitor.getW());
        System.out.println("T3 is finished");
    }
}
