package analyzers;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

/**
 *
 * @author ericojustier
 */
public class Parser {

    List<Token> tokens;
    Token token;
    Map<String, Variable> symbols_table = new HashMap<>();

    File_writer writer = new File_writer("/Users/ericojustier/arquivosfei/compiladores/scriptum/src/lexer/arquivo.java");

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void java_error() throws IOException {
        writer.clear();
        writer.add("package lexer;\n");
        writer.add("public class Arquivo{\n");
        writer.add("public static void main() {\n");
        writer.add("}}\n");

    }

    public void print_symbols_table() {
        System.out.println("Tabela de Símbolos:");
        for (Map.Entry<String, Variable> entry : symbols_table.entrySet()) {
            String key = entry.getKey();
            Variable variable = entry.getValue();
            System.out.println("Tipo: " + variable.get_var_type() + ", ID: " + key + ", Valor: " + variable.get_var_value());
        }

        System.out.println("----------------------------------------");
    }

    public Token get_next_token() {
        if (tokens.size() > 0) {

            return tokens.remove(0);
        } else {
            return null;
        }
    }

    public void next() {
        this.token = get_next_token();
    }

    public void java_lexeme() throws IOException {
        writer.add(this.token.get_lexeme() + " ");
    }

    public void java_print(String msg) throws IOException {
        writer.add(msg + " ");
    }

    public void java_jump() throws IOException {
        writer.add("\n");
    }

    public void java_semicolon() throws IOException {
        writer.add("; ");
    }

    public void java_tab() throws IOException {
        writer.add("  ");
    }

    private String error(String msg) throws IOException {
        if (token == null) {
            return "";
        }
        java_error();
        System.out.println(msg + " at token: " + token.get_lexeme());
        throw new RuntimeException();
    }

    public void java_initialize() throws IOException {
        writer.clear();
        writer.add("///////////////////////////////////////////\n");
        writer.add("///////////////CODIGO EM JAVA//////////////\n");
        writer.add("///////////////////////////////////////////\n\n");
        writer.add("import java.util.Scanner;\n" + "\n" + "public class Arquivo{\n");
        writer.add("public static void main(String[] args) {\n");
        writer.add("Scanner input = new Scanner(System.in);\n");
    }

    public void main() throws IOException {
        java_initialize();
        next();
        my_batch();
        writer.add("}");
//        System.out.println("Sintaticamente correto!\n\n\n\n");
//        print_symbols_table();
        writer.add("}\n\n");
        writer.add("///////////////////////////////////////////\n");
        writer.add("/////////////////TERMINAL//////////////////\n");
        writer.add("///////////////////////////////////////////\n\n");
    }

    public void my_batch() throws IOException {

        if (token == null) {
            return;
        }
        if (is_type()) {
//            System.out.println("Entrou no bloco declaracao, tipo:" + token.get_lexeme());
            my_declaration();
//            System.out.println("Terminou verificacao de declaracao");
            my_batch();
//            System.out.println("Saiu no bloco declaracao");

        } else if (token.get_type().equals("ID")) {
//            System.out.println("Entrou no bloco atribuicao, id: " + token.get_lexeme());
            my_assignment();
//            System.out.println("Terminou verificacao de atribuicao");
            my_batch();
//            System.out.println("Saiu no bloco atribuicao");

        } else if (token.get_lexeme().equals("dum")) {
//            System.out.println("Entrou no bloco while");
            my_while();
//            System.out.println("\nTerminou verificacao de dum (while)");
            my_batch();
//            System.out.println("Saiu no bloco while");

        } else if (token.get_lexeme().equals("si")) {
//            System.out.println("Entrou no bloco if");
            my_if();
//            System.out.println("\nTerminou verificacao de si (if)");
            my_batch();
//            System.out.println("Saiu no bloco if");

        } else if (token.get_lexeme().equals("pro")) {
//            System.out.println("Entrou no bloco for");
            my_for();
//            System.out.println("\nTerminou verificacao de pro (for)");
            my_batch();
//            System.out.println("Saiu no bloco for");

        } else if (token.get_lexeme().equals("legere")) {
//            System.out.println("Entrou no bloco input");
            my_input();
//            System.out.println("\nTerminou verificacao de legere (leitura)");
            my_batch();
//            System.out.println("Saiu no bloco input");

        } else if (token.get_lexeme().equals("scribere")) {
//            System.out.println("Entrou no bloco print");
            my_print();
//            System.out.println("\nTerminou verificacao de scribere (escrita)");
            my_batch();
//            System.out.println("Saiu no bloco print");
        }

    }

