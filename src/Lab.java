import DataInfo.DataReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Lab {

    public static double[][]  MT, MZ, MA;
    public static double[][]  B, D, Y;
    public static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        DataReader dataReader = new DataReader();
        int n = 100;

        MT = dataReader.readMatrix("Data/MT.txt", n, n);
        MZ = dataReader.readMatrix("Data/MZ.txt", n, n);

        B = dataReader.readVector("Data/B.txt", 1, n);
        D = dataReader.readVector("Data/D.txt", 1, n);

        CountDownLatch latch = new CountDownLatch(2);
        CyclicBarrier barrier = new CyclicBarrier(2);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<double[][]> equation1 = executorService.submit(MatrixFunctions.firstThread(latch, barrier));
        Future<double[][]> equation2 = executorService.submit(MatrixFunctions.secondThread(latch, barrier));

        executorService.shutdown();

        try {
            latch.await();
            Y = equation1.get();
            MA = equation2.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Program finished");
        String text = "Total time: " + (endTime - startTime) + " ms";
        System.out.println(text);
        MatrixFunctions.writeTimeToFile("Lab4_Time", text);
    }
}

