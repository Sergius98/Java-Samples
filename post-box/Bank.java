// recieve request from ATM
// if "curr_money - sum > 0" send "true" back
// if ATM sent confirmation, reduce "sum" from user's account
import java.util.ArrayList;
import java.util.Random;

class Bank extends Thread{
  ArrayList<Account> clients = new ArrayList<Account>();
  String name;
  PostBox post;

  void newClient(int amount){
    Account client = new Account(amount);
    clients.add(client);
  }
  public void setMyName(String _name){
    name = _name;
  }
  public void createClients(int num, int _money){
    Random r = new Random();
    int money;

    for (int i = 0; i< num; i++){
      money = r.nextInt(_money);
      newClient(money);
    }
  }
  public void setPostBox(PostBox _post){
    post = _post;
  }

  void printReq(Message msg){
    System.out.println(name + "-<-" + msg.name + "---------recieve------------");
    if (msg.reduce){
      System.out.println(name + "-<-" + msg.name + "--reduce");
    } else {
      System.out.println(name + "-<-" + msg.name + "--check");
    }
    System.out.println(name + "-<-" + msg.name + "--client = "+msg.client);
    System.out.println(name + "-<-" + msg.name + "--sum = "+msg.sum);
    System.out.println(name + "-<-" + msg.name + "--balance = "+clients.get(msg.client).get());
  }

  public void run(){
    System.out.println(name +"(Bank) is running...");
    Random r = new Random();
    Message msg;
    boolean answer = false;
    while (true) {
      msg = post.get();
      printReq(msg);
      if (msg.reduce){
        answer = clients.get(msg.client).reduce(msg.sum);
      } else {
        answer = clients.get(msg.client).isEnough(msg.sum);
      }
      if (answer){
        System.out.println(name + "-<-" + msg.name + " success");
        msg.success = true;
      } else {
        System.out.println(name + "-<-" + msg.name + " failed");
        msg.success = false;
      }
      msg.response = true;
      msg.request = false;
      post.send(msg);
    }
  }
}
