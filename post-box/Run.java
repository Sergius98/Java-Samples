class Run{
  public static void main(String args[]){
    ATM atm = new ATM();
    Bank bank = new Bank();
    int clients = 5;
    int money = 48000;
    PostBox post = new PostBox();
    bank.setDaemon(true);
    atm.setClients(clients);
    bank.createClients(clients, money);
    atm.setMyName("ATM");
    bank.setMyName("BANK");
    atm.setPostBox(post);
    bank.setPostBox(post);
    atm.setMyLimit(30);
    atm.start();
    bank.start();
  }
}
