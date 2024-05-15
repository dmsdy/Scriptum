package analyzers;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ericojustier
 */
public class LexNumber implements AFD{
    int position;
    char current;
    
    @Override
    public Token process(int position, String text){
        this.position = position;
        this.current = text.charAt(position);
        
        if(Character.isDigit(current)){
            String number = get_number(text);
            return new Token("NUMBER", number);
        }
        return null;
    }
    
    public String get_number(String text){
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