    private boolean is_type() {
        return token.get_lexeme().equals("verbum")
                || token.get_lexeme().equals("integrum")
                || token.get_lexeme().equals("decimus");
    }

    public void my_declaration() throws IOException {
        String type = my_type();
        if (token.get_type().equals("ID")) {
//            System.out.println("Terminou em " + token.get_lexeme());
            java_print(token.get_lexeme());
            symbols_table.put(token.get_lexeme(), new Variable(type, token.get_lexeme(), ""));
            java_semicolon();
            java_jump();
            next();
        } else {
            error("NECESSARIO UM ID PARA DECLARACAO");
        }
    }

    public String my_type() throws IOException {
        String type = "";
        if (token.get_lexeme().equals("verbum")) {
            java_print("String");
            type = "verbum";
            next();
        } else if (token.get_lexeme().equals("integrum")) {
            java_print("int");
            type = "integrum";
            next();
        } else if (token.get_lexeme().equals("decimus")) {
            java_print("double");
            type = "decimus";
            next();
        } else {
            error("TIPO DE VARIAVEL INVALIDO");
        }
        return type;
    }

    public void my_assignment() throws IOException {
        if (token.get_type().equals("ID")) {
            String id = token.get_lexeme();
            Variable var = symbols_table.get(id);
            if (var == null) {
                error("VARIAVEL NAO DECLARADA");

            } else {
                java_lexeme();
                next();
                if (token.get_lexeme().equals(":=")) {
                    java_print("=");
                    next();
                    String value = my_expression();
                    var.set_var_value(value);
                    symbols_table.put(id, var);
                    java_semicolon();
                    java_jump();
                } else {
                    error("NECESSARIO OPERADOR DE ATRIBUICAO");
                }
            }
        } else {
            error("NECESSARIO UM ID PARA ATRIBUICAO");
        }

    }

//    public void my_assignment() throws IOException {
//    if (token.get_type().equals("ID")) {
//        String id = token.get_lexeme();
//        Variable var = symbols_table.get(id);
//        if (var == null) {
//            java_error();
//            error("VARIÁVEL NÃO DECLARADA");
//        } else {
//            java_lexeme();
//            next();
//            if (token.get_lexeme().equals(":=")) {
//                java_print("=");
//                next();
//                // Verifica se o próximo token é um identificador (ou seja, uma possível variável)
//                if (token.get_type().equals("ID")) {
//                    String secondId = token.get_lexeme();
//                    Variable secondVar = symbols_table.get(secondId);
//                    if (secondVar == null) {
//                        java_error();
//                        error("SEGUNDA VARIÁVEL NÃO DECLARADA");
//                    } else if (!var.get_var_type().equals(secondVar.get_var_type())) {
//                        java_error();
//                        error("TIPOS INCOMPATÍVEIS PARA ATRIBUIÇÃO");
//                    } else {
//                        // Se os tipos são compatíveis, prossegue com a atribuição.
//                        java_lexeme(); // Imprime o ID da segunda variável
//                        java_semicolon();
//                        java_jump();
//                        next();
//                    }
//                } else {
//                    // Se não for uma variável, trata como uma expressão
//                    String value = my_expression();
//                    if (var.is_value_of_type(value)) {
//                        var.set_var_value(value);
//                        symbols_table.put(id, var);
//                        java_semicolon();
//                        java_jump();
//                    } else {
//                        java_error();
//                        error("VALOR NÃO CORRESPONDE AO TIPO DA VARIÁVEL");
//                    }
//                }
//            } else {
//                java_error();
//                error("NECESSÁRIO OPERADOR DE ATRIBUIÇÃO");
//            }
//        }
//    } else {
//        java_error();
//        error("NECESSÁRIO UM ID PARA ATRIBUIÇÃO");
//    }
//}
    
    
    public void my_assignment_for() throws IOException {
        if (token.get_type().equals("ID")) {
//            System.out.println("Entrou id");
            String id = token.get_lexeme();
            Variable var = symbols_table.get(id);
            if (var == null) {
                java_error();
                error("VARIAVEL NAO DECLARADA");
            } else {
                java_lexeme();
                next();
                if (token.get_lexeme().equals(":=")) {
                    java_print("=");
                    next();
                    String value = my_expression();
                    if (var.is_value_of_type(value)) {
                        var.set_var_value(value);
                        symbols_table.put(id, var);
                    } else {
                        error("VALOR NAO CORRESPONDE AO TIPO DA VARIAVEL");
                    }
                } else {
                    error("NECESSARIO OPERADOR DE ATRIBUICAO");
                }
            }
        } else {
            error("NECESSARIO UM ID PARA ATRIBUICAO");
        }
    }
    
    
    //    public void my_assignment_for() throws IOException {
//    if (token.get_type().equals("ID")) {
//        String id = token.get_lexeme();
//        Variable var = symbols_table.get(id);
//        if (var == null) {
//            java_error();
//            error("VARIÁVEL NÃO DECLARADA");
//        } else {
//            java_lexeme();
//            next();
//            if (token.get_lexeme().equals(":=")) {
//                java_print("=");
//                next();
//                // Verifica se o próximo token é um identificador (ou seja, uma possível variável)
//                if (token.get_type().equals("ID")) {
//                    String secondId = token.get_lexeme();
//                    Variable secondVar = symbols_table.get(secondId);
//                    if (secondVar == null) {
//                        java_error();
//                        error("SEGUNDA VARIÁVEL NÃO DECLARADA");
//                    } else if (!var.get_var_type().equals(secondVar.get_var_type())) {
//                        java_error();
//                        error("TIPOS INCOMPATÍVEIS PARA ATRIBUIÇÃO");
//                    } else {
//                        // Se os tipos são compatíveis, prossegue com a atribuição.
//                        java_lexeme(); // Imprime o ID da segunda variável
//                        next();
//                    }
//                } else {
//                    // Se não for uma variável, trata como uma expressão
//                    String value = my_expression();
//                    if (var.is_value_of_type(value)) {
//                        var.set_var_value(value);
//                        symbols_table.put(id, var);
//                        java_semicolon();
//                        java_jump();
//                    } else {
//                        java_error();
//                        error("VALOR NÃO CORRESPONDE AO TIPO DA VARIÁVEL");
//                    }
//                }
//            } else {
//                java_error();
//                error("NECESSÁRIO OPERADOR DE ATRIBUIÇÃO");
//            }
//        }
//    } else {
//        java_error();
//        error("NECESSÁRIO UM ID PARA ATRIBUIÇÃO");
//    }
//}

