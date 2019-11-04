package seedu.address.logic.search;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.MainApp;
import seedu.address.model.autocomplete.AutoCompleteModel;
import seedu.address.model.autocomplete.Word;

/**
 * Main controller class to execute the searching logic
 */
public class AutoComplete {

    private static final int numOfSug = 10;

    private static AutoCompleteModel acModel;
    private static Word[] matchedResults;
    private static boolean displayWeights;

    public AutoComplete() {
        matchedResults = new Word[numOfSug];
        displayWeights = false;
    }

    /**
     * Read word storage from txt
     *
     * @param is path for the library of words
     * @return the suggestions to be shown in textField
     */
    private static Word[] importDictionary(InputStream is) {
        Word[] queries = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            int lines = Integer.parseInt(reader.readLine());

            queries = new Word[lines];
            for (int i = 0; i < lines; i++) {
                String line = reader.readLine();
                if (line == null) {
                    System.err.println("Could not read line " + (i + 1));
                    System.exit(1);
                }
                int tab = line.indexOf('\t');
                if (tab == -1) {
                    System.err.println("No tab character in line " + (i + 1));
                    System.exit(1);
                }
                long weight = Long.parseLong(line.substring(0, tab).trim());
                String query = line.substring(tab + 1);
                queries[i] = new Word(query, weight);
            }
            reader.close();
        } catch (Exception e) {
            System.err.println("Could not read or parse input file ");
            e.printStackTrace();
            System.exit(1);
        }

        return queries;
    }

    /**
     * Initialize an autocomplete model
     */
    public static void initAc() {
        InputStream in = MainApp.class.getResourceAsStream("/data/vocabulary.txt");
        Word[] data = importDictionary(in);
        acModel = new AutoCompleteModel(data);
    }

    /**
     * Makes a call to the implementation of AutoCompleteModel to get
     * suggestions for the currently entered text.
     *
     * @param text string to search for
     */
    public static List<String> getSuggestions(String text) {

        List<String> suggestions = new ArrayList<>();
        // don't search for suggestions if there is no input
        if (text.length() > 0) {
            // get all matching words
            Word[] allResults = acModel.allMatches(text);
            if (allResults == null) {
                throw new NullPointerException("allMatches() is null");
            }

            if (allResults.length > numOfSug) {
                matchedResults = Arrays.copyOfRange(allResults, 0, numOfSug);
            } else {
                matchedResults = allResults;
            }

            if (!displayWeights) {
                for (Word matchedResult : matchedResults) {
                    suggestions.add(matchedResult.getQuery());
                }
            }
        }
        return suggestions;
    }
}
