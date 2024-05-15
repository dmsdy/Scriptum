package analyzers;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author ericojustier
 */
public class LexFloat implements AFD{
    int position;
    char current;
    
    @Override
    public Token process(int position, String text){
        this.position = position;
        this.current = text.charAt(position);
        
        if(Character.isDigit(current)){
            String decimal = get_decimal(text);
            
            //verifica se tem o ponto, se não sai da verificacao
            if(current == '.'){
                decimal += '.';
                advance(text);
                decimal += get_decimal(text);
                return new Token("FLOAT", decimal);
            }
            
        }
        return null;
    }
    
    public String get_decimal(String text){
    String result = "";
        while(current != '@' && Character.isDigit(current)){
            result += current;
            advance(text);
        }
        return result;
    }
    
    public void advance(String text){
        position += 1;
        if(position > (text.length() - 1)){
            current = '@';
        }else{
            current = text.charAt(position);
        }
    }
}
