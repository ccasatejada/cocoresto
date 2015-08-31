package entities;

public enum OrderStatus {
    
  CANCELLED (0, "Annulée"),
  OPENED (1, "Ouverte"),
  VALIDATE (2, "Validée"),
  PREPARED (3, "En préparation"),
  FINISHED (4, "Terminée"),
  PAYED (5, "Payée");
   
  private Integer id;
  private String name = "";
   
  OrderStatus(Integer id, String name){
    this.id = id;
    this.name = name;
  }
   
  public String toString(){
    return name;
  }
}
    
