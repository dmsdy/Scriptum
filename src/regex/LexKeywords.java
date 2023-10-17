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
public class LexKeywords implements AFD{
    int position;
    char current;

    @Override
    public Token process(int position, String text) {
        this.position = position;
        this.current = text.charAt(position);
        
        if(Character.isLetter(current)){
            String word = get_string(text);
            
            switch(word){
                case "integrum":
                    return new Token("KEYWORD_INTEGRUM", word);
                case "decimus":
                    return new Token("KEYWORD_DECIMUS", word);
                case "verbum":
                    return new Token("KEYWORD_VERBUM", word);
                case "si":
                    return new Token("KEYWORD_SI", word);
                case "tum":
                    return new Token("KEYWORD_TUM", word);
                case "alioquin":
                    return new Token("KEYWORD_ALIOQUIN", word);
                case "dum":
                    return new Token("KEYWORD_DUM", word);
                case "pro":
                    return new Token("KEYWORD_PRO", word);
                case "fac":
                    return new Token("KEYWORD_FAC", word);
                case "finis":
                    return new Token("KEYWORD_FINIS", word);
                case "legere":
                    return new Token("KEYWORD_LEGERE", word);
                case "scribere":
                    return new Token("KEYWORD_SCRIBERE", word);
                
            }
                    
        }
        
        return null;
    }
    
    public String get_string(String text) {
        String result = "";
        while(current != '@' && Character.isLetter(current)){
            result += current;
            advance(text);
        }
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
