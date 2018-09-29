import java.util.ArrayList;
import java.util.Random;

class ATM extends Thread{
  ArrayList<Cash> banknotes = new ArrayList<Cash>();
  int money = 0,
      clients = 1,
      limit = 10;
  String name;
  PostBox post;

  void newCash(int rate, int amount){
    Cash banknote = new Cash(rate, amount);
    money += rate*amount;
    banknotes.add(banknote);
  }
  public void setMyLimit(int lm){
    limit = lm;
  }
  public void setMyName(String _name){
    name = _name;
  }

  public void setClients(int num){
    clients = num;
  }

  public void setPostBox(PostBox _post){
    post = _post;
  }

  void printCash(){
    System.out.println(name + "----------->cash<------------");
    for (int i=0; i<banknotes.size();i++){
      System.out.println(name + "-<cash>--rate:" + banknotes.get(i).rate + ";--quantity:" + banknotes.get(i).amount);
    }
  }

  void printReq(Message msg){
    System.out.println(name + "->-" + msg.name + "---------sent------------");
    if (msg.reduce){
      System.out.println(name + "->-" + msg.name + "--reduce");
    } else {
      System.out.println(name + "->-" + msg.name + "--check");
    }
    System.out.println(name + "->-" + msg.name + "--client = "+msg.client);
    System.out.println(name + "->-" + msg.name + "--sum = "+msg.sum);
  }
  boolean isEnough(Message msg){
    int sum = msg.sum;
    for (int i = 0; i<banknotes.size();i++){
      int quantity = 0;
      while ((sum >= banknotes.get(i).rate)&&(quantity<banknotes.get(i).amount)){
        sum -= banknotes.get(i).rate;
        quantity++;
      }
      if (sum == 0){
        return true;
      }
    }
    return false;
  }
  boolean reduce(Message msg){
    int sum = msg.sum;
    money -= msg.sum;
    for (int i = 0; i<banknotes.size();i++){
      while ((sum >= banknotes.get(i).rate)&&(banknotes.get(i).amount>0)){
        sum -= banknotes.get(i).rate;
        banknotes.get(i).amount--;
      }
      if (sum == 0){
        return true;
      }
    }
    return false;
  }
  public void run(){
    int[] rates = new int[] {100, 50, 20, 10, 5, 2, 1};
    int len = rates.length,
        amount = 25;
    for (int i = 0; i<len; i++){
      newCash(rates[i], amount);
      amount *= 2;
    }
    System.out.println(name + "(ATM) is running...");
    printCash();
    Random r = new Random();
    int max_sum = r.nextInt(money) + 1;
    int curr = 1;
    Message msg;
    while (true) {
      if (money <= 0){
        break;
      } else if (curr >= limit){
        break;
      } else {
        msg = new Message(name, curr);
        msg.client = r.nextInt(clients);
        msg.request = true;
        msg.sum = r.nextInt(max_sum) + 1;
        if (msg.sum > money){
          max_sum = (int)(max_sum / 2);
        }
        printReq(msg);
        post.send(msg);
        msg = post.getResp(msg);
        if (msg.success){
          printCash();
          if (isEnough(msg)){
            System.out.println(name + "->-" + msg.name + "--there is enough cash");
            msg.request = true;
            msg.response = false;
            msg.reduce = true;
            printReq(msg);
            post.send(msg);
            msg = post.getResp(msg);
            if (msg.success){
              reduce(msg);
              System.out.println(name + "->-" + msg.name + " cash has been withdrawn");
              printCash();
            } else {
              System.out.println(name + "->-" + msg.name + " failed");
              max_sum = (int)(max_sum / 2);
            }
          } else {
            System.out.println(name + "->-" + msg.name + "--there isn't enough cash");
          }
        }
        //crit section
        curr++;
      }
    }
    printCash();
    System.out.println(name + "(ATM) is stopped");
  }
}
