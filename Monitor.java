public class Monitor {
    private int a = 0;
    private int p;
    private int e;

    private int F1 = 0;
    private int F2 = 0;
    private int F3 = 0;

    public synchronized void setP(int p) {
        this.p = p;
    }

    public synchronized void setE(int e) {
        this.e = e;
    }

    public synchronized void signalInput() {
        F1++;
        if (F1 == 4) {
            notifyAll();
        }
    }

    public synchronized void waitInput() throws InterruptedException {
        while (F1 < 4) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void signalCalculateA() {
        F2++;
        if (F2 == 4) {
            notifyAll();
        }
    }

    public synchronized void waitCalculateA() throws InterruptedException {
        while (F2 < 4) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void signalOutput() {
        F3++;
        if (F3 == 3) {
            notifyAll();
        }
    }

    public synchronized void waitOutput() throws InterruptedException {
        while (F3 < 3) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void maxA(int a) {
        this.a = Math.max(a, this.a);
    }

    public synchronized int getE() {
        return this.e;
    }

    public synchronized int getA() {
        return this.a;
    }

    public synchronized int getP() {
        return this.p;
    }
}
