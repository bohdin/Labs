import Calculation.Calculation;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Callable;

public class MatrixFunctions {

    public static Callable<double[][]> firstThread(CountDownLatch latch, CyclicBarrier barrier) {
        return () -> {
            Thread.currentThread().setName("First thread");
            System.out.println(Thread.currentThread().getName() + " was started");
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }

            long startTime = System.currentTimeMillis();

            double[][] B = Lab.B;
            double[][] D = Lab.D;
            double[][] MT = Lab.MT;

            double maxB = Calculation.max(B);
            double[][] left = Calculation.multiply(D, MT);
            double[][] right = Calculation.multiply(D, maxB);
            double[][] Y = Calculation.sum(left, right);

            Lab.lock.lock();
            try {
            writeToFile("Lab4_Y",Y);
            writeToConsole("Y", Y);
            } finally {
                Lab.lock.unlock();
            }

            long endTime = System.currentTimeMillis();
            String text_1 = Thread.currentThread().getName() + " was finished in " + (endTime - startTime) + " ms";
            System.out.println(text_1);
            writeTimeToFile("Lab4_Time", text_1);

            latch.countDown();
            return Y;
        };
    }

    public static Callable<double[][]> secondThread(CountDownLatch latch, CyclicBarrier barrier) {
        return () -> {
            Thread.currentThread().setName("Second thread");
            System.out.println(Thread.currentThread().getName() + " was started");
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }

            long startTime = System.currentTimeMillis();

            double[][] MT = Lab.MT;
            double[][] MZ = Lab.MZ;

            double[][] trm = Calculation.sum(MT, MZ);
            double[][] left = Calculation.multiply(MT, trm);
            double[][] right = Calculation.multiply(MZ, MT);
            double[][] MA = Calculation.subtract(left, right);

            Lab.lock.lock();
            try {
            writeToFile("Lab4_MA",MA);
            writeToConsole("MA", MA);
            } finally {
                Lab.lock.unlock();
            }

            long endTime = System.currentTimeMillis();
            String text_2 = Thread.currentThread().getName() + " was finished in " + (endTime - startTime) + " ms";
            System.out.println(text_2);
            writeTimeToFile("Lab4_Time", text_2);

            latch.countDown();
            return MA;
        };
    }

    private static void writeToFile(String fileName, double[][] vector) {
        String file = "./result/" + fileName + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            for (double[] doubles : vector) {
                for (double aDouble : doubles) {
                    writer.print(aDouble + " ");
                }
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeTimeToFile(String fileName, String time) {
        String file = "./result/" + fileName + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            writer.println(time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToConsole(String name, double[][] matrix) {
        System.out.println(name + ":");
        for (double[] doubles : matrix) {
            for (double aDouble : doubles) {
                System.out.print(aDouble + " ");
            }
            System.out.println();
        }
    }
}
