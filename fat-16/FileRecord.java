import java.util.Date;

class FileRecord{
  String name;          //Ім’я файлу max 8
  String extension;     //розширення max 3
  String atrb;          //Атрибути файлу
  //String reserveField;//Резервне поле
  //int crDate;           //Date створення in miliseconds
  int createdTime;           //Час створення in miliseconds
  int lastTime;         //Дата останнього доступу in days from 2018
  //String reserve;     //Зарезервовано
  int modifiedTime;          //Час останньої модифікації
  //int modDate;          //Дата останньої модифікації
  int start;            //Номер початкового кластера FAT
  int size;             //Розмір файла in clasters
  FileRecord(String name, String ){
    Date date = new Date();
    crTime = date.getTime();
    System.out.printf("%2$tY %2$tT", date);
  }
}
