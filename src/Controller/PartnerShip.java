package Controller;

import Model.Enums.Situation;
import java.time.LocalDateTime;
import java.util.List;


public class PartnerShip {
    String partnership;
    int AmountMonthToReceive;
    Situation calculeSituation;
    List<GoalsDefineds> defineGoals;
    LocalDateTime dateTime;

    public PartnerShip() {
    }

    public PartnerShip(String partnership, int AmountMonthToReceive, Situation calculeSituation, List<GoalsDefineds> defineGoals, LocalDateTime dateTime) {
        this.partnership = partnership;
        this.AmountMonthToReceive = AmountMonthToReceive;
        this.calculeSituation = calculeSituation;
        this.defineGoals = defineGoals;
        this.dateTime = dateTime;
    }

    public String getPartnership() {
        return partnership;
    }

    public void setPartnership(String partnership) {
        this.partnership = partnership;
    }

    public int getAmountMonthToReceive() {
        return AmountMonthToReceive;
    }

    public void setAmountMonthToReceive(int AmountMonthToReceive) {
        this.AmountMonthToReceive = AmountMonthToReceive;
    }

    public Situation getCalculeSituation() {
        return calculeSituation;
    }

    public void setCalculeSituation(Situation calculeSituation) {
        this.calculeSituation = calculeSituation;
    }

    public List<GoalsDefineds> getDefineGoals() {
        return defineGoals;
    }


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
}
