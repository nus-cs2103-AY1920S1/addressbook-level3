package dream.fcard.logic.respond;

import java.util.ArrayList;


/**
 * Interface to take in user input and execute program behaviour.
 */
public class Responder {

    private static ArrayList<ArrayList<Responses>> buckets;

    /**
     * Groups responses into buckets for each ResponseGroup
     * Thus processing input checks for matching group before
     * matching each Responses enum; 2 level validation to
     * reduce overhead of checking every Responses enum.
     */
    private static void generateBuckets() {
        if (buckets != null) {
            return;
        }
        buckets = new ArrayList<>();
        for (ResponseGroup g : ResponseGroup.values()) {
            ArrayList<Responses> bucket = new ArrayList<>();
            for (Responses r : Responses.values()) {
                if (r.isInGroup(g)) {
                    bucket.add(r);
                }
            }
            buckets.add(bucket);
        }
    }

    /**
     * Takes in user input and the current state of the program.
     * Uses the user input to decide which is the appropriate
     * responseFunc to call.
     *
     * @param input A String representing the user input.
     */
    public static void takeInput(String input) {
        generateBuckets();
        outer: for (int i = 0; i < buckets.size(); i++) {
            if (ResponseGroup.values()[i].isInGroup(input)) {
                for (Responses r : buckets.get(i)) {
                    if (r.call(input)) {
                        System.out.println(r);
                        break outer;
                    }
                }
            }
        }
    }
}
