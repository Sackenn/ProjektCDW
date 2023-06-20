package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;


import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.io.File;



import gui.Main;
import logic.WordGenerator;
import logic.FileManager;

public class ActionTest {

    @Test
    public void testGenerateWords() {

        assertEquals(0, WordGenerator.generateWords("").size());


        assertEquals(1, WordGenerator.generateWords("a").size());
        assertTrue(WordGenerator.generateWords("a").contains("a"));


        assertEquals(3, WordGenerator.generateWords("abc").size());
        assertTrue(WordGenerator.generateWords("abc").contains("abc"));
        assertTrue(WordGenerator.generateWords("abc").contains("acb"));
        assertTrue(WordGenerator.generateWords("abc").contains("bac"));
    }

    @Test
    public void testSaveAndLoadFromFile() {
        String testContent = "Test content";

        try {

            File testFile = new File("test.txt");
            FileManager.saveToFile(testFile, testContent);


            String loadedContent = FileManager.loadFromFile(testFile);
            assertEquals(testContent, loadedContent);

            
            testFile.delete();
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }



    @Test
    public void testGUI() {

        Main gui = new Main();
        gui.setVisible(true);
    }
}