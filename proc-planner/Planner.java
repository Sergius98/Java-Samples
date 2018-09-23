import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

class Planner {
  int now = 0,
      runtime = 0,
      time = 0,
      quant = 0;
  Proc curr;
  boolean interruptible = false,
          free = true;
  ArrayList<Proc> queue = new ArrayList<Proc>(),
                  queue1 = new ArrayList<Proc>(),
                  queue2 = new ArrayList<Proc>(),
                  queue3 = new ArrayList<Proc>(),
                  finished = new ArrayList<Proc>(),
                  interrupted;
  void clean(){
    queue.clear();
    queue1.clear();
    queue2.clear();
    queue3.clear();
    finished.clear();
  }
  void fInit(String fileName){
    //System.out.println("Planner.fInit-called");
    try (FileReader reader = new FileReader(fileName)){
      int t = 0;
      Proc proc;
      clean();

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
                  //System.out.println("Name:"+proc.getName()+" In:"+proc.getIn()+" duration :"+proc.getDuration());
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
    //System.out.println("Planner.fInit-finished");
  }
  void rInit(int maxDuration, int maxDelay, int num){
    //System.out.println("Planner.rInit-called");
    int t = 0,
        buff;
    Random random = new Random();
    Proc proc;
    clean();

    for (int i=0; i<num; i++){
      t += random.nextInt(maxDelay) + 1;
      buff = random.nextInt(maxDuration) + 1;
      proc = new Proc("P"+i, t, buff);
      queue.add(proc);
      //
      //System.out.println("Name:"+proc.getName()+" In:"+proc.getIn()+" duration :"+proc.getDuration());
      //
    }
    //System.out.println("Planner.rInit-finished");
  }
  public load(Proc proc, int _quant, boolean state,ArrayList<Proc> next){
    curr = proc;
    now = _quant;
    interruptible = state;
    interrupted = next;
    runtime = 0;
  }
  public finish(){
    int rest = curr.getLeft - runtime - 1;
    if (rest == 0) {
      curr.setDelay(time + 1);
      finished.add(curr);
    } else {
      curr.setLeft(runtime)
      interrupted.add(curr)
    }
  }
  public reload(Proc proc, int _quant, boolean state,ArrayList<Proc> next){
    finish();
    load(proc, _quant, state, next);
  }
  public void execute(){
    System.out.println("Planner.execute-called");
    if (now <= 0){
      if (queue1.lenght != 0){
        load(queue1.remove(0), 50, false, queue2);
      } else if (queue2.lenght != 0){
        load(queue2.remove(0), 100, false, queue3);
      } else if (queue3.lenght != 0){
        int left = queue3.get(0).getLeft();
        load(queue3.remove(0), left, true, queue3);
      } else if (curr.left() > runtime) {
        finish();
      }
    }else if (now == 1){
      if (queue1.lenght != 0){
        reload(queue1.remove(0), 50, false, queue2);
      } else if (queue2.lenght != 0){
        reload(queue2.remove(0), 100, false, queue3);
      } else if (queue3.lenght != 0){
        int left = queue3.get(0).getLeft();
        reload(queue3.remove(0), left, true, queue3);
      } else {
        finish();
      }
    } else {
      now--;
      runtime++;
    }
    System.out.println("Planner.execute-finished");
  }
  boolean isEmpty(){
    return queue.isEmpty() && queue1.isEmpty() && queue2.isEmpty() && queue3.isEmpty();
  }
  void root(){
    System.out.println("Planner.root-called");
    Proc proc;
    time = 0;
    now = 0;
    quant = 0;
    runtime = 0;
    curr = nil;
    while (!isEmpty()){
      if (!queue.isEmpty()){
        proc = queue.remove(0);
        while (proc.getIn() < time){
          execute();
        }
        if (curr == nil) {
          load(proc, 50, false, queue2);
        } else if (interruptible && (proc.getLeft - runtime > proc)){
          save();
          load(proc, 50, false, queue2);
        } else {
          queue2.add(proc);
        }
      } else {
        while(curr != nil){
          execute();
        }
      }
    }
    System.out.println("Planner.root-finished");
  }
  public void plan(String fileName){
    //System.out.println("Planner.plan-called");
    fInit(fileName);
    root();
    //System.out.println("Planner.plan-finished");
  }
  public void plan(int maxDuration, int maxDelay, int num){
    //System.out.println("Planner.plan-called");
    rInit(maxDuration, maxDelay, num);
    root();
    //System.out.println("Planner.plan-finished");
  }
}
