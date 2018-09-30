import java.util.ArrayList;

class Memmory{
  ArrayList <RamRecord> ram = new ArrayList<RamRecord>();
  ArrayList <CacheRecord> cache = new ArrayList<CacheRecord>();
  Memmory(int cache_size){
    RamRecord buff;
    for (int i = 0; i < cache_size; i++){
      for (int j = 0; j < 10; j++){
        buff = new RamRecord();
        buff.obj = 0;
        buff.ceche_id = i;
        ram.add(buff);
      }
      cache.add(new CacheRecord());
    }
  }
  int getCacheAddr(int addr){
    return addr/10;
  }
  boolean isCorrect(int addr){
    CacheRecord buff = cache.get(getCacheAddr(addr));
    if (buff.valid){
      if (buff.addr == addr){
        return true;
      }
    }
    return false;
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
      return getCache(addr);
    }
    updateCache(addr);
    return ram.get(addr);
  }
  void incRam(int addr){
    ram.get(addr).obj++;
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
