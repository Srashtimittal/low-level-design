public class Cluster {
    String clusterid;
    String availablecpu;
    String availableram;
    Lock resourcelock;

    Cluster(String clusterid, String ram, String cpu, String availablecpu, String availableram) {
        this.clusterid = clusterid;
        this.ram = ram;
        this.cpu = cpu;
        this.availablecpu = availablecpu;
        this.availableram = availableram;
        resourcelock = new ReetrantLock();
    }

    public boolean findAvailable(String cpu, String ram) {
        if (availablecpu >= cpu && availableram >= ram) {
            return true;
        }
        return false;
    }

    public boolean allocateResource(String cpu, String ram) {
        try {
            if (resourcelock.tryLock(6, TimeUnit.MILLISECONDS)) {
                try {
                    if (findAvailable(cpu, ram)) {
                        availablecpu = availablecpu - cpu;
                        availableram = availableram - ram;
                        return true;
                    } else {
                        return false;
                    }
                } finally {
                    resourcelock.unlock();
                }
            } else {
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
public void deallocateRes(String cpu,String ram)
{
    resourcelock.lock();
    try {
        availableram = availableram + ram;
        availablecpu = availablecpu + cpu;
    }
    finally {
        resourcelock.unlock();
    }
}

}
