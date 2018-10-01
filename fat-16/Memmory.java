import java.util.Date;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Memmory{
  //  first two clasters(id(0)=-3 and id(1)=65535) for system
  //  max number of files - 512;
  //  max length of file name - 8 . 3
  //
  ArrayList <FileRecord> files = new ArrayList<RamRecord>();
  ArrayList <Claster> mem = new ArrayList<CacheRecord>();
  int free;
  Memmory(int size, int defects){
    free = size - 2
    for (int i = 0; i < size; i++){
      Claster clas = new Claster();
      mem.add(clas);
    }
    mem.get(0).id = -30;// => 1d => id
    mem.get(1).id = 65535;
    Random r = new Random();
    int j = 0;
    while (j < defects){
      int i = r.nextInt(size);
      if (mem.get(i).id == 0){
        j++;
        free--;
        mem.get(i).id = r.nextInt(size - 1) + 1;
      }
    }

  }
  int giveMemmory(int size){
    boolean prev = 65535;
    int k = 0;
    for (int i = mem.size() - 1; i >= 0; i--){
      if (k == size){
        return prev;
      }
      if (clasters.get(i).id == 0){
        clasters.get(i).id = prev;
        prev = i;
        k++;
      }
    }
    if (k == size){
      return prev;
    }
    return -1;
  }
  void new(int size){
    //ask for name
    if (size < free){
      System.out.print("not enough memmory");
      return;
    }
    if (size <= 0){
      System.out.print("size is too small");
      return;
    }
    String name = "", extension = "";
    Scanner scan = new Scanner(System.in);
    System.out.print("name:");
    name = scan.nextLine();
    if (name.length > 8){
      name = name.substring(0,8);
    }
    System.out.print("extension:");
    extension = scan.nextLine();
    if (name.length > 3){
      name = name.substring(0,3);
    }
    FileRecord file = new FileRecord(name, extension, size, giveMemmory(size));
    files.add(file);
  }
  void takeMemmory(int id){
      int next = id;
      Claster buff;
      while (next != 65535){
        buff = mem.get(next);
        next = buff.id;
        buff.id = 0;
      }
  }
  void del(int id){
    if (id <= 0){
      System.out.print("id is too small");
      return;
    }
    if (id >= files.size()){
      System.out.print("id is too big");
      return;
    }
    FileRecord pop = files.remove(id);
    takeMemmory(pop.start);
  }
  void inc(int id){
    //ask for number of clasters
  }
  void get(int id){

  }
  void print(){

  }
}
