import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

class Proc {
  private int in, left, duration, delay;
  private String name;
  Proc(String _name, int _in, int _duration){
    this.name = _name;
    this.in = _in;
    this.left = _duration;
    this.duration = _duration;
  }
  public void dec(){
      in -= 1;
  }
  public boolean isFinished() {
    if (left > 0){
      return false;
    } else {
      return true;
    }
  }
  public void setDelay(int curr) {
    delay = curr - in + 1 - duration;
  }
  public int getIn() {
    return in;
  }
  public int getLeft() {
    return left;
  }
  public int getDuration() {
    return duration;
  }
  public int getDelay() {
    return delay;
  }
  public int getFull(){
    return duration + delay;
  }
  public String getName(){
    return name;
  }
}
class Planner {
  ArrayList<Proc> queue = new ArrayList<Proc>(),
                  queue1 = new ArrayList<Proc>(),
                  queue2 = new ArrayList<Proc>(),
                  queue3 = new ArrayList<Proc>();
  void fInit(String fileName){
    try (FileReader reader = new FileReader(fileName)){
      int t = 0;
      Proc proc;
      queue.clear();
      queue1.clear();
      queue2.clear();
      queue3.clear();

      String name = "",
            in = "",
            duration = "";
      int inp = reader.read();
      while(inp != -1){
        if (inp == ';'){
          inp = reader.read();
          while(inp != -1){
            if (inp == ';'){
              inp = reader.read();
              while(inp != -1){
                if (inp == '\n'){
                  proc = new Proc(name, Integer.parseInt(in), Integer.parseInt(duration));
                  queue.add(proc);
                  //
                  System.out.println("Name:"+proc.getName()+" In:"+proc.getIn()+" duration :"+proc.getDuration());
                  //
                  name = "";
                  in = "";
                  duration = "";
                  break;
                }
                duration += (char)inp;
                inp = reader.read();
              }
              break;
            }
            in += (char)inp;
            inp = reader.read();
          }
          continue;
        }
        name += (char)inp;
        inp = reader.read();
      }


      reader.close();
    } catch(IOException ex){
      System.out.println(ex.getMessage());
    }
  }
  void rInit(int maxDuration, int maxDelay, int num){
    int t = 0,
        buff;
    Random random = new Random();
    Proc proc;
    queue.clear();
    queue1.clear();
    queue2.clear();
    queue3.clear();

    for (int i=0; i<num; i++){
      t += random.nextInt(maxDelay) + 1;
      buff = random.nextInt(maxDuration) + 1;
      proc = new Proc("P"+i, t, buff);
      queue.add(proc);
      //
      System.out.println("Name:"+proc.getName()+" In:"+proc.getIn()+" duration :"+proc.getDuration());
      //
    }
    System.out.println("rInit-finished");
  }
  public void plan(String fileName){
    fInit(fileName);
    System.out.println("Planner-finished");
  }
  public void plan(int maxDuration, int maxDelay, int num){
    rInit(maxDuration, maxDelay, num);
    System.out.println("Planner-finished");
  }
}
class Plan{
  static int[] strsToInts(String args[]){
    int nums[] = new int[3];
    nums[0] = Integer.parseInt(args[0]);
    nums[1] = Integer.parseInt(args[1]);
    nums[2] = Integer.parseInt(args[2]);
    return nums;
  }
  public static void main(String args[]){
    Planner planner = new Planner();
    if (args.length == 0) {
      planner.plan(200,100,1000);
    } else if (args.length == 1) {
      planner.plan(args[0] + ".queue");
    } else if (args.length == 3) {
      try {
        int nums[] = strsToInts(args);
        planner.plan(nums[0],nums[1],nums[2]);
      } catch(NumberFormatException ex){
        System.out.println("wrong input: numbers are required");
      }
    } else {
      System.out.println("wrong input: required either name of .queue or 3 ints");
    }
    System.out.println("Run-finished");
  }
}
