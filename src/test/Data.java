package test;
/**
 * Parallel computing.
 * Labwork 02. Threads in Java
 * asda
 * IO-02
 * 24.09.2022
 *
 * F1 (1.15): d = MAX((A + B + C) * (MA * ME))
 * F2 (2.8): MF = g * TRANS(MG) + f * (MK * ML)
 * F3 (3.9): O = SORT(P) * (MR * MS)
 *
 * F1 (1.3): C = A - B*(MA*MC)*e
 * F2 (2.22): MF = (MG*MH)*(MK + ML)
 * F3 (3.23): s = MAX((MO*MP)(R + V))
 */
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
public class Data {
    public int[] A, B, C, P, R, V;
    public int[][] MA, MC, ME, MG, MK, ML, MR, MS,  MO, MP;
    public int g, f, c;
    private int N;
    private boolean r;
    private Random random;
    public Data(int N, boolean random) {
        // N - розмір векторів та матриць
        // random - заповнення елементів випадковими числами(якщо random == true), або
        // встановлення всіх значень 1 (якщо random == false)
        this.N = N;
        this.r = random;
        if (this.r) {
            this.random = new Random();
        }
        // При малих значеннях N(< 5), дані вводяться з клавіатури
        if (N < 5) {
            g = inputNumber("g");
            f = inputNumber("f");
            A = inputVector("A");
            B = inputVector("B");
            C = inputVector("C");
            P = inputVector("P");
            MA = inputMatrix("MA");
            ME = inputMatrix("ME");
            MG = inputMatrix("MG");
            MK = inputMatrix("MK");
            ML = inputMatrix("ML");
            MR = inputMatrix("MR");
            MS = inputMatrix("MS");
        } else { // Інакше, значення будуть встановлені автоматично(випадковим чином, або 1)
            g = initNumber();
            f = initNumber();
            A = initVector();
            B = initVector();
            C = initVector();
            P = initVector();
            MA = initMatrix();
            ME = initMatrix();
            MG = initMatrix();
            MK = initMatrix();
            ML = initMatrix();
            MR = initMatrix();
            MS = initMatrix();
        }
    }
    public int getN() {
        return this.N;
    }
    public int initNumber() {
        if (r) {
            return random.nextInt(100);
        } else {
            return 1;
        }
    }
    public int[] initVector() {
        int[] result = new int[N];
        if (r) {
            for (int i = 0; i < N; i++)
                result[i] = random.nextInt(100);
        } else {
            Arrays.fill(result, 1);
        }
        return result;
    }
    public int[][] initMatrix() {
        int[][] result = new int[N][N];
        if (r) {
            for (int i = 0; i < N; i++)
                for (int j = 0; i < N; i++)
                    result[i][j] = random.nextInt(100);
        } else {
            for (int i = 0; i < N; i++)
                Arrays.fill(result[i], 1);
        }
        return result;
    }
    public int inputNumber(String name) {
        System.out.print(name + " = ");
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }
    public int[] inputVector(String name) {
        System.out.println(name + ": ");
        int[] vector = new int[N];
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < N; i++) {
            vector[i] = sc.nextInt();
        }
        return vector;
    }
    public int[][] inputMatrix(String name) {
        System.out.println(name + ": ");
        int[][] matrix = new int[N][N];
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        return matrix;
    }
    /**
     * Виведення матриці в термінал
     * @param matrix матриця
     */
    public void printMatrix(int[][] matrix) {
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }
    // F1 (1.15): d = MAX((A + B + C) * (MA*ME))
    public int f1(int[] A, int[] B, int[] C, int[][] MA, int[][] ME) {
        return maxVector(multiplyVectorByMatrix(addVectors(A, addVectors(B, C)),
                multiplyMatrices(MA, ME)));
    }
    // F2 (2.8): MF = g * TRANS(MG)+ f * (MK * ML)
    public int[][] f2(int g, int[][] MG, int f, int[][] MK, int[][] ML) {
        return addMatrices(multiplyMatrixByNumber(g, transposeMatrix(MG)),
                multiplyMatrixByNumber(f, multiplyMatrices(MK, ML)));
    }
    // F3 (3.9): O = SORT(P) * (MR * MS)
    public int[] f3(int[] P, int[][] MR, int[][] MS) {
        return multiplyVectorByMatrix(sortVector(P), multiplyMatrices(MR, MS));
    }
    /**
     * Додати вектори A та B
     * @param A Вектор
     * @param B Вектор
     * @return сума векторів
     */
    private int[] addVectors(int[] A, int[] B) {
        int[] C = new int[N];
        for (int i = 0; i < N; i++) {
            C[i] = A[i] + B[i];
        }
        return C;
    }
    /**
     * Максимальне значення вектору
     * @param A Вектор
     * @return Максимальне значення вектору
     */
    private int maxVector(int[] A) {
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
    private int[] multiplyVectorByMatrix(int[] A, int[][] MA) {
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
    private int[][] multiplyMatrixByNumber(int a, int[][] MB) {
        int[][] MC = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                MC[i][j] = a * MB[i][j];
            }
        }
        return MC;
    }
    /**
     * Транспонувати матрицю
     * @param MA Матриця
     * @return Матриця
     */
    private int[][] transposeMatrix(int[][] MA) {
        int buf;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= i; j++) {
                buf = MA[i][j];
                MA[i][j] = MA[j][i];
                MA[j][i] = buf;
            }
        }
        return MA;
    }
    /**
     * Додати матриці
     * @param MA Матриця
     * @param MB Матриця
     * @return Матриця
     */
    private int[][] addMatrices(int[][] MA, int[][] MB) {
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
    private int[][] multiplyMatrices(int[][] MA, int[][] MB) {
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
    /**
     * Відсортувати вектор
     * @param A Вектор
     * @return Вектор
     */
    private int[] sortVector(int[] A) {
        Arrays.sort(A);
        return A;
    }
}