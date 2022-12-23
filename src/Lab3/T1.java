package Lab3;
public class T1 extends Thread {
    private int a1;
    private int d1;
    @Override
    public void run() {
        System.out.println("T1 is started");
        // Чекати на введення MB, E в Т1; C, MA в T2; MD, d в T3  Data.inputOutputMonitor.waitForInputSignal();
        // Обчислення1 a1 = max(C*MDh)
        a1 = Data.resourcesMonitor.maxValue(0, Data.H);  // Обчислення2 a = max(a, a1)
        Data.resourcesMonitor.compare_a(a1);
        // Сигнал задачі T1, T2, T3 про завершення обчислення a  Data.synchronizationMonitor1.a_CalculatedSignal();  // Чекати на завершення обчислень a
        Data.synchronizationMonitor1.waitForCalculated_a();
        // Копія a1 = a
        a1 = Data.resourcesMonitor.copy_a();
        // Копія d1 = d
        d1 = Data.resourcesMonitor.copy_d();
        // Обчислення3 Wh = a1 + E * (MA * MBh) * d1  Data.resourcesMonitor.calculateResultPart(a1, d1, 0, Data.H);
        // Сигнал задачі T3 про завершення обчислення Wh  Data.inputOutputMonitor.outputSignal();
        System.out.println("T1 is finished");
    }
}
