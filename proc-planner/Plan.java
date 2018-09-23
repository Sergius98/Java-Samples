class Plan{
  static int[] strsToInts(String args[]){
    int nums[] = new int[3];
    nums[0] = Integer.parseInt(args[0]);
    nums[1] = Integer.parseInt(args[1]);
    nums[2] = Integer.parseInt(args[2]);
    return nums;
  }
  public static void main(String args[]){
  //System.out.println("Plan.main-started");
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
    //System.out.println("Plan.main-finished");
  }
}
