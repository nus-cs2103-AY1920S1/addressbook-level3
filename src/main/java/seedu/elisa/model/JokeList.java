package seedu.elisa.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * List of jokes to pick and display
 * */

public class JokeList {
    private InputStream jokeFile = JokeList.class.getResourceAsStream("/documents/jokes.txt");
    private ArrayList<String> jokes;
    private Random rng;

    public JokeList() {
        jokes = new ArrayList<>();
        rng = new Random();

        BufferedReader r = new BufferedReader(new InputStreamReader(jokeFile));

        try {
            String l;
            while ((l = r.readLine()) != null) {
                jokes.add(l);
            }
        } catch (IOException e) {
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
