package analyzers;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ericojustier
 */
public class Variable {
    String var_type;
    String var_id;
    String var_value;

    public Variable(String var_type, String var_id, String var_value) {
        this.var_type = var_type;
        this.var_value = var_value;
        this.var_id = var_id;
    }
    
   public boolean is_value_of_type(String value) {
       System.out.println(var_type);
    switch (var_type) {
        case "verbum":
            return value.startsWith("\"") && value.endsWith("\"");
        case "integrum": 
            try {
                Integer.parseInt(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        case "decimus":
            try {
                Double.parseDouble(value);
                return value.contains(".");
            } catch (NumberFormatException e) {
                return false;
            }
        default:
            return false;
    }
}

    
    

    public String get_var_type() {
        return var_type;
    }

    public void set_var_type(String var_type) {
        this.var_type = var_type;
    }

    public String get_var_value() {
        return var_value;
    }

    public void set_var_value(String var_value) {
        this.var_value = var_value;
    }

    public String get_var_id() {
        return var_id;
    }

    public void set_var_id(String var_id) {
        this.var_id = var_id;
    }
    
    
}
