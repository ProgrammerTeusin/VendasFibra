
package Controller;

import Model.Enums.TypeCalculation;
import java.math.BigDecimal;

public class GoalsDefineds {
    String packService;
    BigDecimal value;
    TypeCalculation typeCalcule;
    int qdtToGoal;

    public GoalsDefineds() {
    }

    public GoalsDefineds(String packService, BigDecimal value, TypeCalculation typeCalcule, int qdtToGoal) {
        this.packService = packService;
        this.value = value;
        this.typeCalcule = typeCalcule;
        this.qdtToGoal = qdtToGoal;
    }

    public GoalsDefineds(BigDecimal value, TypeCalculation typeCalcule, int qdtToGoal) {
        this.value = value;
        this.typeCalcule = typeCalcule;
        this.qdtToGoal = qdtToGoal;
    }

    public String getPackService() {
        return packService;
    }

    public void setPackService(String packService) {
        this.packService = packService;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public TypeCalculation getTypeCalcule() {
        return typeCalcule;
    }

    public void setTypeCalcule(TypeCalculation typeCalcule) {
        this.typeCalcule = typeCalcule;
    }

    public int getQdtToGoal() {
        return qdtToGoal;
    }

    public void setQdtToGoal(int qdtToGoal) {
        this.qdtToGoal = qdtToGoal;
    }

    @Override
    public String toString() {
        return "GoalsDefineds{" + "packService=" + packService + ", value=" + value + ", typeCalcule=" + typeCalcule + ", qdtToGoal=" + qdtToGoal + '}';
    }
    
    
}
