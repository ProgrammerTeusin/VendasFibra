package Model.Enums;

public enum PartnerShip {
    SELECT("Selecione"),
    OI("Oi Fibra"),
    SKY("SKY");

    String value;

    private PartnerShip(String val) {
        value = val;
    }

    @Override
    public String toString() {
        return value;
    }

    public static PartnerShip fromString(String value) {
        for (PartnerShip partnership : PartnerShip.values()) {
            if (partnership.toString().equals(value)) {
                return partnership;
            }
        }
        return null;
    }
}
