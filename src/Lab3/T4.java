package Lab3;
public class T4 extends Thread {
    private int a4;
    private int d4;
    @Override
    public void run() {
        System.out.println("T4 is started");

        // Введення MD
        int[][] MD = new int[Data.N][Data.N];
        for (int i = 0; i < Data.N; i++) {
            for (int j = 0; j < Data.N; j++) {
                MD[i][j] = 1;
            }
        }
        // Введення d
        int d = 1;
        Data.resourcesMonitor.setMB(MD);
        Data.resourcesMonitor.set_d(d);
        // Сигнал задачі T1, T2, T3 про введення MD  Data.inputOutputMonitor.inputSignal();
        // Чекати на введення MB, E в Т1 та MD, d в T3  Data.inputOutputMonitor.waitForInputSignal();
        // Обчислення1 a4 = max(C*MDh)
        a4 = Data.resourcesMonitor.maxValue(Data.H*3, Data.H*4);  // Обчислення2 a = max(a, a4)
        Data.resourcesMonitor.compare_a(a4);
        // Сигнал задачі T1, T2, T3 про завершення обчислення a  Data.synchronizationMonitor1.a_CalculatedSignal();
        Data.synchronizationMonitor1.waitForCalculated_a();
        // Чекати на завершення обчислень a
        // Копія a4 = a
        a4 = Data.resourcesMonitor.copy_a();
        // Копія d4 = d
        d4 = Data.resourcesMonitor.copy_d();
        // Обчислення3 Wh = a4 + E * (MA * MBh) * d4  Data.resourcesMonitor.calculateResultPart(a4, d4, Data.H*3,  Data.H*4);
        // Сигнал задачі T3 про завершення обчислення Wh  Data.inputOutputMonitor.outputSignal();
        System.out.println("T4 is finished");
    }
}