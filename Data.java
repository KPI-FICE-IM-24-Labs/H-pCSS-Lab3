import java.sql.Array;
import java.util.Arrays;

public class Data {
    public static final int N = 8;
    public static final int P = 4;
    public static final int H = N / P;

    public static int[] X = new int[N];
    public static int[] C = new int[N];
    public static int[] R = new int[N];
    public static int[] B = new int[N];
    public static int[][] MA = new int[N][N];
    public static int[][] MD = new int[N][N];

    public static int getMaxElementOfSubvector(int[] subVector) {
        int max = 0;
        for (int i = 0; i < subVector.length; i++) {
            if (subVector[i] > max) {
                max = subVector[i];
            }
        }
        return max;
    }

    public static int[][] getColumnsOfSubmatrix(int[][] subMatrix, int start, int end) {
        final int[][] result = new int[subMatrix.length][end - start + 1];
        for (int i = 0; i < subMatrix.length; i++) {
            for (int j = start; j <= end; j++) {
                result[i][j - start] = subMatrix[i][j];
            }
        }
        return result;
    }

    public static int[] multiplyVectorByMatrix(int[] vector, int[][] matrix) {
        final int rowLength = matrix[0].length;
        final int[] result = new int[rowLength];

        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < vector.length; j++) {
                result[i] += vector[j] * matrix[j][i];
            }
        }
        return result;
    }

    public static int[][] multiplyMatrixByMatrix(int[][] firstMatrix, int[][] secondMatrix) {
        final int rowLength = secondMatrix[0].length;
        final int[][] result = new int[secondMatrix.length][rowLength];

        for (int i = 0; i < secondMatrix.length; i++) {
            for (int j = 0; j < rowLength; j++) {
                for (int k = 0; k < secondMatrix.length; k++) {
                    result[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }
        return result;
    }

    public static int[] getSubvector(int[] vector, int start, int end) {
        final int[] result = new int[end - start + 1];
        for (int i = start; i <= end; i++) {
            result[i - start] = vector[i];
        }
        return result;
    }

    public static int[] mergeTwoVectors(int[] vector, int[] subVector, int startFrom) {
        for (int i = 0; i < subVector.length; i++) {
            vector[startFrom + i] = subVector[i];
        }
        return vector;
    }

    public static int[] sumTwoVectors(int[] firstVector, int[] secondVector) {
        final int[] result = new int[firstVector.length];
        for (int i = 0; i < firstVector.length; i++) {
            result[i] = firstVector[i] + secondVector[i];
        }
        return result;
    }

    public static int[] multiplyVectorByScalar(int[] vector, int scalar) {
        final int[] result = new int[vector.length];
        for (int i = 0; i < vector.length; i++) {
            result[i] = vector[i] * scalar;
        }
        return result;
    }

    public static void fillMA() {
        for (int i = 0; i < MA.length; i++) {
            Arrays.fill(MA[i], 1);
        }
    }

    public static void fillR() {
        Arrays.fill(R, 1);
    }

    public static void fillMD() {
        for (int i = 0; i < MD.length; i++) {
            Arrays.fill(MD[i], 1);
        }
        ++MD[0][0]; // Для демострації максимуму
    }

    public static void fillB() {
        Arrays.fill(B, 1);
    }

    public static void resultToString() {
        System.out.println("X: " + Arrays.toString(X));
    }
}
