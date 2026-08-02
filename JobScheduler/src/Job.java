public class Job {
    String jobid;
    String cpu;
    String ram;
    String time;
    public Job(String jobid,String cpu,String ram,String time)
    {
        this.jobid=jobid;
        this.cpu=cpu;
        this.ram=ram;
        this.time=time;
    }
    public String getTime(String cpu,String time)
    {
        return time;
    }
    public String getReqCpu()
    {
        return cpu;
    }
    public String getReqRam()
    {
        return ram;
    }

}
