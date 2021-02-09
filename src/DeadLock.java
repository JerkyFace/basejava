class SharedResource {
    String resourceName;

    public SharedResource(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }
}

public class DeadLock implements Runnable {


    private final SharedResource resource1 = new SharedResource("ресурс1");
    private final SharedResource resource2 = new SharedResource("ресурс2");

    private void job1() throws InterruptedException {
        UseResource(resource2, resource1);
    }

    private void job2() throws InterruptedException {
        UseResource(resource1, resource2);
    }

    private void UseResource(final SharedResource resource1, final SharedResource resource2) throws InterruptedException {
        synchronized (resource1) {
            System.out.println(Thread.currentThread().getName() + " удерживает " + resource1.getResourceName());
            Thread.sleep(5);
            synchronized (resource2) {
                System.out.println(Thread.currentThread().getName() + " удерживает " + resource2.getResourceName());
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

