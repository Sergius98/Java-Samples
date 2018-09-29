class PostBox{
  private Message empty = new Message("empty", 1);
  Message[] messages;
  int curr = 0;
  int max = 1;
  public synchronized Message get() {
    while (messages[curr].request == false) {
      for (int i=0; i< curr; i++){
        if (messages[i].request){
          Message buff = messages[i];
          messages[i] = messages[curr];
          messages[curr] = buff;
          break;
        }
      }
      if (messages[curr].request){
        break;
      }
      System.out.println("PostBox.get(): wait 'get' message");
      try{
        wait();
      }catch(Exception e){
        System.out.println(e);
      }
    }
    Message answ = messages[curr];
    messages[curr] = empty;
    if (curr > 0){
      curr--;
    }
    notify();
    return answ;
  }
  public synchronized Message getResp(Message msg_curr) {
    while ((messages[curr].response == false)||(messages[curr] != msg_curr)) {
      for (int i=0; i< curr; i++){
        if (messages[i].response && (messages[i] == msg_curr)){
          Message buff = messages[i];
          messages[i] = messages[curr];
          messages[curr] = buff;
          break;
        }
      }
      if (messages[curr].response && (messages[curr] == msg_curr)){
        break;
      }
      System.out.println("PostBox.getResp(): wait 'resp' message");
      try{
        wait();
      }catch(Exception e){
        System.out.println(e);
      }
    }
    Message answ = messages[curr];
    messages[curr] = empty;
    if (curr > 0){
      curr--;
    }
    notify();
    return answ;
  }
  public synchronized void send(Message msg) {
    while (messages[curr] != empty) {
      if ((curr + 1) < max){
        curr++;
        break;
      }
      System.out.println("PostBox.add(): wait release of message");
      try{
        wait();
      }catch(Exception e){
        System.out.println(e);
      }
    }
    messages[curr] = msg;
    if (curr < max - 1){
      curr++;
    }
    notify();
  }
  PostBox(int num){
    max = num;
    messages = new Message[num];
    for (int i=0; i<num; i++){
      messages[i] = empty;
    }
  }
}
