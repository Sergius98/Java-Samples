import java.util.Objects;
import java.util.Scanner;
class Run {
  public static void main(String args[]){
    int size = 0;
    if (args.length != 1){
      System.out.println("wrong arguments, try 'java Run 2'");
      return;
    }
    try {
      size = Integer.parseInt(args[0]);
    } catch (Exception e){
      System.out.println("wrong arguments, try 'java Run 2'\nError:"+e);
      return;
    }
    if (size <= 0){
      System.out.println("wrong arguments, try 'java Run 2'");
      return;
    }
    Memmory memo = new Memmory(size);
    String command = "", addr = "";
    Scanner scan = new Scanner(System.in);
    while (!Objects.equals(command,"quit")){
      //memo.print();
      System.out.println("print 'quit' to quit");
      System.out.println("print 'inc' to write memmory");
      System.out.println("print 'get' to access memmory");
      System.out.println("print 'print' to print memmory table");
      System.out.print("command: ");
      command = scan.nextLine();
      switch (command) {
        case "inc":
          System.out.print("addr: ");
          addr = scan.nextLine();
          memo.inc(Integer.parseInt(addr));
          break;
        case "get":
          System.out.print("addr: ");
          addr = scan.nextLine();
          memo.get(Integer.parseInt(addr));
          break;
        case "print":
          memo.print();
          break;
      }

    }
  }
}
