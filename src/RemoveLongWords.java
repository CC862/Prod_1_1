// Course: CS645
//Group Members: Mihir Rana, Cristofer Carcamo, Jyotika Chand

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Scanner;

public class RemoveLongWords {
    public static void main(String[] args) {
        String inputFilename = "../src/common-passwords3";
        String outputFilename = "../src/shortlist";
        ArrayList<String> words = new ArrayList<String>();

        // Read in the words from the input file
        try {
            File inputFile = new File(inputFilename);
            Scanner scanner = new Scanner(inputFile);

            while (scanner.hasNext()) {
                String word = scanner.next();
                if (word.length() <= 16) {
                    words.add(word);
                }
            }

            scanner.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the input file: " + e.getMessage());
            return;
        }

        // Write the modified text to the output file
        try {
            FileWriter outputFile = new FileWriter(outputFilename);
            for (String word : words) {
                outputFile.write(word + "\n"); // changed from a space to a newline character
            }
            outputFile.close();

            System.out.println("Words longer than 16 characters have been removed from the input file and written to the output file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the output file: " + e.getMessage());
        }
    }
}
