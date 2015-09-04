package library;

public abstract class FieldValidation {
    
    public static boolean checkInteger(String parameter, boolean required, int min) {

        if(parameter == null){
            return false;
        }
        
        if(required && parameter.trim().isEmpty()){
            return false;
        } else if (!required && parameter.trim().isEmpty()) {
            return true;
        }
        
        try {
            int nb = Integer.valueOf(parameter);
            if (nb < min) {
                return false;
            }
        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }
    
    
    
}
