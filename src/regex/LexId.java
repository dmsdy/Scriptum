/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package regex;

import lexer.Token;

/**
 *
 * @author ericojustier
 */
public class LexId implements AFD{
    int position;
    char current;
    
    @Override
    public Token process(int position, String text){
        this.position = position;
        this.current = text.charAt(position);
        
        if(Character.isLetter(current)){
            String id = get_id(text);
            return new Token("ID", id);
        }
        return null;
    }
    
    public String get_id(String text){
    String result = "";
        while(current != '@' && Character.isLetter(current)){
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
