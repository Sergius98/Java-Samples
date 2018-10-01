import java.util.Date;

class FileRecord{
  String name;          //Ім’я файлу max 8
  String extension;     //розширення max 3
  //String atrb;          //Атрибути файлу read-only, hidden, System, Volume, Directory, Archive
  //String reserveField;//Резервне поле
  //int crDate;           //Date створення in miliseconds
  int createdTime;           //Час створення in miliseconds
  int lastTime;         //Дата останнього доступу in days from 2018
  //String reserve;     //Зарезервовано
  int modifiedTime;          //Час останньої модифікації
  //int modDate;          //Дата останньої модифікації
  int start;            //Номер початкового кластера FAT
  int size;             //Розмір файла in clasters

  FileRecord(String _name, String _extension, int _size, int _start){
    Date date = new Date();
    createdTime = date.getTime();
    lastTime = date.getTime();
    modifiedTime = date.getTime();
    System.out.printf("%2$tY %2$tT", date);
    name = _name;
    extension = _extension;
    size = _size;
    start = _start;
  }
}
