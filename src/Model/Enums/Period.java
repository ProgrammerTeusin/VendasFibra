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
  
  public static Period fromString(String value) {
        for (Period period : Period.values()) {
            if (period.toString().equals(value)) {
                return period;
            }
        }
        return null;
    }
}
