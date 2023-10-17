/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package syntax;


import lexer.*;
import java.util.List;

/**
 *
 * @author ericojustier
 */
public class Parser {
    List<Token> tokens;
    Token token;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }
    
    public Token get_next_token(){
        if (tokens.size() > 0){
            return tokens.remove(0);
        }else {
            return null;
        }
    }
    
    //CRIAR GLC AQUI
    public void main(){
        token = get_next_token();
        expressao();
        System.out.println("Sintaticamente correto!");
    }
    
    public void expressao(){
        if(token.get_tipo() == "KEYWORD_VERBUM"){
            System.out.println(token.get_lexema());
            token = get_next_token();
        }
    }
}
