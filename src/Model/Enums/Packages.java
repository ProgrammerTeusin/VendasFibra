package Model.Enums;

public enum Packages {
    SELECT("Selecione"),
    I_400MB("400MB"),
    I_500MB("500MB"),
    I_600MB("600MB"),
    I_700MB("700MB"),
    I_1GB("1GB"),
    ALL("Todos");   

    private String pac;
    
    private Packages(String pac) {
        this.pac = pac;
    }

   
    @Override
    public String toString() {
        return pac;
    }
    
     public static Packages fromString(String value) {
        for (Packages pack : Packages.values()) {
            if (pack.toString().equals(value)) {
                return pack;
            }
        }
        return null;
    }
    
    
    
}
