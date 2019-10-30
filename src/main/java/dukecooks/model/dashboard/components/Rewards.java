package dukecooks.model.dashboard.components;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represent rewards that can be triggered in DukeCooks.
 */
public class Rewards {

    private ArrayList<String> jokeList;

    public Rewards() {
        jokeList = getJokeList();
    }

    /**
     * Gets a list of jokes.
     */
    public ArrayList<String> getJokeList() {
        ArrayList<String> arr = new ArrayList<>();
        arr.add("joke1");
        arr.add("joke2");
        arr.add("joke3");
        return arr;
    }

    public ArrayList<String> getAdviceList() {
        ArrayList<String> arr = new ArrayList<>();
        arr.add("advice1");
        arr.add("advice2");
        arr.add("advice3");
        return arr;
    }

    /**
     * Gets a joke randomly from jokeList.
     */
    public String generateJoke() {
        Random ran = new Random();
        int num = ran.nextInt(jokeList.size());
        return jokeList.get(num);
    }

}
