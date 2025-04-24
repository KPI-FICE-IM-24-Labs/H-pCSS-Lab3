public class T2 extends Thread {
    private final Monitor monitor;
    private int a2;
    private int e2;
    private int p2;

    public T2(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread T2 started");

            // Введення C, MA
            Data.fillC();
            Data.fillMA();

            // Сигнал задачам T1, T3, T4 про введення C, MA
            monitor.signalInput();

            // Чекати на введення даних в задачах T1, T3 та T4
            monitor.waitInput();

            // Обчислення1: a2 = max(C * (MA * MDн))
            int[][] subMD = Data.getColumnsOfSubmatrix(Data.MD, Data.H, Data.H * 2 - 1);
            int[][] productMA_MD = Data.multiplyMatrixByMatrix(Data.MA, subMD);
            int[] vectorProduct = Data.multiplyVectorByMatrix(Data.C, productMA_MD);
            a2 = Data.getMaxElementOfSubvector(vectorProduct);

            // Обчислення2: a = max(a, a2)
            monitor.maxA(a2);

            // Сигнал задачам T1, T3, T4 про завершення обчислення a
            monitor.signalCalculateA();

            // Чекати на завершення обчислення a в задачах T1, T3, T4
            monitor.waitCalculateA();

            // Копія а2 = а
            a2 = monitor.getA();

            // Копія e2 = d
            e2 = monitor.getE();

            // Копія p2 = p
            p2 = monitor.getP();

            // Обчислення3: Хн = p2 * a2 * Rн + e2 * Bн
            int[] temp = Data.getSubvector(Data.R, Data.H, Data.H * 2 - 1);
            int[] firstSubvector = Data.multiplyVectorByScalar(temp, a2 * p2); // p2 * a2 * Rн

            temp = Data.getSubvector(Data.B, Data.H, Data.H * 2 - 1);
            int[] secondSubvector = Data.multiplyVectorByScalar(temp, e2); // e2 * Bн

            int[] Xh = Data.sumTwoVectors(firstSubvector, secondSubvector);
            Data.mergeTwoVectors(Data.X, Xh, Data.H);

            // Сигнал задачі Т1 про завершення обчислення Xн
            monitor.signalOutput();

            System.out.println("Thread T2 finished");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
