import java.util.Objects;
import java.util.Scanner;
class Run {
  public static void main(String args[]){
    int size = 0, defects = 0;
    if (args.length != 2){
      System.out.println("wrong arguments, try 'java Run 10 2'");
      return;
    }
    try {
      size = Integer.parseInt(args[0]);// number of clasters
      defects = Integer.parseInt(args[1]);// number of defects
    } catch (Exception e){
      System.out.println("wrong arguments, try 'java Run 10 2'\nError:"+e);
      return;
    }
    if (size <= 1){
      System.out.println("too little clasters, try 'java Run 10 2'");
      return;
    }
    if (size >= 65535){
      System.out.println("too many clasters, try 'java Run 10 2'");
      return;
    }
    if (size <= defects){
      System.out.println("too many defects, try 'java Run 10 2'");
      return;
    }
    Memmory memo = new Memmory(size, defects);
    String command = "", arg = "";
    Scanner scan = new Scanner(System.in);
    while (!Objects.equals(command,"quit")){
      //memo.print();
      System.out.println("print 'quit' to quit");
      System.out.println("print 'new' to create file");
      System.out.println("print 'del' to deleate file");
      System.out.println("print 'inc' to increase file size");
      System.out.println("print 'get' to search file in memmory");
      System.out.println("print 'print' to print state of memmory");
      System.out.print("command: ");
      command = scan.nextLine();
      switch (command) {
        case "new":
          System.out.print("size: ");
          arg = scan.nextLine();
          memo.newFile(Integer.parseInt(arg), scan);//ask for name in new
          break;
        case "del":
          System.out.print("id: ");
          arg = scan.nextLine();
          memo.del(Integer.parseInt(arg));
          break;
        case "inc":
          System.out.print("id: ");
          arg = scan.nextLine();
          memo.inc(Integer.parseInt(arg), scan);//ask for number of clasters in inc
          break;
        case "get":
          System.out.print("id: ");
          arg = scan.nextLine();
          memo.get(Integer.parseInt(arg));
          break;
        case "print":
          memo.print();
          break;
      }

    }
  }
}