    public String my_expression() throws IOException {
        String result = my_term();
        return result + my_expression_line();
//        my_term();
//        my_expression_line();
    }

    public String my_expression_line() throws IOException {
        if (token == null) {
            return "";
        }
        if (token.get_lexeme().equals("+")) {
            java_lexeme();
            next();
            return "+" + my_term() + my_expression_line();
//            my_term();
//            my_expression_line();
        } else if (token.get_lexeme().equals("-")) {
            java_lexeme();
            next();
            return "-" + my_term() + my_expression_line();
//            my_term();
//            my_expression_line();
        } else {
            return "";
        }
    }

    public String my_term() throws IOException {
        String result = my_factor();
        return result + my_term_line();
//        my_factor();
//        my_term_line();
    }

    public String my_term_line() throws IOException {
        if (token == null) {
            return "";
        }
        if (token.get_lexeme().equals("*")) {
            java_lexeme();
            next();
            return "*" + my_factor() + my_term_line();
//            my_factor();
//            my_term_line();
        } else if (token.get_lexeme().equals("/")) {
            java_lexeme();
            next();
            return "/" + my_factor() + my_term_line();
//            my_factor();
//            my_term_line();
        } else {
            return "";
        }
    }

    public String my_factor() throws IOException {
        String result = "";
        if (token == null) {
            return result;
        }
        if (token.get_type().equals("ID")) {
            java_lexeme();
            result = token.get_lexeme();
            next();
        } else if (token.get_type().equals("NUMBER")) {
            java_lexeme();
            result = token.get_lexeme();
            next();
        } else if (token.get_type().equals("FLOAT")) {
            java_lexeme();
            result = token.get_lexeme();
            next();
        } else if (token.get_type().equals("STRING")) {
            java_lexeme();
            result = token.get_lexeme();
            next();
        } else if (token.get_lexeme().equals("apertum")) {
            java_print("(");
            next();
            result = my_expression();
            if (token.get_lexeme().equals("clausum")) {
                java_print(")");
                next();
            } else {
                java_error();
                error("PARENTESES NAO FORAM INSERIDOS CORRETAMENTE");
            }
        } else {
            java_error();
            error("FATOR NAO FOI CHAMADO CORRETAMENTE!!!");
        }
        return result;
    }

