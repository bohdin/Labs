package DataInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataReader {

    // метод для зчитування матриці з файлу
    public static double[][] readMatrix(String filename, int rows, int columns) {
        double[][] matrix = new double[rows][columns];

        try (BufferedReader reader = new BufferedReader(new FileReader((filename)))) {
            for (int i = 0; i < rows; i++) {
                String line = reader.readLine();
                String[] parts = line.split(" ");
                for (int j = 0; j < columns; j++) {
                    matrix[i][j] = Double.parseDouble(parts[j]);
                }
            }
        } catch (IOException e) {
            System.err.println("Помилка зчитування файлу " + filename + ": " + e.getMessage());
        }

        return matrix;
    }
    public static double[][] readVector(String filename, int rows, int columns) {
        double[][] vector = new double[rows][columns];

        try (BufferedReader reader = new BufferedReader(new FileReader((filename)))) {
            for (int j = 0; j < columns; j++) {
                String line = reader.readLine();
                vector[0][j] = Double.parseDouble(line);
            }
        } catch (IOException e) {
            System.err.println("Помилка зчитування файлу " + filename + ": " + e.getMessage());
        }

        return vector;
    }
}
