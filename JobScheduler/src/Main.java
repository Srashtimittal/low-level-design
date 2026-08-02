//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
Cluster cluster1=new Cluster("1","4","10");
Cluster cluster2=new Cluster("2","6","9");
ClusterManager clusterManager=ClusterManager.getInstance();
clusterManager.addclusters(cluster1);
clusterManager.addclusters(cluster2);
        JobScheduler jobScheduler=new JobScheduler();
Job job1=new Job('4','4','10','1000');
jobScheduler.submitJob(job1);
        Thread.sleep(15000); // wait for jobs to finish

        jobScheduler.shutdown(); // ← HERE — we added this
    }
}