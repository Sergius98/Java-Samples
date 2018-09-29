class PostBox{
  private Message empty = new Message("empty", 1),
                  message = empty;
  public synchronized Message get() {
    while (message.request == false) {
      System.out.println("PostBox.get(): wait 'get' message");
      try{
        wait();
      }catch(Exception e){
        System.out.println(e);
      }
    }
    Message answ = message;
    message = empty;
    notifyAll();
    return answ;
  }
  public synchronized Message getResp(Message curr) {
    while ((message.response == false)||(message != curr)) {
      System.out.println("PostBox.getResp(): wait 'resp' message");
      try{
        wait();
      }catch(Exception e){
        System.out.println(e);
      }
    }
    Message answ = message;
    message = empty;
    notifyAll();
    return answ;
  }
  public synchronized void send(Message msg) {
    while (message != empty) {
      System.out.println("PostBox.add(): wait release of message");
      try{
        wait();
      }catch(Exception e){
        System.out.println(e);
      }
    }
    message = msg;
    notifyAll();
  }
}
