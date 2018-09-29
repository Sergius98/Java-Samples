class Message{
  boolean request = false,
          response = false,
          reduce = false,
          success = false;
  int sum = 0, client = -1;
  String name;
  Message(String _name, int _num){
    name = _name + "[" + _num + "]";
  }
}
