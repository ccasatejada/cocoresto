package entities;

public enum OrderStatus {

    CANCELLED("Annulée"),
    OPENED("Ouverte"),
    VALIDATE("Validée"),
    PREPARED("En préparation"),
    FINISHED("Terminée"),
    PAYED("Payée");

    private String name;
            
    OrderStatus(String name) {
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }
}
