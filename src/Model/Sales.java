package Model;

import Model.Enums.Origin;
import Model.Enums.Period;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class Sales {
    
    private Vendedor seller;
    private LocalDateTime sellDateHour;
        private String customers; //cliente 
    private String contact;  
    private String cpf;  
    private String packages;
    private LocalDateTime installationMarked; //instalacao marcada
    private Period period;
    private Origin origin;
    private String observation;

    public Sales(Vendedor seller, LocalDateTime sellDateHour, String cpf, String customers, String contact, String packages, LocalDateTime installationMarked, Period period, Origin origin, String observation) {
        this.seller = seller;
        this.sellDateHour = sellDateHour;
        this.customers = customers;
        this.contact = contact;
        this.packages = packages;
        this.installationMarked = installationMarked;
        this.period = period;
        this.origin = origin;
        this.observation = observation;
        this.cpf = cpf;
        
    }

    public Vendedor getSeller() {
        return seller;
    }

    public void setSeller(Vendedor seller) {
        this.seller = seller;
    }

    public LocalDateTime getSellDateHour() {
        return sellDateHour;
    }

    public void setSellDateHour(LocalDateTime sellDateHour) {
        this.sellDateHour = sellDateHour;
    }

    public String getCustomers() {
        return customers;
    }

    public void setCustomers(String customers) {
        this.customers = customers;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public LocalDateTime getInstallationMarked() {
        return installationMarked;
    }

    public void setInstallationMarked(LocalDateTime installationMarked) {
        this.installationMarked = installationMarked;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    
    
    
    
}
