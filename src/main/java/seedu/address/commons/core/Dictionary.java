package seedu.address.commons.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import seedu.address.commons.exceptions.DictionaryException;

/**
 * Represents dictionary object with name and dictionary look up.
 */
public class Dictionary {
    private String name;
    private final Map<String, Integer> dictionary;

    /**
     * Creates dictionary object with specific name and dictionary.
     * @param name name of dictionary object
     * @param dictionary the hashmap of dictionary word to ranking
     */
    public Dictionary(String name, Map<String, Integer> dictionary) {
        this.name = name;
        this.dictionary = dictionary;
    }

    /**
     * Builds the required dictionary based on the specified dictionary name.
     * @param name the name of the required dictionary
     * @return the dictionary object
     */
    public static Dictionary build(String name) throws DictionaryException {
        try {
            switch (name) {
            case "passwords.txt":
                return new Dictionary(name, load("/dictionaries/passwords.txt"));
            default:
                throw new DictionaryException("Requested dictionary not found");
            }
        } catch (DictionaryException de) {
            throw new DictionaryException(de.getMessage(), de);
        }
    }

    /**
     * Loads the content of the file from the path into the dictionary.
     * @param path the path of the file with dictionary words
     * @return the hashmap of dictionary word to ranking
     */
    private static Map<String, Integer> load(String path) {
        //File initialFile = new File(path);
        Map<String, Integer> dictionary = new HashMap<>();
        try (InputStream is = Dictionary.class.getResourceAsStream(path);
             BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
            String line;
            int i = 1;
            while ((line = br.readLine()) != null) {
                dictionary.put(line, i++);
            }
        } catch (IOException e) {
            System.out.println("Error while reading " + path);
        }
        return dictionary;
    }

    /**
     * Gets the hashmap of dictionary word to ranking.
     * @return the dictionary
     */
    public Map<String, Integer> getDictionary() {
        return this.dictionary;
    }
}
