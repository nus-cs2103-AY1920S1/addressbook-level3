package seedu.ezwatchlist.spellcheck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class WordDictionary {
    final static String HOME = System.getProperty("user.home");
    final static String GLOSSARY_FILEPATH = HOME + File.separator + "data" + File.separator + "words.txt";
    private final static List<String> CORPUS = new ArrayList<String>();

    private HashSet<String> wordList;

    public WordDictionary() throws IOException {
        wordList = new HashSet<>();

        File file = new File(GLOSSARY_FILEPATH);
        if (!file.exists()) {
            File parent = file.getParentFile();
            parent.mkdirs();
            initializeWordList(file);
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                wordList.add(line);
            }
            //loadFromList();
        } finally {
            br.close();
        }
    }

    private void initializeWordList(File file) throws IOException {
        file.createNewFile();
        Files.write(Paths.get(GLOSSARY_FILEPATH), CORPUS);
    }

    public HashSet<String> getWordList() {
        return wordList;
    }

    public void updateWordList() {
        //loadFromList();
    }
}
