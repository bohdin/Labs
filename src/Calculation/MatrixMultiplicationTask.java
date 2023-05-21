package Calculation;
import java.util.concurrent.RecursiveTask;

public class MatrixMultiplicationTask extends RecursiveTask<double[][]> {
    private final double[][] matrix1;
    private final double[][] matrix2;
    private final int startRow;
    private final int endRow;
    private final int startCol;
    private final int endCol;
    private static final int THRESHOLD = 10;

    public MatrixMultiplicationTask(double[][] matrix1, double[][] matrix2, int startRow, int endRow, int startCol, int endCol) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.startRow = startRow;
        this.endRow = endRow;
        this.startCol = startCol;
        this.endCol = endCol;
    }

    @Override
    protected double[][] compute() {
        int rows = endRow - startRow + 1;
        int cols = endCol - startCol + 1;

        if (rows <= THRESHOLD && cols <= THRESHOLD) {
            // Виконати перемноження матриць прямо в потоці
            return multiplyMatrices();
        } else {
            int midRow = (startRow + endRow) / 2;
            int midCol = (startCol + endCol) / 2;

            // Розбити задачу на чотири підзадачі
            MatrixMultiplicationTask topLeft = new MatrixMultiplicationTask(matrix1, matrix2, startRow, midRow, startCol, midCol);
            MatrixMultiplicationTask topRight = new MatrixMultiplicationTask(matrix1, matrix2, startRow, midRow, midCol + 1, endCol);
            MatrixMultiplicationTask bottomLeft = new MatrixMultiplicationTask(matrix1, matrix2, midRow + 1, endRow, startCol, midCol);
            MatrixMultiplicationTask bottomRight = new MatrixMultiplicationTask(matrix1, matrix2, midRow + 1, endRow, midCol + 1, endCol);

            // Викликати підзадачі паралельно
            invokeAll(topLeft, topRight, bottomLeft, bottomRight);

            // Отримати результати підзадач і об'єднати їх у кінцеву матрицю
            double[][] result = new double[rows][cols];
            mergeResults(result, topLeft.join(), topRight.join(), bottomLeft.join(), bottomRight.join());

            return result;
        }
    }

    private double[][] multiplyMatrices() {
        int rows = endRow - startRow + 1;
        int cols = endCol - startCol + 1;
        double[][] result = new double[rows][cols];

        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                double sum = 0.0;
                double c = 0.0; // Змінна для алгоритму типу Kahan

                for (int k = 0; k < matrix1[0].length; k++) {
                    double prod = matrix1[i][k] * matrix2[k][j];
                    double y = prod - c;
                    double t = sum + y;
                    c = (t - sum) - y;
                    sum = t;
                }

                result[i - startRow][j - startCol] = sum;
            }
        }

        return result;
    }

    private void mergeResults(double[][] result, double[][] topLeft, double[][] topRight, double[][] bottomLeft, double[][] bottomRight) {
        int rows = endRow - startRow + 1;
        int cols = endCol - startCol + 1;
        int midRow = (startRow + endRow) / 2;
        int midCol = (startCol + endCol) / 2;

        // Об'єднати результати підзадач у кінцеву матрицю
        mergeSubMatrix(result, topLeft, 0, 0);
        mergeSubMatrix(result, topRight, 0, midCol - startCol + 1);
        mergeSubMatrix(result, bottomLeft, midRow - startRow + 1, 0);
        mergeSubMatrix(result, bottomRight, midRow - startRow + 1, midCol - startCol + 1);
    }

    private void mergeSubMatrix(double[][] result, double[][] subMatrix, int rowOffset, int colOffset) {
        for (int i = 0; i < subMatrix.length; i++) {
            for (int j = 0; j < subMatrix[0].length; j++) {
                result[i + rowOffset][j + colOffset] = subMatrix[i][j];
            }
        }
    }
}