class Proc {
  private int in, left, duration, delay, end;
  private String name;
  Proc(String _name, int _in, int _duration){
    this.name = _name;
    this.in = _in;
    this.left = _duration;
    this.duration = _duration;
  }
  public void dec(int quant){
      left -= quant;
  }
  public void setDelay(int curr) {
    delay = curr - in - duration;
    end = curr;
  }
  public int getFinal(){
    return end;
  }
  public int getIn() {
    return in;
  }
  public int getLeft() {
    return left;
  }
  public int getDuration() {
    return duration;
  }
  public int getDelay() {
    return delay;
  }
  public int getFull(){
    return duration + delay;
  }
  public String getName(){
    return name;
  }
}
