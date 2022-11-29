package Lab1;
import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
public class Data {
    public static final int N = 4;
    public static final int P = 4;
    public static final int H = N / P;
    public static int[][] MM = new int[N][N];
    public static int[][] MX = new int[N][N];
    public static int[][] MT = new int[N][N];
    public static int[] A = new int[N];
    public static int[] B = new int[N];
    public static int[] Z = new int[N];
    public static int[] X = new int[N];
    public static int p = 0;
    public static int d = 0;
    public static AtomicInteger a = new AtomicInteger();
    public static final Object W = new Object();
    public static final Object Y = new Object();
    // Елементи взаємодії
    public static Semaphore Sem1 = new Semaphore(1, true);
    public static Semaphore Sem2 = new Semaphore(0, true);
    public static Semaphore Sem3 = new Semaphore(0, true);
    public static Semaphore Sem4 = new Semaphore(0, true);

    public static Semaphore Sem5 = new Semaphore(0, true);
    public static Semaphore Sem6 = new Semaphore(0, true);
    public static Semaphore Sem7 = new Semaphore(0, true);
    public static Semaphore Sem8 = new Semaphore(0, true);
    public static Semaphore Sem9 = new Semaphore(0, true);
    public static Semaphore Sem10 = new Semaphore(0, true);
    public static Semaphore Sem11 = new Semaphore(0, true);
    public static Semaphore Sem12 = new Semaphore(0, true);
    public static Semaphore Sem13 = new Semaphore(0, true);
    public static Semaphore Sem14 = new Semaphore(0, true);
    public static Semaphore Sem15 = new Semaphore(0, true);
    public static void calculateResultPart(int ai, int pi, int start, int
            end) {
        writeSubVectorToResult(
                Data.addSubVectors(
                        multiplySubVectorByScalar(multiplyVectorByMatrix(X, multiplyMatrixByMatrix(MT, MX, start, end), start, end), pi, start, end),
                        multiplySubVectorByScalar(Z, ai, start, end),
                    start, end),
                start, end);
    }
    public static void writeSubVectorToResult(int[] X, int start, int end) {
        if (end - start >= 0) System.arraycopy(X, start, A, start, end - start);
    }
    // Обчислення1 GH = sort(D * (ME * MMH))
    public static void calculation1(int start, int end) {
        for (int i = start; i < end; i++) {
            int l2 = 0;
            for (int j = 0; j < N; j++) {
                l2 += MM[i][j] * Z[j];
            }
            X[i] = d * B[i] + l2;
        }
        Arrays.sort(X, start, end);
    }
    // R = sort*(X, Y)
    public static void mergeSort(int[] R, int[] X, int[] Y, int startX, int startY, int endX, int endY) {
        int[] buffer = new int[endX - startX + endY - startY];
        int i = startX, j = startY, k = 0;
        while (i < endX && j < endY) {
            if (X[i] <= Y[j]) {
                buffer[k++] = X[i++];
            }
            else {
                buffer[k++] = Y[j++];
            }
        }
        while (i < endX) {
            buffer[k++] = X[i++];
        }
        while (j < endY) {
            buffer[k++] = Y[j++];
        }
        System.arraycopy(buffer, 0, R, startX*2, k);
    }

    public static int[][] multiplyMatrixByMatrix(int[][] MA, int[][] MB, int start, int end) {
        int[][] MT = new int[N][end - start];
        for (int i = 0; i < N; i++) {
            int c = 0;
            for (int j = start; j < end; j++) {
                MT[i][c] = 0;
                for (int k = 0; k < N; k++) {
                    MT[i][c] = MA[i][k] * MB[k][j];
                }
                c++;
            }
        }
        return MT;
    }

    static int[] multiplyVectorByMatrix(int[] A, int[][] MA, int start, int end) {
        int[] B = new int[N];
        for (int i = start; i < end; i++) {
            for (int j = 0; j < N; j++) {
                B[i] += A[j] * MA[i][j];
            }
        }
        return B;
    }

    static int[] multiplySubVectorByScalar(int[] X, int x, int start, int end) {
        for (int i = start; i < end; i++) {
            X[i] = X[i] * x;
        }
        return X;
    }

    static int[] addSubVectors(int[] X, int[] Y, int start, int end) {
        for (int i = start; i < end; i++) {
            X[i] = X[i] + Y[i];
        }
        return X;
    }

    static int scalarProductPart(int[] X, int[] Y, int start, int end) {
        int result = 0;
        for (int i = start; i < end; i++) {
            result += X[i] * Y[i];
        }
        return result;
    }

    public static void printVector(int[] X) {
        System.out.println(Arrays.toString(X));
    }
}
