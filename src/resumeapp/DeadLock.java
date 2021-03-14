package resumeapp;

public class DeadLock {
    public static void main(String[] args) {
        String resource1 = "ресурс1";
        String resource2 = "ресурс2";

        new Thread(() -> UseResources(resource1, resource2)).start();
        new Thread(() -> UseResources(resource2, resource1)).start();
    }

    private static void UseResources(final String resource1, final String resource2) {
        synchronized (resource1) {
            System.out.println(Thread.currentThread().getName() + " удерживает " + resource1);
            try {
                Thread.sleep(5);
            } catch (InterruptedException ignored) {
            }
            synchronized (resource2) {
                System.out.println(Thread.currentThread().getName() + " удерживает " + resource1 + " и " + resource2);
            }
        }
    }
}

