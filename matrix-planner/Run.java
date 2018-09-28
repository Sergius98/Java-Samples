import java.util.Objects;
class Run {
  public static void main(String args[]){
    if (args.length == 0){
      System.out.println("name wasn't found");
    } else if (Objects.equals(args[0],"M1")){
      SchedulerM1 planner = new SchedulerM1();
      planner.run();
    } else if (Objects.equals(args[0],"M2")){
      SchedulerM2 planner = new SchedulerM2();
      planner.run();
    } else {
      System.out.println("wrong name");
    }
  }
}
