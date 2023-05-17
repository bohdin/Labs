package DataInfo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class DataGenerator {
    public static void main(String[] args) {
        Random random = new Random();
        int n = 100;

        // генеруємо матриці
        double[][] MT = new double[n][n];
        double[][] MZ = new double[n][n];
        for (int i = 0; i < MT.length; i++) {
            for (int j = 0; j < MT[i].length; j++) {
                MT[i][j] = random.nextDouble() * 10;
            }
        }
        for (int i = 0; i < MZ.length; i++) {
            for (int j = 0; j < MZ[i].length; j++) {
                MZ[i][j] = random.nextDouble() * 10;
            }
        }

        // генеруємо вектори
        double[] B = new double[n];
        double[] D = new double[n];
        for (int i = 0; i < B.length; i++) {
            B[i] = random.nextDouble() * 1000;
        }
        for (int i = 0; i < D.length; i++) {
            D[i] = random.nextDouble() * 1000;
        }

        // створюємо папку "Data"
        File dataFolder = new File("Data");
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        // зберігаємо матриці у файли
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Data/MT.txt"))) {
            for (int i = 0; i < MT.length; i++) {
                for (int j = 0; j < MT[i].length; j++) {
                    writer.write(Double.toString(MT[i][j]));
                    writer.write(" ");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            System.err.println("Помилка запису у файл MT: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Data/MZ.txt"))) {
            for (int i = 0; i < MZ.length; i++) {
                for (int j = 0; j < MZ[i].length; j++) {
                    writer.write(Double.toString(MZ[i][j]));
                    writer.write(" ");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            System.err.println("Помилка запису у файл MZ: " + e.getMessage());
        }

        // зберігаємо вектори у файли
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Data/B.txt"))) {
            for (int i = 0; i < B.length; i++) {
                writer.write(Double.toString(B[i]));
                writer.write("\n");
            }
        } catch (IOException e) {
            System.err.println("Помилка запису у файл B: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Data/D.txt"))) {
            for (int i = 0; i < D.length; i++) {
                writer.write(Double.toString(D[i]));
                writer.write("\n");
            }
        } catch (IOException e) {
            System.err.println("Помилка запису у файл D: " + e.getMessage());
        }

    }
}

