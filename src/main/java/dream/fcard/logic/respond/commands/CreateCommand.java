package dream.fcard.logic.respond.commands;

/*import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.cards.FlashCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
*/
/**
 * Represents a command that creates a new deck or card (?).
 */
/*public class CreateCommand {
    private String deckName;
    private String currFront;
    private String currBack;
    private ArrayList<String> choices;

    private boolean hasFront;
    private boolean hasBack;
    private boolean hasChoice;

    private Deck currDeck;

    public CreateCommand() {

    }

    public CreateCommand(Deck deck) {
        currDeck = deck;
    }

    public void processInput(String input) {
        if(input.contains("deck/")) {
            deckName = input.split("deck/")[1].split(" ")[0];
        }

        if (hasFront(input)) {
            currFront = input.split("front/")[1].split(" ")[0];
            hasFront = true;
        }

        if (hasBack(input)) {
            currBack = input.split("back/")[1].split(" ")[0];
            hasBack = true;
        }

        if(!hasChoice(input)) {

        }
    }

    /**
     *
     * @param input
     * @return
     * /
    private boolean hasFront(String input) {
        return input.contains("front/");
    }

    /**
     *
     * @param input
     * @return
     * /
    private boolean hasBack(String input) {
        return input.contains("back/");
    }

    /**
     *
     * @param input
     * @return
     * /
    private boolean hasChoice(String input) {
        return input.contains("choice/");
    }

    public Deck getCurrDeck() {
        return this.currDeck;
    }

}*/

