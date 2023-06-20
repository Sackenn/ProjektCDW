package logic;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// saveToFile zapisuje wygenerowane ciągi znaków do pliku

public class FileManager {
    public static void saveToFile(File file, String content) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
            writer.flush();
        }
    }

// loadFromFile wczytuje wcześniej zapisane do pliku ciągi znaków oraz
// oraz przekazuje je do wyświetlenia na ekranie 
    public static String loadFromFile(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
        }
        return sb.toString();
    }
}