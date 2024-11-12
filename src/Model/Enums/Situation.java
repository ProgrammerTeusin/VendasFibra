package Model.Enums;

public enum Situation {
    SELECT("Selecione"),
    ALL("Todos"),
    PROVISIONING("Aprovisionamento"),
    INSTALLED("Instalada"),
    BLOCKED("Parcial/Bloqueada"),
    CANCELED("Cancelada"),
    DAY("Dia");

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
