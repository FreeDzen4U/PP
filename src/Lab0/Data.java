package Lab0;
/**
 * Parallel computing.
 * Labwork 02. Threads in Java
 * Sobchenko Yaroslav
 * IO-02
 * 24.09.2022
 * F1 (1.3): C = A - B * (MA * MC) * e
 * F2 (2.22): MF = (MG * MH) * (MK + ML)
 * F3 (3.23): s = MAX((MO * MP)(R + V))
 */

import java.util.Random;
import java.util.Scanner;

public class Data {

    private static final Random random = new Random();
    private static final Boolean r = false;
    private static final int N = Lab0.N;

    public static int initNumber(String name) {
        Scanner scanner = new Scanner(System.in);
        int result;
        if (N < 5) {
            System.out.println(name + " = ");
            result = scanner.nextInt();
        } else {
            if (r) {
                result = random.nextInt(10) + 1;
            } else {
                result = 1;
            }
        }
        return result;
    }
    public static int[] initVector(String name) {
        Scanner scanner = new Scanner(System.in);
        int[] result = new int[N];
        if (N < 5) {
            for (int i = 0; i < N; i++) {
                System.out.println(name + "[" + i + "] = ");
                result[i] = scanner.nextInt();
            }
        } else {
            if (r) {
                for (int i = 0; i < N; i++) {
                    result[i] = random.nextInt(10) + 1;
                }
            } else {
                for (int i = 0; i < N; i++) {
                    result[i] = 1;
                }
            }
        }
        return result;

    }
    public static int[][] initMatrix(String name) {
        Scanner scanner = new Scanner(System.in);
        int[][] result = new int[N][N];
        if (N < 5) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    System.out.println(name + "[" + i + "]" + "[" + j + "] = ");
                            result[i][j] = scanner.nextInt();
                }
            }
        } else {
            if (r) {
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        result[i][j] = random.nextInt(10) + 1;
                    }
                }
            } else {
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        result[i][j] = 1;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Виведення матриці в термінал
     *
     * @param matrix матриця
     */
    public static void printMatrix(int[][] matrix) {
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }

    // F1 (1.3): C = A - B * (MA * MC) * e
    public static int[] F1(int[] A, int[] B, int[][] MA, int[][] MC, int e) {
        return difVectors(A, multiplyVectorByMatrix(B, multiplyMatrixByNumber(e, multiplyMatrices(MA, MC))));
    }
    // F2 (2.22): MF = (MG * MH) * (MK + ML)
    public static int[][] F2(int[][] MG, int[][] MH, int[][] MK, int[][] ML) {
        return multiplyMatrices(multiplyMatrices(MG, MH), addMatrices(MK, ML));
    }
    // s = MAX((MO * MP)(R + V))
    public static int F3(int[] R, int[] V, int[][] MO, int[][] MP) {
        return maxVector(multiplyVectorByMatrix(addVectors(R, V), multiplyMatrices(MO, MP)));
    }
    /**
     * Відняти вектори A та B
     * @param A Вектор
     * @param B Вектор
     * @return різниця векторів
     */
    private static int[] difVectors(int[] A, int[] B) {
        int[] C = new int[N];
        for (int i = 0; i < N; i++) {
            C[i] = A[i] - B[i];
        }
        return C;
    }
    /**
     * Додати вектори A та B
     * @param A Вектор
     * @param B Вектор
     * @return сума векторів
     */
    private static int[] addVectors(int[] A, int[] B) {
        int[] C = new int[N];
        for (int i = 0; i < N; i++) {
            C[i] = A[i] + B[i];
        }
        return C;
    }
    /**
     * Максимальне значення вектору
     * @param A вектор
     * @return Максимальне значення вектору
     */
    private static int maxVector(int[] A) {
        int r = A[0];
        for (int i = 1; i < N; i++) {
                if (A[i] > r) {
                    r = A[i];
                }
        }
        return r;
    }
    /**
     * Помножити вектор на матрицю
     * @param A Вектор
     * @param MA Матриця
     * @return Вектор
     */
    private static int[] multiplyVectorByMatrix(int[] A, int[][] MA) {
        int[] C = new int[N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                C[i] += A[j] * MA[j][i];
            }
        }
        return C;
    }
    /**
     * Помножити матрицю на число
     * @param a Число
     * @param MB Матриця
     * @return Матриця
     */
    private static int[][] multiplyMatrixByNumber(int a, int[][] MB) {
        int[][] MC = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                MC[i][j] = a * MB[i][j];
            }
        }
        return MC;
    }
    /**
     * Додати матриці
     * @param MA Матриця
     * @param MB Матриця
     * @return Матриця
     */
    private static int[][] addMatrices(int[][] MA, int[][] MB) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                MA[i][j] = MA[i][j] + MB[i][j];
            }
        }
        return MA;
    }
    /**
     * Помножити матриці
     * @param MA Матриця
     * @param MB Матриця
     * @return Матриця
     */
    private static int[][] multiplyMatrices(int[][] MA, int[][] MB) {
        int[][] MC = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    MC[i][j] += MA[i][k] * MB[k][j];
                }
            }
        }
        return MC;
    }
}