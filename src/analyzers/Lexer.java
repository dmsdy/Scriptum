package analyzers;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ericojustier
 */
public class Lexer {
    private String text;
    private int position;
    private char current;
    private List<AFD> afds;//lista de automatos que vai ser processada
    
    public Lexer(String text){
        this.text = text;
        this.position = 0;
        this.current = text.charAt(position);
        afds = new ArrayList<>();
        afds.add(new LexString());
        afds.add(new LexKeywords());
        afds.add(new LexFloat());
        afds.add(new LexNumber());
        afds.add(new LexId());
        afds.add(new LexSymbols());
    }
    
    
    public void advance(int qtd){
        position += qtd;
        if(position > (text.length() - 1)){
            current = '@';
        }else{
        current = text.charAt(position);
        }
    }
    
    public void skip_white_space(){
        while(current != '@' && (current == ' ' || current == '\n' || current == '\r'))
            advance(1);
    }
    
    
    public Token get_next_token() {
        while (current != '@') {
        for (AFD afd : afds) {
            Token recognized = afd.process(position, text);
            if (recognized != null) {
                advance(recognized.length());
                return recognized;
            }
        }
        skip_white_space();  // Ajustado para chamar o novo método
    }
        
    while (current != '@') {
        for (AFD afd : afds) {
            Token recognized = afd.process(position, text);
            if (recognized != null) {
                advance(recognized.length());
                return recognized;
            }
        }
        if ((current == ' ' || current == '\n' || current == '\r')) {
            skip_white_space();
        } else {
            error();
        }
    }
    return new Token("EOF", "@");
    }
    
    public void error(){
        System.out.println(current);
        throw new RuntimeException("Caractere inválido");
    }
}
        
        