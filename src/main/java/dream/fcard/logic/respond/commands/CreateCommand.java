package dream.fcard.logic.respond.commands;

import java.util.ArrayList;

import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.exceptions.DeckAlreadyExistsException;

/**
 * Represents a command that creates a new deck or card (?).
 */
public class CreateCommand extends Command {

    private String input;
    private State progState;

    public CreateCommand(String i, State s) {
        this.input = i;
        this.progState = s;
    }

    /**
     * Performs execution of create command.
     */
    public boolean funcCall() throws DeckAlreadyExistsException {
        String deckName = input.split("deck/")[1].strip();
        //if (deckAlreadyExists(deckName)) {
        //    throw new DeckAlreadyExistsException(deckName);
        //} else {
        //    Deck newDeck = new Deck(deckName);
        //    Scanner sc = new Scanner(System.in);
        //
        //
        //    if (!progState.isCreateMode()) {
        //        progState.toggleCreateMode();
        //    }

        //    //GUI.printCreateMode("Let’s begin creating the deck ‘" + deckName
        //    // + "’. When done, simply type ‘quit’!");
        //    System.out.println("Let’s begin creating the deck ‘" + deckName
        //            + "’. When done, simply type ‘quit’!");
        //    while (sc.hasNextLine()) {
        //        // GUI.printCreateMode("Type the front of card #" + (newDeck.getNumCards() + 1));
        //        System.out.println("Type the front of card #" + (newDeck.getNumCards() + 1));
        //        String frontOfCard;
        //        String backOfCard;
        //        if (sc.nextLine().matches("(?i)^(quit)?.")) {
        //            //GUI.printCreateMode("You have created the deck ‘" + deckName + "’ with a total of "
        //            //        + newDeck.getNumCards() + " cards!\n");
        //            System.out.println("You have created the deck ‘" + deckName + "’ with a total of "
        //                    + newDeck.getNumCards() + " cards!\n");
        //            break;
        //        } else {
        //            frontOfCard = sc.nextLine();
        //        }
        //
        //        // GUI.printCreateMode("Type the back of card #" + (newDeck.getNumCards() + 1));
        //        System.out.println("Type the back of card #" + (newDeck.getNumCards() + 1));
        //        if (sc.nextLine().matches("(?i)^(quit)?.")) {
        //            //GUI.printCreateMode("You have created the deck ‘" + deckName + "’ with a total of "
        //            //        + newDeck.getNumCards() + " cards!\n");
        //            System.out.println("You have created the deck ‘" + deckName + "’ with a total of "
        //                    + newDeck.getNumCards() + " cards!\n");
        //            break;
        //        } else {
        //            backOfCard = sc.nextLine();
        //            FlashCard newCard = new FrontBackCard(frontOfCard, backOfCard);
        //            newDeck.addNewCard(newCard);
        //        }
        //    }
        //    progState.toggleCreateMode();
        //    return true;
        //}
        return false;
    }

    /**
     * Checks if there is already a deck with the same name as the user input.
     *
     * @param deckName A String representing the name of the deck to be created.
     * @return A boolean of whether a deck with the same name already exists
     */
    private boolean deckAlreadyExists(String deckName) {
        ArrayList<Deck> allDecks = progState.getAllDecks();
        for (Deck curr : allDecks) {
            if (curr.getName().equals(deckName)) {
                return true;
            }
        }
        return false;
    }
}

