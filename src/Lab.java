import DataInfo.DataReader;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Lab {

    public static double[][]  MT, MZ;
    public static double[][]  B, D;

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

        executorService.execute(MatrixFunctions.firstThread(latch, barrier));
        executorService.execute(MatrixFunctions.secondThread(latch, barrier));

        executorService.shutdown();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Program finished");
        String text = "Total time: " + (endTime - startTime) + " ms";
        System.out.println(text);
        MatrixFunctions.writeTimeToFile("Lab3_Time", text);
    }
}

