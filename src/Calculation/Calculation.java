package Calculation;

public class Calculation {
    public static double[][] multiply(double[][] A, double[][] B) {
        int n = A.length;
        int m = B[0].length;
        int p = B.length;
        double[][] C = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                double sum = 0.0;
                double c = 0.0;
                for (int k = 0; k < p; k++) {
                    double y = A[i][k] * B[k][j] - c;
                    double t = sum + y;
                    c = (t - sum) - y;
                    sum = t;
                }
                C[i][j] = sum;
            }
        }
        return C;
    }



    public static double[][] sum(double[][] A, double[][] B) {
        int m = A.length;
        int n = A[0].length;
        double[][] C = new double[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }

        return C;
    }


    public static double max(double[][] x) {
        double max = x[0][0];
        for(double[] doubles : x){
            for(int j = 0; j < x[0].length; j++){
                if(doubles[j] > max) max = doubles[j];
            }
        }
        return max;
    }


    public static double[][] subtract(double[][] A, double[][] B) {
        int m = A.length;
        int n = A[0].length;
        double[][] C = new double[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] - B[i][j];
            }
        }
        return C;
    }


    public static double[][] multiply(double[][] vector, double scalar) {
        int rows = vector.length;
        int columns = vector[0].length;
        double[][] C = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++)
            C[i][j] = scalar * vector[i][j];
        }
        return C;
    }
}
