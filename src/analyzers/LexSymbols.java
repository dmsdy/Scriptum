package analyzers;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ericojustier
 */
public class LexSymbols implements AFD{
    int position;
    char current;
    
   @Override
    public Token process(int position, String text) {
        this.position = position;
        this.current = text.charAt(position);
        
        // Verifica se o caractere atual é ':' e o próximo caractere é '='
        if (current == ':' && position + 1 < text.length() && text.charAt(position + 1) == '=') {
            return new Token("ASSIGNMENT", ":=");
//        } else if(current == '('){
//            return new Token("OPEN_PARENTHESES", "(");
//        } else if(current == ')'){
//            return new Token("CLOSE_PARENTHESES", ")");
        } else if(current == '+'){
            return new Token("OPERATOR_ADD", "+");
        } else if(current == '-'){
            return new Token("OPERATOR_SUB", "-");
        } else if(current == '/'){
            return new Token("OPERATOR_DIV", "/");
        } else if(current == '*'){
            return new Token("OPERATOR_MUL", "*");
        } else if(current == '%'){
            return new Token("OPERATOR_MOD", "%");
        } else if(current == ';'){
            return new Token("CONDITION_PRO", ";");
        } else if(current == '='){
            return new Token("COMPARISION", "=");
        }else if(current == ','){
            return new Token("SEPARATION", ",");
        } else if(current == '<'){
            return new Token("COMPARISION_SMALLER", "<");
        } else if(current == '>'){
            return new Token("COMPARISION_BIGGER", ">");
        }
//        } else if (current == '<' && position + 1 < text.length() && text.charAt(position + 1) == '=') {
//            return new Token("COMPARISION_SMALLER_EQUAL", "<=");
//        } else if (current == '>' && position + 1 < text.length() && text.charAt(position + 1) == '=') {
//            return new Token("COMPARISION_BIGGER_EQUAL", ">=");
//        }
        
        return null;
    }
}


