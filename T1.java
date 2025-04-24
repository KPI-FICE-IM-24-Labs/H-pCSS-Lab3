public class T1 extends Thread {
    private final Monitor monitor;
    private int a1;
    private int e1;
    private int p1;

    public T1(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread T1 started");

            // Введення значення e
            monitor.setE(1);

            // Сигнал задачам T2, T3, T4 про введення e
            monitor.signalInput();

            // Чекати на введення даних в задачах T2, T3 та T4
            monitor.waitInput();

            // Обчислення1: a1 = max(C * (MA * MDн))
            int[][] subMD = Data.getColumnsOfSubmatrix(Data.MD, 0, Data.H - 1);
            int[][] productMA_MD = Data.multiplyMatrixByMatrix(Data.MA, subMD);
            int[] vectorProduct = Data.multiplyVectorByMatrix(Data.C, productMA_MD);
            a1 = Data.getMaxElementOfSubvector(vectorProduct);

            // Обчислення2: a = max(a, a1)
            monitor.maxA(a1);

            // Сигнал задачам T2, T3, T4 про завершення обчислення a
            monitor.signalCalculateA();

            // Чекати на завершення обчислення a в задачах T2, T3, T4
            monitor.waitCalculateA();

            // Копія а1 = а
            a1 = monitor.getA();

            // Копія e1 = d
            e1 = monitor.getE();

            // Копія p1 = p
            p1 = monitor.getP();

            // Обчислення3: Хн = p1 * a1 * Rн + e1 * Bн
            int[] temp1 = Data.getSubvector(Data.R, 0, Data.H - 1);
            int[] firstSubvector = Data.multiplyVectorByScalar(temp1, a1 * p1); // p1 * a1 * Rн

            int[] temp2 = Data.getSubvector(Data.B, 0, Data.H - 1);
            int[] secondSubvector = Data.multiplyVectorByScalar(temp2, e1); // e1 * Bн

            int[] Xh = Data.sumTwoVectors(firstSubvector, secondSubvector);
            Data.mergeTwoVectors(Data.X, Xh, 0);

            // Чекати на завершення обчислення Xн в задачах T2, T3, T4
            monitor.waitOutput();

            // Виведення Х
            Data.resultToString();
            System.out.println("Thread T1 finished");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
