package dream.fcard.logic.respond.commands;

import dream.fcard.core.commons.core.LogsCenter;
import dream.fcard.gui.Gui;
import dream.fcard.logic.respond.ResponseFunc;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.exceptions.DeckNotFoundException;
import dream.fcard.model.exceptions.IndexNotFoundException;

enum ACTION{
    EDIT, REMOVE
}

/**
 *
 */
public class EditCommand implements ResponseFunc {

    private String deckName;
    private ACTION action;
    private int index;
    private String front;
    private String back;
    private boolean hasFront;
    private boolean hasBack;

    /**
     *
     */
    public EditCommand() {
    }

    /**
     *
     * @param commandInput string
     * @param programState state object
     * @return
     * @throws DeckNotFoundException
     */
    @Override
    public boolean funcCall(String commandInput, State programState) throws DeckNotFoundException, IndexNotFoundException {

        boolean success = parseInput(commandInput);

        if (!success) {
            return true;
        }
        System.out.println(index);

        boolean hasDeck = programState.hasDeck(deckName);
        if (!hasDeck) {
            throw new DeckNotFoundException("Deck does not exist - " + deckName);
        }

        Deck deck = programState.getDeck(deckName);
        //System.out.println("Deck obtained");

        if (action == ACTION.REMOVE) {
            performRemove(deck);
        }

        try {
            if (action == ACTION.EDIT) {
                performEdit(deck);
            }
        } catch (IndexNotFoundException i) {
            throw new IndexNotFoundException(i.getMessage());
        }

        return true;
    }

    /**
     *
     *
     * @param commandInput
     */
    private boolean parseInput(String commandInput) {
        String userFields = commandInput.replaceFirst("edit(\\s)+deck/", "");
        String[] splitUserFields = userFields.split(" action/");

        deckName = splitUserFields[0].trim();

        splitUserFields = splitUserFields[1].split(" index/");
        String inputAction = splitUserFields[0].trim();

        hasFront = splitUserFields[1].contains("front/");
        hasBack = splitUserFields[1].contains("back/");

        String stringIndex = "";
        front = "";
        back = "";

        if (inputAction.toLowerCase().equals("edit")) {
            action = ACTION.EDIT;
        } else if (inputAction.toLowerCase().equals("remove")) {
            action = ACTION.REMOVE;
        }
        if (hasFront && hasBack) {
            splitUserFields = splitUserFields[1].split(" front/");
            stringIndex = splitUserFields[0].trim();

            splitUserFields = splitUserFields[1].split(" back/");
            front = splitUserFields[0].trim();
            back = splitUserFields[1].trim();
        }

        if (hasFront && !hasBack) {
            splitUserFields = splitUserFields[1].split(" front/");
            stringIndex = splitUserFields[0].trim();
            front = splitUserFields[1].trim();
        }

        if (!hasFront && hasBack) {
            splitUserFields = splitUserFields[1].split(" back/");
            stringIndex = splitUserFields[0].trim();
            back = splitUserFields[1].trim();
        }

        if (!hasBack && !hasFront && action == ACTION.REMOVE) {
            stringIndex = splitUserFields[1].trim();
        }

        if (!hasBack && !hasFront && action == ACTION.EDIT) {
            LogsCenter.getLogger(EditCommand.class).warning("EDIT_DECK_EDIT_CARD: No changes to front back");
            Gui.showError("No back and front provided.");
            return false;
        }

        try {
            index = Integer.parseInt(stringIndex);
        } catch (NumberFormatException n) {
            throw new NumberFormatException("Index provided is a invalid number - " + stringIndex);
        }
        return true;
    }

    /**
     *
     * @param deck
     */
    private void performEdit(Deck deck) throws IndexNotFoundException {
        if (hasFront) {
            deck.editFrontCardFromDeck(front, index);
            //System.out.println("Edit front card successsfullly");
        }

        if (hasBack) {
            deck.editBackCardInDeck(back, index);
            //System.out.println("Edit back card successsfullly");
        }
    }

    private void performRemove(Deck deck) {
        try {
            deck.removeCardFromDeck(index);

        } catch (NumberFormatException | IndexNotFoundException n) {
            LogsCenter.getLogger(EditCommand.class).info("EDIT_DECK_REMOVE_CARD: Invalid index provided" + index);
            System.out.println(n.getMessage());
        }
    }
}
