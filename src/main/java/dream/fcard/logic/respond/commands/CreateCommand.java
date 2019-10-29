package dream.fcard.logic.respond.commands;

/**
 * Represents a command that creates a new deck or card (?).
 */
public class CreateCommand {
//public class CreateCommand implements ResponseFunc {
    //private String deckName;
    //private String front;
    //private String back;
    //private ArrayList<String> choices;
    //
    //private boolean hasFront;
    //private boolean hasBack;
    //private boolean hasChoice;
    //
    ///**
    // *
    // * @param commandInput string
    // * @param programState state object
    // * @return
    // * @throws DeckNotFoundException
    // */
    //@Override
    //public boolean funcCall(String commandInput, State programState) {
    //
    //    System.out.println(commandInput);
    //
    //    hasFront = hasFront(commandInput);
    //    hasBack = hasBack(commandInput);
    //    hasChoice = hasChoice(commandInput);
    //
    //    boolean success;
    //    if (!hasChoice) {
    //        success = parseInputOneShot(commandInput);
    //    } else {
    //        success = parseInputWithChoice(commandInput);
    //    }
    //
    //    if (!success) {
    //        Gui.showError("MCQ card creation failed.");
    //        return true;
    //    }
    //
    //    try {
    //        Deck deck = programState.getDeck(deckName);
    //
    //        if (!hasChoice) {
    //            deck.addNewCard(new FrontBackCard(front, back));
    //        } else {
    //            deck.addNewCard(new MultipleChoiceCard(front, back, choices));
    //        }
    //
    //        LogsCenter.getLogger(CreateCommand.class).info("DECK_CREATE_REG_CARD: Card added to " + deckName);
    //
    //    } catch (DeckNotFoundException d) {
    //        LogsCenter.getLogger(CreateCommand.class).warning(
    //                "DECK_CREATE_REG_CARD: Deck not found - " + deckName);
    //        Gui.showError(d.getMessage());
    //    } catch (DuplicateInChoicesException e) {
    //        Gui.showError("Duplicate found in choices.");
    //    } catch (IndexNotFoundException e) {
    //        Gui.showError("Answer not valid.");
    //    } catch (NumberFormatException n) {
    //        Gui.showError("Answer not valid.");
    //    }
    //    return true;
    //}
    //
    ///**
    // *
    // *
    // * @param commandInput
    // * @return
    // */
    //public boolean funcCall() throws DeckAlreadyExistsException {
    //    String deckName = input.split("deck/")[1].strip();
    //    //if (deckAlreadyExists(deckName)) {
    //    //    throw new DeckAlreadyExistsException(deckName);
    //    //} else {
    //    //    Deck newDeck = new Deck(deckName);
    //    //    Scanner sc = new Scanner(System.in);
    //    //
    //    //
    //    //    if (!progState.isCreateMode()) {
    //    //        progState.toggleCreateMode();
    //    //    }
    //
    //    //    //GUI.printCreateMode("Let’s begin creating the deck ‘" + deckName
    //    //    // + "’. When done, simply type ‘quit’!");
    //    //    System.out.println("Let’s begin creating the deck ‘" + deckName
    //    //            + "’. When done, simply type ‘quit’!");
    //    //    while (sc.hasNextLine()) {
    //    //        // GUI.printCreateMode("Type the front of card #" + (newDeck.getNumCards() + 1));
    //    //        System.out.println("Type the front of card #" + (newDeck.getNumCards() + 1));
    //    //        String frontOfCard;
    //    //        String backOfCard;
    //    //        if (sc.nextLine().matches("(?i)^(quit)?.")) {
    //    //            //GUI.printCreateMode("You have created the deck ‘" + deckName + "’ with a total of "
    //    //            //        + newDeck.getNumCards() + " cards!\n");
    //    //            System.out.println("You have created the deck ‘" + deckName + "’ with a total of "
    //    //                    + newDeck.getNumCards() + " cards!\n");
    //    //            break;
    //    //        } else {
    //    //            frontOfCard = sc.nextLine();
    //    //        }
    //    //
    //    //        // GUI.printCreateMode("Type the back of card #" + (newDeck.getNumCards() + 1));
    //    //        System.out.println("Type the back of card #" + (newDeck.getNumCards() + 1));
    //    //        if (sc.nextLine().matches("(?i)^(quit)?.")) {
    //    //            //GUI.printCreateMode("You have created the deck ‘" + deckName + "’ with a total of "
    //    //            //        + newDeck.getNumCards() + " cards!\n");
    //    //            System.out.println("You have created the deck ‘" + deckName + "’ with a total of "
    //    //                    + newDeck.getNumCards() + " cards!\n");
    //    //            break;
    //    //        } else {
    //    //            backOfCard = sc.nextLine();
    //    //            FlashCard newCard = new FrontBackCard(frontOfCard, backOfCard);
    //    //            newDeck.addNewCard(newCard);
    //    //        }
    //    //    }
    //    //    progState.toggleCreateMode();
    //    //    return true;
    //    //}
    //    return false;
    //}
    //
    ///**
    // *
    // * @param input
    // * @return
    // */
    //private boolean hasFront(String input) {
    //    return input.contains("front/");
    //}
    //
    ///**
    // *
    // * @param input
    // * @return
    // */
    //private boolean hasBack(String input) {
    //    return input.contains("back/");
    //}
    //
    ///**
    // *
    // * @param input
    // * @return
    // */
    //private boolean hasChoice(String input) {
    //    return input.contains("choice/");
    //}
}

