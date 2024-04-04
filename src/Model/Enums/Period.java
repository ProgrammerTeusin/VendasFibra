package Model.Enums;

public enum Period {
     SELECT("Selecione"),
    MORNING("Manhã"),
    AFTERNOON("Tarde");
    
    private String value;
    
    Period(String value){
       this.value = value;
    }
 public String toString(){
     return value;
 }    
  
}
