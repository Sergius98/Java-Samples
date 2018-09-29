class Account{
  private int account;
  public synchronized int get() {
    return account;
  }
  public synchronized boolean reduce(int sum) {
    if (account - sum < 0){
      return false;
    } else {
      account -= sum;
      return true;
    }
  }
  public synchronized boolean isEnough(int sum) {
    if (account - sum < 0){
      return false;
    } else {
      return true;
    }
  }
  Account(int num){
    account = num;
  }
}
