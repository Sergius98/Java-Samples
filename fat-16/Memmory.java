import java.util.Date;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Memmory{
  //  first two clasters(id(0)=-3 and id(1)=65535) for system
  //  max number of files - 512;
  //  max length of file name - 8 . 3
  //
  ArrayList <FileRecord> files = new ArrayList<FileRecord>();
  ArrayList <Claster> clasters = new ArrayList<Claster>();
  int free;
  Memmory(int size, int defects){
    free = size - 2;
    for (int i = 0; i < size; i++){
      Claster clas = new Claster();
      clasters.add(clas);
    }
    clasters.get(0).id = -30;// => 1d => id
    clasters.get(1).id = 65535;
    Random r = new Random();
    int j = 0;
    while (j < defects){
      int i = r.nextInt(size);
      if (clasters.get(i).id == 0){
        j++;
        free--;
        clasters.get(i).id = r.nextInt(size - 1) + 1;
      }
    }

  }
  int giveMemmory(int size){
    int prev = 65535;
    int k = 0;
    for (int i = clasters.size() - 1; i >= 0; i--){
      if (k == size){
        return prev;
      }
      if (clasters.get(i).id == 0){
        clasters.get(i).id = prev;
        prev = i;
        k++;
        free--;
      }
    }
    return prev;
  }
  void newFile(int size, Scanner scan){
    //ask for name
    if (free == 0){
      System.out.println("not enough memmory");
      return;
    }
    if (size > free){
      System.out.println("not enough memmory");
      return;
    }
    if (size <= 0){
      System.out.println("size is too small");
      return;
    }
    String name = "", extension = "";
    System.out.print("name:");
    name = scan.nextLine();
    if (name.length() > 8){
      name = name.substring(0,8);
    }
    System.out.print("extension:");
    extension = scan.nextLine();
    if (name.length() > 3){
      name = name.substring(0,3);
    }
    FileRecord file = new FileRecord(name, extension, size, giveMemmory(size));
    files.add(file);
  }
  void takeMemmory(int id){
      int next = id;
      Claster buff;
      while (next != 65535){
        free++;
        buff = clasters.get(next);
        next = buff.id;
        buff.id = 0;
      }
  }
  void del(int id){
    if (id < 0){
      System.out.println("id is too small");
      return;
    }
    if (id >= files.size()){
      System.out.println("id is too big");
      return;
    }
    FileRecord pop = files.remove(id);
    takeMemmory(pop.start);
  }
  Claster getLast(int start){
    Claster buff = clasters.get(start);
    while (buff.id != 65535){
      buff = clasters.get(buff.id);
    }
    return buff;
  }
  void inc(int id, Scanner scan){
    if (free == 0){
      System.out.println("not enough memmory");
      return;
    }
    int size;
    if (id <= 0){
      System.out.println("id is too small");
      return;
    }
    if (id >= files.size()){
      System.out.println("id is too big");
      return;
    }
    System.out.print("size(+): ");
    size = Integer.parseInt(scan.nextLine());
    if (size > free){
      System.out.println("not enough memmory");
      return;
    }
    if (size <= 0){
      System.out.println("memmory is too small");
      return;
    }
    FileRecord buff = files.get(id);
    getLast(buff.start).id = giveMemmory(size);
    buff.size += size;
  }
  void get(int id){

  }
  void print(){
    int max = clasters.size()/5;
    for (int i = 0; i < max; i++){
      System.out.printf("{%5d}=[%05d]|", (i*5), clasters.get((i*5)).id);
      System.out.printf("{%5d}=[%05d]|", 1+(i*5), clasters.get(1+(i*5)).id);
      System.out.printf("{%5d}=[%05d]|", 2+(i*5), clasters.get(2+(i*5)).id);
      System.out.printf("{%5d}=[%05d]|", 3+(i*5), clasters.get(3+(i*5)).id);
      System.out.printf("{%5d}=[%05d]|%n", 4+(i*5), clasters.get(4+(i*5)).id);
    }
    for (int i = max*5; i < clasters.size(); i++){
      System.out.printf("{%5d}=[%05d]|", i, clasters.get(i).id);
    }
    System.out.println();
  }
}
