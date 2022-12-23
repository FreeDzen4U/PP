package Lab3;
import java.util.Arrays;
public class Data {
    public static final int N = 4;
    public static final int P = 4;
    public static final int H = N / P;
    public static ResourcesMonitor resourcesMonitor = new  ResourcesMonitor();
    public static InputOutputMonitor inputOutputMonitor = new
            InputOutputMonitor();
    public static SynchronizationMonitor1 synchronizationMonitor1 =  new SynchronizationMonitor1();
    public static class ResourcesMonitor {
        private int[][] MB, MA, MD;
        private int[] E, C;
        private int a, d;
        private int[]W = new int[N];
        public void calculateResultPart(int ai, int start, int  end) {
            writeSubVectorToResult(addingSkalarAndVector(
                    ai, multiplicationVectorBySubMatrix(
                            multiplicationSubVectorByScalar(E, d, start, end),
                            multiplicationMatrixAndSubMatrix(MA, MB, start, end), start, end),
                            start, end),
                    start, end);
        }
        public void writeSubVectorToResult(int[] X, int start, int  end) {
            if (end - start >= 0) System.arraycopy(X, start, W, start,  end - start);
        }
        private static int[][]
        multiplicationMatrixAndSubMatrix(int[][] MX, int[][] MY, int start,  int end) {
            int[][] result = new int[N][N];
            for (int i = start; i < end; i++) {
                for (int j = 0; j < N; j++) {
                    int s = 0;
                    for (int k = 0; k < N; k++) {
                        s += MY[i][k] * MX[k][j];
                    }
                    result[i][j] = s;
                }
            }
            return result;
        }
        public int[] multiplicationSubVectorByScalar(int[] X, int x,  int start, int end) {
            int[] result = new int[N];
            for (int i = start; i < end; i++) {
                result[i] = X[i] * x;
            }
            return result;
        }
        public int[] addingSkalarAndVector(int x, int[] Y, int start, int  end) {
            int[] result = new int[N];
            for (int i = start; i < end; i++) {
                result[i] = x + Y[i];
            }
            return result;
        }
        public int[] multiplicationVectorBySubMatrix(int[] X, int[][]  MX, int start, int end) {
            int[] result = new int[N];
            for (int i = start; i < end; i++) {
                for (int j = 0; j < N; j++) {
                    result[i] += X[j] * MX[i][j];
                }
            }
            return result;
        }

        public int maxValue(int start, int end) {
            int[] K = multiplicationVectorBySubMatrix(C, MD, start, end);

            int max = K[start];
            for (int j = start + 1; j < end; j++) {
                if (K[j] > max) {
                    max = K[j];
                }
            }
            return max;
        }
        private boolean first = false;
        public synchronized void compare_a(int ai) {
            if (first) {
                if (ai < this.a) {
                    this.a = ai;
                }
            } else {
                this.a = ai;
                first = true;
            }
        }
        public void printVector(int[] X) {
            System.out.println(Arrays.toString(X));
        }
        public synchronized void setMB(int[][] MB) {
            this.MB = MB;
        }
        public synchronized void setE(int[] E) {
            this.E = E;
        }
        public synchronized void setC(int[] C) {
            this.C = C;
        }
        public synchronized void setMA(int[][] MA) {  this.MA = MA;
        }
        public synchronized void setMD(int[][] MD) {  this.MD = MD;
        }
        public synchronized void set_d(int d) {  this.d = d;
        }
        public synchronized int copy_a() {  return a;
        }
        public synchronized int copy_d() {  return d;
        }
        public synchronized int[] getW() {  return this.W;
        }
    }
    public static class InputOutputMonitor {  private int F1 = 0;
        private int F2 = 0;
        public synchronized void inputSignal() {  F1++;
            if (F1 >= 3) {
                notifyAll();
            }
        }
        public synchronized void waitForInputSignal() {  try {
            if (F1 < 3) {
                wait();
            }
        } catch (Exception w) {
            w.printStackTrace();
        }
        }
        public synchronized void outputSignal() {  F2++;
            if (F2 >= 3) {
                notify();
            }
        }
        public synchronized void waitForOutputSignal() {  try {
            if (F2 < 3) {
                wait();
            }
        } catch (Exception w) {
            w.printStackTrace();
        }
        }
    }
    public static class SynchronizationMonitor1 {  private int F1 = 0;
        public synchronized void a_CalculatedSignal() {  F1++;
            if (F1 >= 3) {
                notifyAll();
            }
        }
        public synchronized void waitForCalculated_a() {  try {
            if (F1 < 3) {
                wait();
            }
        } catch (Exception w) {
            w.printStackTrace();
        }
        }
    }
}
