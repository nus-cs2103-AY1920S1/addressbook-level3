package seedu.address.model.password.analyser.resources;

import java.util.Map;

/**
 * A {@code Dictionary} stub that has hard coded dictionary mappings
 */
public class DictionaryStub extends Dictionary {

    public DictionaryStub(String name, Map<String, Integer> dictionary) {
        super(name, dictionary);
    }

    @Override
    public Integer getRank(String s) {
        switch(s) {
        case "qwe":
            return 1;
        case "qwerty":
            return 2;
        case "qwer":
            return 3;
        default:
            return null;
        }
    }

}
