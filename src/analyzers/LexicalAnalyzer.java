package analyzers;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author ericojustier
 */
public class LexicalAnalyzer {
    
    public List<Token> lexical() throws FileNotFoundException{
//        File file = new File("/Users/ericojustier/arquivosfei/compiladores/scriptum/src/lexer/codigo.txt");
//        Scanner input = new Scanner(file);
        System.out.println("Scriptum>>");
        Scanner input = new Scanner(System.in);
        List<Token> tokens = new ArrayList<>();

        try {
            while (input.hasNextLine()) {
                String linha = input.nextLine();

                Lexer lexer = new Lexer(linha);
//                lexer.setInput(linha);

                Token token = lexer.get_next_token();

                while (!token.get_type().equals("EOF")) {
                    tokens.add(token);
                    //System.out.println(token);
                    token = lexer.get_next_token();
                }
                
            }
        } catch (RuntimeException e) {
//            System.out.println(e.getMessage());
        } finally {
            input.close();
        }
        
        return tokens;
    }
}
//    {
//        Scanner input = new Scanner(System.in);
//        
//        try{
//            System.out.println("SCRIPTUM_LEXER: ");
//            String text = input.nextLine();
//            Lexer lexer = new Lexer(text);
//            Token token = lexer.get_next_token();
//            
//            while(!token.get_type().equals("EOF")){
//                System.out.println(token);
//                token = lexer.get_next_token();
//            }
//        }catch(Exception e){
//            System.out.println("Error: " + e.getMessage());
//            input.close();
//        }
//    }
//}
