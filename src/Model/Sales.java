package Model;

import Model.Enums.Origin;
import Model.Enums.PartnerShip;
import Model.Enums.Period;
import Model.Enums.Situation;
import Model.Enums.ToPrioritize;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Sales {

    private int id;
    private Seller seller;
    private LocalDateTime sellDateHour;
    private String customers; //cliente 
    private String contact;
    private String cpf;
    private String packages;
    private LocalDateTime installationMarked; //instalacao marcada
    private Period period;
    private Origin origin;
    private Situation situation;
    private String observation;
    private ToPrioritize prioritize;
    private float valuePackage;
    private PartnerShip partnetship;

    public Sales() {

    }

    public Sales(Seller seller,
            LocalDateTime sellDateHour,
            String cpf,
            String customers,
            String contact,
            String packages,
            float valuePackage,
            LocalDateTime installationMarked,
            Period period,
            Origin origin,
            Situation situation,
            String observation,
            ToPrioritize prioritize) {
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
        this.valuePackage = valuePackage;
        this.situation = situation;
        this.prioritize = prioritize;
    }

    public Sales(Seller seller,
            LocalDateTime sellDateHour,
            String cpf,
            String customers,
            String contact,
            String packages,
            float valuePackage,
            LocalDateTime installationMarked,
            Period period,
            Origin origin,
            Situation situation,
            PartnerShip partnetship,
            String observation,
            ToPrioritize prioritize) {
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
        this.valuePackage = valuePackage;
        this.situation = situation;
        this.partnetship = partnetship;
        this.prioritize = prioritize;
    }

    public Sales(int id, Seller seller, LocalDateTime sellDateHour, String cpf, String customers, String contact, String packages, float valuePackage, LocalDateTime installationMarked, Period period, Origin origin, Situation situation, PartnerShip partnetship, String observation, ToPrioritize prioritize) {
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
        this.valuePackage = valuePackage;
        this.situation = situation;
        this.partnetship = partnetship;
        this.id = id;
        this.prioritize = prioritize;

    }

    public Sales(int id, Situation situation, String observation, LocalDateTime installationMarked) {
        this.observation = observation;
        this.installationMarked = installationMarked;

        this.situation = situation;
        this.id = id;

    }

    public PartnerShip getPartnetship() {
        return partnetship;
    }

    public void setPartnetship(PartnerShip partnetship) {
        this.partnetship = partnetship;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public float getValuePackage() {
        return valuePackage;
    }

    public void setValuePackage(float valuePackage) {
        this.valuePackage = valuePackage;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public ToPrioritize getPrioritize() {
        return prioritize;
    }

    public void setPrioritize(ToPrioritize prioritize) {
        this.prioritize = prioritize;
    }

}
