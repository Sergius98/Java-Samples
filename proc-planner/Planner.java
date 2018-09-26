import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

class Planner {
  int runtime = 0,
      time = 0,
      quant = 0;
  Proc curr,
       nil = new Proc("nil", 0, 0);
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
                  System.out.println("Name:"+proc.getName()+" In:"+proc.getIn()+" duration :"+proc.getDuration());
                  //
                  name = "";
                  in = "";
                  duration = "";

                  inp = reader.read();
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
      System.out.println("Name:"+proc.getName()+" In:"+proc.getIn()+" duration :"+proc.getDuration());
      //
    }
    //System.out.println("Planner.rInit-finished");
  }
  void load(Proc proc, int _quant, boolean state, ArrayList<Proc> next){
    curr = proc;
    quant = _quant;
    interruptible = state;
    interrupted = next;
    runtime = 0;
  }
  int getMin(int a1, int a2){
    if (a1 >= a2){
      return a2;
    }
    return a1;
  }
  void save(){
    //System.out.println("Planner.save-called");
    if (curr != nil){
      int rest = curr.getLeft() - runtime;
      if (rest == 0) {
        curr.setDelay(time);
        finished.add(curr);
      } else {
        curr.dec(runtime);
        interrupted.add(curr);
      }
      curr = nil;
    }
    quant = 0;
    runtime = 0;
    //System.out.println("Planner.save-finished");
  }

  Proc getBest(ArrayList<Proc> line){
    int shortest = 0;
    for (int i = 1; i < line.size(); i++){
      if (line.get(i).getLeft() < line.get(shortest).getLeft()){
        shortest = i;
      }
    }
    return line.remove(shortest);
  }

  void reload(){
    //System.out.println("Planner.reload-called");
    Proc newP;
    if (!queue1.isEmpty()){
      newP = queue1.remove(0);
      load(newP, getMin(50, newP.getLeft()), false, queue2);
    } else if (!queue2.isEmpty()){
      newP = queue2.remove(0);
      load(newP, getMin(100, newP.getLeft()), false, queue3);
    } else if (!queue3.isEmpty()){
      newP = getBest(queue3);
      load(newP, newP.getLeft(), true, queue3);
    } else if (curr != nil){
      curr = nil;
      quant = 0;
      runtime = 0;
    }
    //System.out.println("Planner.reload-finished");
  }
  void execute(){
    //System.out.println("Planner.execute-called");
    System.out.println("start time = " + time);
    System.out.println("name = " + curr.getName());
    System.out.println("runtime = " + runtime);
    System.out.println("quant = " + quant);
    System.out.println("interruptible = " + interruptible);
    System.out.println(" ");
    time++;
    if (curr == nil){
      reload();
    }
    runtime++;
    if (runtime >= quant){
      save();
      reload();
    }
    System.out.println("finish time = " + time);
    System.out.println("name = " + curr.getName());
    System.out.println("runtime = " + runtime);
    System.out.println("quant = " + quant);
    System.out.println("interruptible = " + interruptible);
    System.out.println(" ");

    //System.out.println("Planner.execute-finished");
  }
  boolean isEmpty(){
    return queue.isEmpty() && queue1.isEmpty() && queue2.isEmpty() && queue3.isEmpty();
  }
  void root(){
    //System.out.println("Planner.root-called");
    Proc proc;
    time = 0;
    quant = 0;
    runtime = 0;
    curr = nil;
    while ((!isEmpty())||(curr != nil)){
      if (!queue.isEmpty()){
        proc = queue.remove(0);
        int inTime = proc.getIn();
        while (inTime > time){
          execute();
        }
        /*System.out.println("time = " + time);
        System.out.println("name = " + curr.getName());
        System.out.println("runtime = " + runtime);
        System.out.println("quant = " + quant);
        System.out.println("interruptible = " + interruptible);
        System.out.println("curr.getLeft() - runtime = " + (curr.getLeft() - runtime));
        System.out.println("proc.getLeft() = " + proc.getLeft());
        System.out.println(" ");*/
        if (curr == nil){
          load(proc, getMin(50,proc.getLeft()), false, queue2);
        } else if (interruptible && (curr.getLeft() - runtime > proc.getLeft())){
          save();
          load(proc, getMin(50,proc.getLeft()), false, queue2);
        } else {
          queue1.add(proc);
        }
      } else {
        while(curr != nil){
          execute();
        }
      }
    }
    //System.out.println("Planner.root-finished");
  }
  void printTable(){
    Proc prc;
    int i = 0,
        delay = 0,
        full = 0;
    System.out.println("\nname| in  | fin |delay|full |duration");
    while(i<finished.size()){
      prc = finished.get(i);
      delay += prc.getDelay();
      full += prc.getFull();
      System.out.format(prc.getName() + "  |%05d|%05d|%05d|%05d|%05d%n",
        prc.getIn(), prc.getFinal(), prc.getDelay(), prc.getFull(), prc.getDuration());
      i++;
    }
    System.out.println("\n average delay:"+ (delay/(i+1)));
    System.out.println("\n average full:"+ (full/(i+1)));
  }
  public void plan(String fileName){
    //System.out.println("Planner.plan-called");
    fInit(fileName);
    root();
    printTable();
    //System.out.println("Planner.plan-finished");
  }
  public void plan(int maxDuration, int maxDelay, int num){
    //System.out.println("Planner.plan-called");
    rInit(maxDuration, maxDelay, num);
    root();
    printTable();
    //System.out.println("Planner.plan-finished");
  }
}
