package analyzers;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author ericojustier
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException{
        
        //lexico
        
        LexicalAnalyzer lex = new LexicalAnalyzer();
        List<Token> list_tokens = lex.lexical();
        
//        for (Token token : list_tokens) {
//            System.out.println(token);
//        }
        Parser parser = new Parser(list_tokens);
        parser.main();
        
       
        
    }
    
}