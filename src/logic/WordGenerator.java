package logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordGenerator {
    public static List<String> generateWords(String letters) {
        List<String> result = new ArrayList<>();

        wordCreator("", letters, result);

        // Usunięcie duplikatów z listy słów
        Set<String> uniqueChar = new HashSet<>(result);
        result.clear();
        result.addAll(uniqueChar);

        return result;
    }

    private static void wordCreator(String prefix, String lettersR, List<String> result) {
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