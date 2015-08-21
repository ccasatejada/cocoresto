
package entities;

public enum Unit {
    Grammes("g"),
    KiloCalories("kcal");
    
    private String name= "";
    
    Unit(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
    
}