    public void my_input() throws IOException {
    String a, b;
    if (token.get_lexeme().equals("legere")) {
        next();
        if (token.get_lexeme().equals("apertum")) {
            next();
            if (token.get_type().equals("STRING")) {
                a = token.get_lexeme();
                next();
                if (token.get_lexeme().equals(",")) {
                    next();
                    if (token.get_type().equals("ID")) {
                        b = token.get_lexeme();
                        Variable var = symbols_table.get(b);
                        if (var == null) {
                            error("VARIÁVEL NÃO DECLARADA");
                            return;
                        }
                        String varType = var.get_var_type();
                        // Aqui ocorre a verificação do tipo da variável e a impressão correspondente
                        if (varType.equals("verbum")) {
                            writer.add("System.out.println(" + a + ");\n");
                            writer.add(b + " = input.nextLine();\n");
                        } else if (varType.equals("integrum")) {
                            writer.add("System.out.println(" + a + ");\n");
                            writer.add(b + " = input.nextInt();\n");
                        } else if (varType.equals("decimus")) {
                            writer.add("System.out.println(" + a + ");\n");
                            writer.add(b + " = input.nextDouble();\n");
                        } else {
                            error("TIPO DE VARIÁVEL DESCONHECIDO");
                            return;
                        }
                        next();
                        if (!token.get_lexeme().equals("clausum")) {
                            error("PARENTESES NÃO FORAM INSERIDOS CORRETAMENTE");
                        }
                        java_jump();
                        next();
                    } else {
                        error("NENHUMA VARIÁVEL FOI CHAMADA NA LEITURA");
                    }
                } else {
                    error("FUNÇÃO DE LEITURA CHAMADA INCORRETAMENTE");
                }
            } else {
                error("STRING NECESSÁRIA PARA FUNÇÃO");
            }
        } else {
            error("FUNÇÃO NÃO FOI CHAMADA CORRETAMENTE");
        }
    } else {
        error("FUNÇÃO INEXISTENTE!");
    }
}


