package seedu.address.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * List of jokes to pick and display
 * */

public class JokeList {
    private Path jokeFile = Paths.get("data", "jokes.txt");
    private ArrayList<String> jokes;
    private Random rng;

    public JokeList() {
        jokes = new ArrayList<>();
        rng = new Random();

        File file = jokeFile.toFile();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                jokes.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns random joke from list
     */
    public String getJoke() {
        rng.setSeed(System.currentTimeMillis());
        int index = rng.nextInt(jokes.size());
        return jokes.get(index);
    }
}
