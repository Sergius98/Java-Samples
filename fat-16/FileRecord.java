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

  FileRecord(String _name, String _extension, int _size, int _start){
    Date date = new Date();
    SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    createdTime = date;
    lastTime = date;
    modifiedTime = date;
    System.out.println(form.format(date));
    name = _name;
    extension = _extension;
    size = _size;
    start = _start;
  }
}
