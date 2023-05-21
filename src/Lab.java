import DataInfo.DataReader;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import Calculation.MatrixMultiplicationTask;

public class Lab {

    public static double[][]  MT, MZ, MA, result;
    public static double[][]  B, D, Y;
    public static Lock lock = new ReentrantLock();
    public static int n = 100;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        DataReader dataReader = new DataReader();

        MT = dataReader.readMatrix("Data/MT.txt", n, n);
        MZ = dataReader.readMatrix("Data/MZ.txt", n, n);

        B = dataReader.readVector("Data/B.txt", 1, n);
        D = dataReader.readVector("Data/D.txt", 1, n);

        CountDownLatch latch = new CountDownLatch(2);
        CyclicBarrier barrier = new CyclicBarrier(2);

        ForkJoinPool pool = new ForkJoinPool(2);

        MatrixMultiplicationTask task = new MatrixMultiplicationTask(MZ, MT,0, 99, 0, 99);
        result = pool.invoke(task);
        ForkJoinTask<double[][]> equation1 = pool.submit(MatrixFunctions.firstThread(latch, barrier));
        ForkJoinTask<double[][]> equation2 = pool.submit(MatrixFunctions.secondThread(latch, barrier));

        pool.shutdown();


        try {
            latch.await();
            Y = equation1.get();
            MA = equation2.get();
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Program finished");
        String text = "Total time: " + (endTime - startTime) + " ms";
        System.out.println(text);
        MatrixFunctions.writeTimeToFile("Lab5_Time", text);
    }
}

