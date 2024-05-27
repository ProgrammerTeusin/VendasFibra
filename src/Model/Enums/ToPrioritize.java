package Model.Enums;


public enum ToPrioritize {
    YES("SIM"),
    NO("NÃO");

    String value;

     private ToPrioritize(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
    
    public static ToPrioritize fromString(String value) {
        for (ToPrioritize priori : ToPrioritize.values()) {
            if (priori.toString().equals(value)) {
                return priori;
            }
        }
        return null;
    }

}
