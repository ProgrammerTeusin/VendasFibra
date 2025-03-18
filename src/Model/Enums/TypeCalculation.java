package Model.Enums;

public enum TypeCalculation {
    EQUAL("Igual"), 
    DIFFERENT("Diferente"), 
    BIGGER("Maior"),
    BIGGER_EQUAL("Maior Igual"),
    LESSER("Menor"),
    LESSER_EQUAL("Menor Igual");
    
    String value;

    private TypeCalculation(String val) {
        value = val;
    }

    @Override
    public String toString() {
        return value;
    }

    public static TypeCalculation fromString(String value) {
        for (TypeCalculation typeCalculation : TypeCalculation.values()) {
            if (typeCalculation.toString().equals(value)) {
                return typeCalculation;
            }
        }
        return null;
    }

}
