/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lexer;

/**
 *
 * @author ericojustier
 */
public class Token {
    //classe deve possuir tipo & lexema
    // < tipo, lexema >
    private String tipo;
    private String lexema;
    
    public Token(String tipo, String lexema){
        this.tipo = tipo;
        this.lexema = lexema;
    }
    
    @Override
    public String toString(){
        return "token< " + tipo + ", " + lexema + " >";
    }

    public String get_tipo() {
        return tipo;
    }

    public String get_lexema() {
        return lexema;
    }

    public int length(){
        return lexema.length();
    }
}
