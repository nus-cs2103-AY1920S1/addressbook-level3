package seedu.elisa.storage;

import seedu.elisa.model.exceptions.IllegalListException;

import java.io.*;
import java.nio.file.Path;
import java.util.TreeSet;

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

    public void updateScoreList(int score) {
        scorelist.add(score);
    }

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
