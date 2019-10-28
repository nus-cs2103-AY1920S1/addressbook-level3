package dream.fcard.logic.respond.commands;

import java.util.ArrayList;

import dream.fcard.core.commons.core.LogsCenter;
import dream.fcard.gui.Gui;
import dream.fcard.logic.respond.ResponseFunc;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.cards.FrontBackCard;
import dream.fcard.model.exceptions.DeckNotFoundException;

/**
 * Represents a command that creates a new deck or card (?).
 */
public class CreateCommand implements ResponseFunc {
    private String deckName;
    private String front;
    private String back;
    private ArrayList<String> choices;

    /**
     *
     * @param commandInput string
     * @param programState state object
     * @return
     * @throws DeckNotFoundException
     */
    @Override
    public boolean funcCall(String commandInput, State programState) {

        boolean success = parseInputOneShot(commandInput);

        if (!success) {
            Gui.showError("Need both front and back text.");
            return true;
        }

        try {
            Deck deck = programState.getDeck(deckName);
            deck.addNewCard(new FrontBackCard(front, back));

            LogsCenter.getLogger(CreateCommand.class).info("DECK_CREATE_REG_CARD: Card added to " + deckName);

        } catch (DeckNotFoundException d) {
            LogsCenter.getLogger(CreateCommand.class).warning(
                    "DECK_CREATE_REG_CARD: Deck not found - " + deckName);
            Gui.showError(d.getMessage());
        }
        return true;
    }

    /**
     *
     * @param commandInput
     * @return
     */
    private boolean parseInputOneShot(String commandInput) {
        String userInput = commandInput.replaceFirst("create deck/", "");

        boolean hasFront = userInput.contains("front/");
        boolean hasBack = userInput.contains("back/");

        if (hasBack && hasFront) {
            String[] userInputFields = userInput.trim().split(" front/");

            String deckName = userInputFields[0];

            String[] userCardFields = userInputFields[1].trim().split(" back/");

            front = userCardFields[0];
            back = userCardFields[1];
            return true;
        } else {
            return false;
        }
    }
}

