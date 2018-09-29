class Run{
  public static void main(String args[]){
    ATM atm = new ATM();
    ATM atm2 = new ATM();
    Bank bank = new Bank();
    //Bank bank2 = new Bank();
    int clients = 5;
    int money = 48000;
    PostBox post = new PostBox(3);
    atm.setPostBox(post);
    atm.setMyName("ATM{1}");
    atm.setClients(clients);
    atm.setMyLimit(5);
    atm2.setPostBox(post);
    atm2.setMyName("ATM{2}");
    atm2.setClients(clients);
    atm2.setMyLimit(5);
    bank.setDaemon(true);
    bank.createClients(clients, money);
    bank.setMyName("BANK");
    bank.setPostBox(post);
    //bank2.setDaemon(true);
    //bank2.createClients(clients, money);
    //bank2.setMyName("BANK{2}");
    //bank2.setPostBox(post);
    atm2.start();
    atm.start();
    bank.start();
    //bank2.start();
  }
}
