package Model.Enums;

public enum Situation {
    SELECT("Selecione"),
    PROVISIONING("Aprovisionamento"),
    INSTALLED("Instalada"),
    CANCELED("Cancelada");

    String value;

    private Situation(String val) {
        value = val;
    }

    @Override
    public String toString() {
        return value;
    }

    public static Situation fromString(String value) {
        for (Situation situation : Situation.values()) {
            if (situation.toString().equals(value)) {
                return situation;
            }
        }
        return null;
    }
}
