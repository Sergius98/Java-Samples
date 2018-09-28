import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;

class SchedulerM1 {
  ArrayList<Branch> line = new ArrayList<Branch>();
  ArrayList<Integer> order = new ArrayList<Integer>();
  Proc proc1 = new Proc("P1"),
       proc2 = new Proc("P2"),
       proc3 = new Proc("P3");
  Vars status = new Vars();
  boolean isFree(){
    if (proc1.free && proc2.free && proc3.free){
      return true;
    }
    return false;
  }
  boolean isBusy(){
    if (proc1.free || proc2.free || proc3.free){
      return false;
    }
    return true;
  }
  Proc getFree(){
    if (proc1.free){
      return proc1;
    }
    if (proc2.free){
      return proc2;
    }
    return proc3;
  }
  void executeProc(Proc proc){
    if (proc.free){
      proc.deadtime++;
      return;
    }
    proc.runtime++;
    if (proc.runtime == proc.quant) {
      proc.free = true;
      setStatus(proc.branch, false);
    }
    System.out.println("-- "+proc.name + "(" + proc.runtime + "/" + proc.quant +")");
  }

  void execute(){
    executeProc(proc1);
    executeProc(proc2);
    executeProc(proc3);
  }
  void setStatus(Branch br, Boolean stat){
    status.lockedA[br.ai][br.aj] = stat;
    status.lockedB[br.bi][br.bj] = stat;
    status.lockedC[br.ci][br.cj] = stat;
  }
  void auto(){
    Branch curr;
    Proc proc;
    while(true){
      //System.out.println("while(true)");
      while(!line.isEmpty()) {
        //System.out.println("while(!line.isEmpty())");
        while(isBusy()){
          //System.out.println("isBusy()");
          execute();
        }
        int l = 0;
        while (l< line.size()){
          //System.out.println("while (l< line.size())");
          //System.out.println(l + "<" + line.size());
          curr = line.remove(0);
          if ((!status.lockedA[curr.ai][curr.aj])&&
          (!status.lockedB[curr.bi][curr.bj])&&
          (!status.lockedC[curr.ci][curr.cj])){
            //System.out.println("if (!locked)");
            proc = getFree();
            proc.branch = curr;
            proc.runtime = 0;
            proc.free = false;
            setStatus(curr, true);
            if(status.emptyC[curr.ci][curr.cj]){
              //System.out.println("status.emptyC[curr.ci][curr.cj]");
              proc.quant = 4;
              status.emptyC[curr.ci][curr.cj] = false;
            }else{
              proc.quant = 5;
            }
            System.out.println("Z(A[" + curr.ai + "][" + curr.aj +
             "]; B[" + curr.bi + "][" + curr.bj +
             "]; C[" + curr.ci + "][" + curr.cj + "]) is loaded to " + proc.name);
            break;
          }
          l++;
          line.add(curr);
        }
        if(l >= line.size()){
          //System.out.println("if(l+1 == line.size())");
          execute();
        }
      }
      if(isFree()){
        //System.out.println("if(isFree()){");
        break;
      }
      execute();
    }
  }
  void handy(){
    Branch curr;
    Proc proc;
    while(true){
      while(!order.isEmpty()) {
        while(isBusy()){
          execute();
        }
        curr = line.get(order.remove(0));
        proc = getFree();
        proc.branch = curr;
        proc.runtime = 0;
        proc.free = false;
        if(status.emptyC[curr.ci][curr.cj]){
          proc.quant = 4;
          status.emptyC[curr.ci][curr.cj] = false;
        }else{
          proc.quant = 5;
        }
        System.out.println("Z(A[" + curr.ai + "][" + curr.aj +
         "]; B[" + curr.bi + "][" + curr.bj +
         "]; C[" + curr.ci + "][" + curr.cj + "]) is loaded to " + proc.name);
      }
      if(isFree()){
        break;
      }
      execute();
    }
  }

  void lineInit(int im, int jm, int km){
    Branch branch;
    for (int i = 0; i<im; i++){
      for (int j = 0; j<jm; j++){
        for (int k = 0; k <km; k++){
          branch = new Branch(i,k,k,j,i,j);
          line.add(branch);
        }
      }
    }
  }
  void handyInit(String fileName){
    try (FileReader reader = new FileReader(fileName)){
      line = new ArrayList<Branch>();
      proc1 = new Proc("P1");
      proc2 = new Proc("P2");
      proc3 = new Proc("P3");
      status = new Vars();

      String buff="";
      int inp = reader.read();
      while(inp != -1){
        if (inp != '\n'){
          buff += (char)inp;
        } else {
          order.add(Integer.parseInt(buff));
          buff = "";
        }
        inp = reader.read();
      }
      //System.out.println(order.get(0));
      reader.close();
    } catch(IOException ex){
      System.out.println(ex.getMessage());
    }
  }
  void run(){
    lineInit(3,3,3);
    auto();
    handyInit("ex1");
    lineInit(3,3,3);
    handy();
  }
}
