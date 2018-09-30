import java.util.ArrayList;

class Memmory{
  ArrayList <RamRecord> ram = new ArrayList<RamRecord>();
  ArrayList <CacheRecord> cache = new ArrayList<CacheRecord>();

  public void print(){
    System.out.println("\nRAM");
    System.out.println("addr | obj | cache_id");
    for(int i=0; i<ram.size(); i++){
      System.out.format("%05d|%05d|%05d%n", i, ram.get(i).obj, ram.get(i).cache_id);
    }
    System.out.println("\ncache");
    System.out.println("addr |ram addr| obj | modified");
    String buff;
    for(int i=0; i<cache.size(); i++){
      if (cache.get(i).modified){
        buff = "true";
      } else {
        buff = "false";
      }
      System.out.format("%05d|%08d|%05d|"+buff+"%n", i, cache.get(i).addr, cache.get(i).obj);
    }
  }

  Memmory(int cache_size){
    RamRecord buff;
    for (int i = 0; i < cache_size; i++){
      for (int j = 0; j < 10; j++){
        buff = new RamRecord();
        buff.obj = 0;
        buff.cache_id = i;
        ram.add(buff);
      }
      cache.add(new CacheRecord());
    }
  }
  int getCacheAddr(int addr){
    return ram.get(addr).cache_id;
  }
  boolean isCorrect(int addr){
    CacheRecord buff = cache.get(getCacheAddr(addr));
    if (buff.addr == addr){
      return true;
    }
    return false;
  }
  CacheRecord getCache(int addr){
    return cache.get(getCacheAddr(addr));
  }
  void save(int obj, int addr){
    ram.get(addr).obj = obj;
  }
  void updateCache(int addr){
    CacheRecord buff = cache.get(getCacheAddr(addr));
    if (buff.modified){
      save(buff.obj, buff.addr);
      buff.modified = false;
    }
    buff.obj = ram.get(addr).obj;
    buff.addr = addr;
  }
  int get(int addr){
    if (addr < 0){
      System.out.println("expected addr >= 0");
      return -1;
    }
    if (addr >= ram.size()){
      System.out.println("cant find addr in RAM");
      return -1;
    }
    if (isCorrect(addr)){
      return getCache(addr).obj;
    }
    updateCache(addr);
    return ram.get(addr).obj;
  }
  void incRam(int addr){
    ram.get(addr).obj++;
  }
  void incCache(int addr){
    cache.get(getCacheAddr(addr)).modified = true;
    cache.get(getCacheAddr(addr)).obj++;
  }
  void inc(int addr){
    if (addr < 0){
      System.out.println("expected addr >= 0");
      return;
    }
    if (addr >= ram.size()){
      System.out.println("cant find addr in RAM");
      return;
    }
    if (isCorrect(addr)){
      incCache(addr);
      return;
    }
    incRam(addr);
    updateCache(addr);
    return;
  }
}
