import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;



class Letters implements ActionListener {
    private List<JTextField> LetterF;
    private JTextArea wordsTA;

    public Letters(List<JTextField> LetterF, JTextArea wordsTA) {
        this.LetterF = LetterF;
        this.wordsTA = wordsTA;
    }

    public void actionPerformed(ActionEvent e) {
        wordGen();
    }

    private void wordGen() {
        StringBuilder sb = new StringBuilder();

        for (JTextField textField : LetterF) {
            String letter = textField.getText();

            if (letter.length() > 1) {
                JOptionPane.showMessageDialog(null, "Wprowadź tylko jedną literę w każdym polu.", "Błąd",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!letter.isEmpty()) {
                sb.append(letter);
            }
        }

        String letters = sb.toString();

        if (letters.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Wprowadź litery.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> genWords = wordGen(letters);

        sb = new StringBuilder();
        for (String word : genWords) {
            sb.append(word).append("\n");
        }
        wordsTA.setText(sb.toString());
    }

    private List<String> wordGen(String letters) {
        List<String> result = new ArrayList<>();

        wordCreator("", letters, result);

        // Usunięcie duplikatów z listy słów
        Set<String> uniqueChar = new HashSet<>(result);
        result.clear();
        result.addAll(uniqueChar);

        return result;
    }

    private void wordCreator(String prefix, String lettersR, List<String> result) {
        if (lettersR.length() == 0) {
            result.add(prefix);
            return;
        }

        for (int i = 0; i < lettersR.length(); i++) {
            char cLetter = lettersR.charAt(i);

            String nPrefix = prefix + cLetter;
            String nLettersR = lettersR.substring(0, i)
                    + lettersR.substring(i + 1);

            wordCreator(nPrefix, nLettersR, result);
        }
    }
}


class SW implements ActionListener {
    private JTextArea wordsTA;

    public SW(JTextArea wordsTA) {
        this.wordsTA = wordsTA;
    }

    public void actionPerformed(ActionEvent e) {
        save();
    }

    private void save() {
        JFileChooser files = new JFileChooser();
        files.setDialogTitle("Zapisz Słowa do Pliku");

        int userChoice = files.showSaveDialog(null);
        if (userChoice == JFileChooser.APPROVE_OPTION) {
            File selectedFile = files.getSelectedFile();

            try (FileWriter writer = new FileWriter(selectedFile)) {
                writer.write(wordsTA.getText());
                writer.flush();

                JOptionPane.showMessageDialog(null, "Słowa zostały zapisane do pliku.", "Sukces",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas zapisywania pliku.", "Błąd",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}


class LW implements ActionListener {
    private JTextArea wordsTA;

    public LW(JTextArea wordsTA) {
        this.wordsTA = wordsTA;
    }

    public void actionPerformed(ActionEvent e) {
        loadFile();
    }

    private void loadFile() {
        JFileChooser files = new JFileChooser();
        files.setDialogTitle("Wczytaj Słowa z Pliku");

        int userChoice = files.showOpenDialog(null);
        if (userChoice == JFileChooser.APPROVE_OPTION) {
            File selectedFile = files.getSelectedFile();

            try (Scanner scanner = new Scanner(selectedFile)) {
                StringBuilder sb = new StringBuilder();
                while (scanner.hasNextLine()) {
                    sb.append(scanner.nextLine()).append("\n");
                }
                wordsTA.setText(sb.toString());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas wczytywania pliku.", "Błąd",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

public class Main extends JFrame {
    private static final long serialVersionUID = 1L;

    private List<JTextField> LetterF;
    private JTextArea wordsTA;

    public Main() {
        setTitle("Generator Słów");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel Window = new JPanel(new BorderLayout());
        setContentPane(Window);

        JPanel inputPanel = iPanel();
        Window.add(inputPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = TextASP();
        Window.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = bPanel();
        Window.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel iPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 0));

        JPanel lettersPanel = new JPanel();
        JLabel lettersLabel = new JLabel("Podaj litery:");
        lettersPanel.add(lettersLabel);

        LetterF = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            JTextField textField = new JTextField(1);
            LetterF.add(textField);
            lettersPanel.add(textField);
        }

        panel.add(lettersPanel);

        return panel;
    }

    private JScrollPane TextASP() {
        wordsTA = new JTextArea();
        wordsTA.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(wordsTA);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        return scrollPane;
    }

    private JPanel bPanel() {
        JPanel panel = new JPanel();

        JButton gb = new JButton("Generuj Słowa");
        gb.addActionListener(new Letters(LetterF, wordsTA));

        JButton sb = new JButton("Zapisz do Pliku");
        sb.addActionListener(new SW(wordsTA));

        JButton lb = new JButton("Wczytaj z Pliku");
        lb.addActionListener(new LW(wordsTA));

        panel.add(gb);
        panel.add(sb);
        panel.add(lb);

        return panel;
    }

    public static void main(String[] args) {
        Main gui = new Main();
        gui.setVisible(true);
    }
}
