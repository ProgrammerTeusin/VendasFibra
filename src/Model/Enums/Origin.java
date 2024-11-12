package Model.Enums;

public enum Origin {
      SELECT("Selecione"),
    BASE("Base"),
    BASE_SIP("Base URA"),
    CHAT("Chat"),
    INDICATION("Indicação"),;
    
    private String type;
    
    
    private Origin(String type) {  
    this.type = type;    
    }

    @Override
    public String toString() {
        return type;
    }
    
        public static Origin fromString(String value) {
           for (Origin origin : Origin.values()) {
               if (origin.toString().equals(value)) {
                   return origin;
               }
           }
           return null;
       }
    
}
