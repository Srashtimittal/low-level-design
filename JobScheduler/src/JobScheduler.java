public class JobScheduler {
    private ClusterManager clusterManager;
    private BlockingQueue<Job> jobQueue;
    private ExecutorService executorService;

    public JobScheduler() {
        clusterManager = ClusterManager.getInstance();
        jobQueue = new ArrayBlockingQueue<>(100);
        executorService = Executors.newFixedThreadPool(5);
        // Allow multiple threads to pick and execute jobs in parallel,
        for (int i = 0; i < 5; i++) {
            executorService.submit(this::executeJob);
        }
    }
    public void submitJob(Job job) {
        System.out.println("Submitting job " + job.getId() + " at " + System.currentTimeMillis());
        boolean accepted = jobQueue.offer(job);   // changed from add() to offer()
        if (accepted) {
            System.out.println("Job " + job.getId() + " added to the queue at " + System.currentTimeMillis());
        } else {
            System.out.println("Job " + job.getId() + " REJECTED — queue is full");
        }
    }
//    public void submitJob(Job job) {
//        System.out.println("Submitting job "+job.getId() + " at "+System.currentTimeMillis());
//        jobQueue.add(job);
//        System.out.println("Job "+job.getId()+" added to the queue at "+System.currentTimeMillis());
//    }

    private void executeJob() {
        while (true) {
            System.out.println("Thread id: "+Thread.currentThread().getId());
            try {
                Job job = jobQueue.take();
                Cluster cluster = clusterManager.getAvailableCluster(job.getRequiredCpu(), job.getRequiredRam());
                if (Objects.isNull(cluster)) {
                    System.out.println("No cluster available for the job " + job.getId());
                    jobQueue.offer(job);
                    continue;
                }
                startJob(cluster, job);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
                break;
            }
        }
    }

    private void startJob(Cluster cluster, Job job) {
        long currentTime = System.currentTimeMillis();
        System.out.println("Job " + job.getId() + " started on cluster "+cluster.getId() + " at " + currentTime);
        try {
            Thread.sleep(job.getExecutionTimeInSeconds() * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cluster.deallocateResource(job.getRequiredCpu(), job.getRequiredRam());
        }
        System.out.println("Job " + job.getId() + " completed on cluster "+cluster.getId() + " at " + System.currentTimeMillis());
        System.out.println("Total time taken for job "+job.getId()+" is "+(System.currentTimeMillis()-currentTime)/1000+"s");
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow(); // ← HERE — fallback if graceful shutdown takes too long
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}