    public void my_print() throws IOException {
        if (token.get_lexeme().equals("scribere")) {
            java_print("System.out.println");
            next();
            if (token.get_lexeme().equals("apertum")) {
                java_print("(");
                next();
                my_expression();
                if (token.get_lexeme().equals("clausum")) {
                    java_print(")");
                    next();
                    java_semicolon();
                    java_jump();
                } else {
                    java_error();
                    error("PARENTESES NAO FORAM INSERIDOS CORRETAMENTE");
                }
            } else {
                java_error();
                error("FUNCAO NAO FOI CHAMADA CORREMTAMENTE");
            }
        } else {
            java_error();
            error("FUNCAO INEXISTENTE!");
        }
    }

    public void my_return() throws IOException {
        if (token.get_lexeme().equals("redit")) {
            next();
            my_expression();
        } else {
            java_error();
            error("FUNCAO INEXISTENTE!");
        }
    }

    public void my_if() throws IOException {
        if (token.get_lexeme().equals("si")) {
            java_print("if");
            next();
            if (token.get_lexeme().equals("apertum")) {
                java_print("(");
                next();
                my_condition();
                if (token.get_lexeme().equals("clausum")) {
                    java_print(")");
                    next();
                    if (token.get_lexeme().equals("tum")) {
                        next();
                        if (token.get_lexeme().equals("fac")) {
                            java_print("{");
                            java_jump();
                            //java_tab();
                            next();
                            my_batch();
                            if (token.get_lexeme().equals("finis")) {
                                java_print("}");
                                next();
                                my_else();
                                java_jump();
                            } else {
                                java_error();
                                error("IF: FUNCAO NAO FOI FINALIZADA CORRETAMENTE");
                            }
                        } else {
                            java_error();
                            error("IF: FUNCAO NAO FOI CHAMADA CORRETAMENTE");
                        }
                    } else {
                        java_error();
                        error("ELEMENTO 'TUM' NAO FOI CHAMADO");
                    }
                } else {
                    java_error();
                    error("CONDICAO NAO FOI CHAMADA CORRETAMENTE");
                }
            } else {
                java_error();
                error("CONDICAO NAO FOI CHAMADA CORRETAMENTE");
            }
        } else {
            java_error();
            error("FUNCAO INEXISTENTE");
        }
    }

    public void my_else() throws IOException {
        if (token == null) {
            return;
        }
        if (token.get_lexeme().equals("alioquin")) {
            java_print("else");
            next();
            if (token.get_lexeme().equals("fac")) {
                java_print("{");
                java_jump();
                //java_tab();
                next();
                my_batch();
                if (token.get_lexeme().equals("finis")) {
                    java_print("}");
                    next();
                } else {
                    error("ELSE: FUNCAO NAO FOI FINALIZADA CORRETAMENTE");
                }
            } else {
                error("ELSE: UNCAO NAO FOI CHAMADA CORRETAMENTE");
            }
        } else {
            return;
        }
    }

    public void my_condition() throws IOException {
        if (token.get_type().equals("ID")) {
            java_lexeme();
            next();
            my_operator();
            my_expression();
        } else {
            error("CONDICAO NAO FOI INSERIDA CORRETAMENTE");
        }
    }

    public void my_for() throws IOException {
        if (token.get_lexeme().equals("pro")) {
            java_print("for");
            next();
            if (token.get_lexeme().equals("apertum")) {
                java_print("(");
                next();
                my_assignment_for();
                if (token.get_lexeme().equals(",")) {
                    java_semicolon();
                    next();
                    my_condition();
                    if (token.get_lexeme().equals(",")) {
                        java_semicolon();
                        next();
                        my_incrementation();
                        if (token.get_lexeme().equals("clausum")) {
                            java_print(")");
                            next();
                            if (token.get_lexeme().equals("fac")) {
                                java_print("{");
                                java_jump();
                                //java_tab();
                                next();
                                my_batch();
                                if (token.get_lexeme().equals("finis")) {
                                    java_print("}");
                                    java_jump();
                                    next();
                                } else {
                                    error("FUNCAO NAO FOI FINALIZADA CORRETAMENTE");
                                }
                            } else {
                                error("FUNCAO NAO FOI CHAMADA CORRETAMENTE");
                            }
                        } else {
                            error("CONDICAO NAO FOI CHAMADA CORRETAMENTE");
                        }
                    } else {
                        error("SEPARACAO NA CONDICAO NAO EXISTE");
                    }
                } else {
                    error("SEPARACAO NA CONDICAO NAO EXISTE");
                }
            } else {
                error("CONDICAO NAO FOI CHAMADA CORRETAMENTE");
            }
        } else {
            error("FUNCAO INEXISTENTE");
        }
    }

