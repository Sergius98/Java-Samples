import java.util.Date;
import java.text.SimpleDateFormat;

class FileRecord{
  String name;          //Ім’я файлу max 8
  String extension;     //розширення max 3
  //String atrb;          //Атрибути файлу read-only, hidden, System, Volume, Directory, Archive
  //String reserveField;//Резервне поле
  //int crDate;           //Date створення in miliseconds
  Date createdTime;           //Час створення in miliseconds
  Date lastTime;         //Дата останнього доступу in days from 2018
  //String reserve;     //Зарезервовано
  Date modifiedTime;          //Час останньої модифікації
  //int modDate;          //Дата останньої модифікації
  int start;            //Номер початкового кластера FAT
  int size;             //Розмір файла in clasters

  FileRecord(int id, String _name, String _extension, int _size, int _start){
    Date date = new Date();
    createdTime = date;
    lastTime = date;
    modifiedTime = date;
    name = _name;
    extension = _extension;
    size = _size;
    start = _start;
    print(id);
  }
  static void printHeader(){
    System.out.printf("%12s|%8s|%10s|%8s|%10s|%8s|%10s|%n", "name", "cr_time", "creat_date", "lastTime", "Last_date", "mod_time", "mod_date");
  }
  void print(int id){
    SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");
    SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
    System.out.printf("%5d|%8s.%3s|", id, name, extension);
    System.out.printf("%8s|%10s|", time.format(createdTime), date.format(createdTime));
    System.out.printf("%8s|%10s|", time.format(lastTime), date.format(lastTime));
    System.out.printf("%8s|%10s|", time.format(modifiedTime), date.format(modifiedTime));
    System.out.printf("%5d|%5d|%n", start, size);
  }
}
