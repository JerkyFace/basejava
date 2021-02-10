public class DeadLock implements Runnable {

    private final String resource1 = "ресурс 1";
    private final String resource2 = "ресурс 2";

    private void job1() throws InterruptedException {
        UseResource(resource1, resource2);
    }

    private void job2() throws InterruptedException {
        UseResource(resource2, resource1);
    }

    private void UseResource(final String resource1, final String resource2) throws InterruptedException {
        synchronized (resource1) {
            System.out.println(Thread.currentThread().getName() + " удерживает " + resource1);
            Thread.sleep(5);
            synchronized (resource2) {
                System.out.println(Thread.currentThread().getName() + " удерживает " + resource1 + " и " + resource2);
            }
        }
    }

    @Override
    public void run() {
        try {
            job1();
            job2();
        } catch (Exception ignore) {
        }
    }

    public static void main(String[] args) {
        DeadLock deadLock = new DeadLock();
        new Thread(deadLock).start();
        new Thread(deadLock).start();
    }
}

