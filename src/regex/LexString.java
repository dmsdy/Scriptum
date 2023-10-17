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
public class LexString implements AFD{
    int position;
    char current;

    @Override
    public Token process(int position, String text) {
        this.position = position;
        this.current = text.charAt(position);
        
        if (current == '"' || current == '“'){
            String word = get_string(text);
            return new Token("STRING", word);
            
        }
        
        return null;
    }
    
    public String get_string(String text) {
        String result = "";
        
        // Continua a adicionar caracteres ao resultado até encontrar uma outra aspa dupla ou o final do texto
        do{
            result += current;
            advance(text);
            if(current == '"'){
                result += '"';
                break;
            }else if(current == '”'){
                result += '”';
                break;
            }
        }while(current != '@');
        
        return result;
    }
    
    public void advance(String text) {
        position += 1;
        if (position > (text.length() - 1)) {
            current = '@';
        } else {
            current = text.charAt(position);
        }
        
    }
}