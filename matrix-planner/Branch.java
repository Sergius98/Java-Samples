import java.util.ArrayList;
class Branch {
  String name = "";
  int ai,aj,bi,bj,ci,cj;
  boolean isAdd = false;
  ArrayList<Branch> required = new ArrayList<Branch>();
  Branch(int aai, int aaj, int bbi, int bbj, int cci, int ccj){
    ai = aai;
    aj = aaj;
    bi = bbi;
    bj = bbj;
    ci = cci;
    cj = ccj;
    name = "M("+"A["+ai+"]["+aj+"],"+"B["+bi+"]["+bj+"],"+"C["+ci+"]["+cj+"]"+")";
  }
  Branch(){
    isAdd = true;
    name = "S(";
  }
  Branch(Branch req){
    isAdd = true;
    required.add(req);
    name = "S(" + req.name;
  }
  void push(Branch req){
    required.add(req);
    name += ", " + req.name;
  }
  boolean isReady(ArrayList<Branch> finished){
    for (int k = 0; k< required.size(); k++){
      int i = 0;
      while (i < finished.size()){
        if (required.get(k) == finished.get(i)){
          break;
        }
        i++;
      }
      if (i == finished.size()){
        return false;
      }
    }
    return true;
  }
}
