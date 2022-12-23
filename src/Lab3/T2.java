package Lab3;
public class T2 extends Thread {
    private int a2;
    private int d2;
    @Override
    public void run() {
        System.out.println("T2 is started");

        // Введення E
        int[] E = new int[Data.N];
        for (int i = 0; i < Data.N; i++) {
            E[i] = 1;
        }
        // Введення MB
        int[][] MB = new int[Data.N][Data.N];
        for (int i = 0; i < Data.N; i++) {
            for (int j = 0; j < Data.N; j++) {
                MB[i][j] = 1;
            }
        }
        Data.resourcesMonitor.setE(E);
        Data.resourcesMonitor.setMB(MB);
        // Сигнал задачі T1, T3, T4 про введення E, MB  Data.inputOutputMonitor.inputSignal();
        // Чекати на введення MB, E в Т1 та MD, d в T3  Data.inputOutputMonitor.waitForInputSignal();
        // Обчислення1 a2 = max(C*MDh)
        a2 = Data.resourcesMonitor.maxValue(Data.H, Data.H*2);  // Обчислення2 a = max(a, a2)
        Data.resourcesMonitor.compare_a(a2);
        // Сигнал задачі T1, T3, T4 про завершення обчислення a  Data.synchronizationMonitor1.a_CalculatedSignal();
        Data.synchronizationMonitor1.waitForCalculated_a();
        // Чекати на завершення обчислень a
        // Копія a2 = a
        a2 = Data.resourcesMonitor.copy_a();
        // Копія d2 = d
        d2 = Data.resourcesMonitor.copy_d();
        // Обчислення3 Wh = a2 + E * (MA * MBh) * d2  Data.resourcesMonitor.calculateResultPart(a2, d2, Data.H,  Data.H*2);
        // Сигнал задачі T3 про завершення обчислення Wh  Data.inputOutputMonitor.outputSignal();
        System.out.println("T2 is finished");
    }
}
