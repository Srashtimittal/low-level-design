import java.util.ArrayList;
import java.util.List;

public class ClusterManager {
    private static ClusterManager instance=null;
    List<Cluster> clusterlist=new ArrayList<>();
    private ClusterManager()
    {

    }
    public static ClusterManager getInstance()
    {
        if(instance==null)
        {
            synchronized (ClusterManager.class)
            {
                if(instance==null)
                {
                    instance= new ClusterManager();
                }
            }
        }
        return instance;
    }
    public void addclusters(Cluster cluster)
    {
        clusterlist.add(cluster);
    }

    public Cluster findCluster(String cpu, String ram)
    {
        for(Cluster cluster: clusterlist)
        {
            if(cluster.allocateResource(cpu,ram))
            {
                return cluster;
            }
        }
        return null;
    }

}