    public void my_while() throws IOException {
        if (token.get_lexeme().equals("dum")) {
            java_print("while");
            next();
            if (token.get_lexeme().equals("apertum")) {
                java_print("(");
                next();
                my_condition();
                if (token.get_lexeme().equals("clausum")) {
                    java_print(")");
                    next();
                    if (token.get_lexeme().equals("fac")) {
                        java_print("{");
                        java_jump();
                        //java_tab();
                        next();
                        my_batch();
                        if (token.get_lexeme().equals("finis")) {
                            java_print("}");
                            java_jump();
                            next();
                        } else {
                            error("FUNCAO NAO FOI FINALIZADA CORRETAMENTE");
                        }
                    } else {
                        error("FUNCAO NAO FOI CHAMADA CORRETAMENTE");
                    }
                } else {
                    error("CONDICAO NAO FOI CHAMADA CORRETAMENTE");
                }
            } else {
                error("CONDICAO NAO FOI CHAMADA CORRETAMENTE");
            }
        } else {
            error("FUNCAO INEXISTENTE");
        }
    }

    public void my_operator() throws IOException {
        if (token.get_lexeme().equals("<")) {
            java_lexeme();
            next();
        } else if (token.get_lexeme().equals(">")) {
            java_lexeme();
            next();
        } else if (token.get_lexeme().equals("<=")) {
            java_lexeme();
            next();
        } else if (token.get_lexeme().equals(">=")) {
            java_lexeme();
            next();
        } else if (token.get_lexeme().equals("=")) {
            java_print("==");
            next();
        } else {
            error("ERRO, operador inexistente");
        }
    }

    public void my_incrementation() throws IOException {
        if (token.get_type().equals("ID")) {
            java_lexeme();
            next();
            if (token.get_lexeme().equals("++")) {
                java_lexeme();
                next();

            } else if (token.get_lexeme().equals("--")) {
                java_lexeme();
                next();

            } else {
                error("INCREMENTACAO OU DECREMENTACAO NAO FOI CHAMADA CORRETAMENTE");
            }
        } else {
            error("NAO EXISTE INCREMENTACAO");
        }
    }
}
//main -> batch
//bloco -> declaration batch | assignment batch | input batch | print my_batch | if batch | for batch | while batch | return batch
//declaration -> my_type id ':=' expression
//assignment -> id ':=' expression
//expression -> term expression_line
//expression_line -> '+' term expression_line | '-' term expression_line | ε
//term -> my_factor term_line
//term_line -> '*' factor term_line | '/' factor term_line | ε
//factor -> id | numero | float | string | '(' expression ')'
//input -> 'legere' '(' expression ')'
//print -> 'scribere' '(' expression ')'
//if -> 'si' '(' condition ')' 'tum' 'fac' batch else 'finis'
//else -> 'alioquin' 'fac' batch 'finis' | ε
//for -> 'pro' '(' assignment ',' condition ',' incrementation ')' 'fac' batch 'finis'
//while -> 'dum' '(' condition ')' 'fac' batch 'finis'    
//condition -> id operator expression
//operator -> '<' | '>' | '<=' | '>=' | '='   
//type -> 'verbum' | 'integrum' | 'decimus'
//incrementation -> id '++' | '--'

