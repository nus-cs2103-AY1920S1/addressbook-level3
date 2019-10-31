package seedu.savenus.model.alias;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an adder which adds all command words in $aveNUS to the list of AliasPairs.
 */
public class CommandWordAdder {

    /**
     * Main method to add all command words to the list of AliasPairs.
     * @return the list of AliasPairs with all $aveNUS command words.
     */
    public List<AliasPair> addCommandWords() {
        List<AliasPair> aliasPairList = new ArrayList<>();
        aliasPairList.add(new AliasPair("add", ""));
        aliasPairList.add(new AliasPair("alias", ""));
        aliasPairList.add(new AliasPair("autosort", ""));
        aliasPairList.add(new AliasPair("budget", ""));
        aliasPairList.add(new AliasPair("buy", ""));
        aliasPairList.add(new AliasPair("clear", ""));
        aliasPairList.add(new AliasPair("collapse", ""));
        aliasPairList.add(new AliasPair("customsort", ""));
        aliasPairList.add(new AliasPair("defaukt", ""));
        aliasPairList.add(new AliasPair("delete", ""));
        aliasPairList.add(new AliasPair("dislike", ""));
        aliasPairList.add(new AliasPair("edit", ""));
        aliasPairList.add(new AliasPair("exit", ""));
        aliasPairList.add(new AliasPair("filter", ""));
        aliasPairList.add(new AliasPair("find", ""));
        aliasPairList.add(new AliasPair("help", ""));
        aliasPairList.add(new AliasPair("history", ""));
        aliasPairList.add(new AliasPair("info", ""));
        aliasPairList.add(new AliasPair("like", ""));
        aliasPairList.add(new AliasPair("makesort", ""));
        aliasPairList.add(new AliasPair("recommend", ""));
        aliasPairList.add(new AliasPair("removedislike", ""));
        aliasPairList.add(new AliasPair("removelike", ""));
        aliasPairList.add(new AliasPair("sort", ""));
        aliasPairList.add(new AliasPair("save", ""));
        aliasPairList.add(new AliasPair("topup", ""));
        aliasPairList.add(new AliasPair("viewsort", ""));

        return aliasPairList;
    }
}
