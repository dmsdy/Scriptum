package analyzers;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author ericojustier
 */
public class File_writer {
    private String filePath;

    public File_writer(String filePath) {
        this.filePath = filePath;
    }

    public void add(String text) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(text);
//            writer.newLine(); // Para adicionar uma nova linha após a string
        }
    }
    
    public void clear() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Não escrever nada no arquivo irá limpar o conteúdo existente.
        }
    }
}
