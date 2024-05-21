package Model.Enums;


public enum MonthsYear {
    JANUARY ("Janeiro"),
FEBRUARY ("Fevereiro"),
MARCH ("Março"),
APRIL ("Abril"),
MAY ("Maio"),
JUNE ("Junho"),
JULY ("Julho"),
AUGUST ("Agosto"),
SEPTEMBER ("Setembro"),
OCTOBER ("Outubro"),
NOVEMBER ("Novembro"),
DECEMBER ("Dezembro");

String month;
MonthsYear(String month){
    this.month = month;
}
@Override
    public String toString() {
        return month;
    }
    
     public static MonthsYear fromString(String value) {
        for (MonthsYear months : MonthsYear.values()) {
            if (months.toString().equals(value)) {
                return months;
            }
        }
        return null;
    }
    

}
