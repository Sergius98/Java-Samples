class Proc{
  String name;
  Branch branch;
  int runtime,
      quant,
      deadtime = 0;
  boolean free = true;
  Proc(String nm){
    name = nm;
  }
}
