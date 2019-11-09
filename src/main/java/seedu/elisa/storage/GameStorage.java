package seedu.elisa.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import java.nio.file.Path;
import java.util.TreeSet;

import seedu.elisa.model.exceptions.IllegalListException;

/**
 * Game Storage class to store high score of game.
 */
public class GameStorage {

    private String filePath;
    private File file;
    private TreeSet<Integer> scorelist;

    public GameStorage(Path filePath) {
        this.filePath = filePath.toString();

        scorelist = new TreeSet<>();
    }

    public TreeSet<Integer> getScorelist() {
        return this.scorelist;
    }

    /**
     * Method to update scorelist.
     * @param score
     */
    public void updateScoreList(int score) {
        scorelist.add(score);
    }

    /**
     * Methods to save to file.
     * @throws Exception
     */
    public void save() throws Exception {
        try {
            FileWriter writer = new FileWriter(file);
            for (Integer score: scorelist) {
                writer.write(score + "\n");
            }
            writer.close();
        } catch (Exception e) {
            // This is not supposed to happen.
        }
    }

    /**
     * Method to load the file.
     * @throws Exception
     */
    public void load() throws Exception {
        try {
            file = new File(filePath);
            file.createNewFile();
            BufferedReader br = new BufferedReader(new FileReader(file));

            String str;

            while ((str = br.readLine()) != null) {
                int score = Integer.parseInt(str);
                scorelist.add(score);
            }

        } catch (FileNotFoundException e) {
            throw new IllegalListException();
        }
    }
}
