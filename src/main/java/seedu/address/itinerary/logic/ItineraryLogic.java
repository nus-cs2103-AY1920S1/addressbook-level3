package seedu.address.itinerary.logic;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.itinerary.logic.parser.ItineraryParser;
import seedu.address.itinerary.model.Itinerary;
import seedu.address.itinerary.model.Model;
import seedu.address.itinerary.model.ReadOnlyItinerary;
import seedu.address.itinerary.model.event.Event;
import seedu.address.itinerary.storage.ItineraryStorage;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The main logic for the itinerary class.
 */
public class ItineraryLogic {

    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(ItineraryLogic.class);

    private final Model model;
    private final ItineraryStorage itineraryStorage;
    private final ItineraryParser itineraryParser;

    public ItineraryLogic(Model itineraryModel, ItineraryStorage storage) {
        this.model = itineraryModel;
        this.itineraryStorage = storage;
        this.itineraryParser = new ItineraryParser();
        Itinerary itinerary = new Itinerary();

        try {
            Optional<ReadOnlyItinerary> itineraryOptional = itineraryStorage.readItinerary();
            if (itineraryOptional.isPresent()) {
                itinerary.updateItinerary(itineraryOptional.get());
            } else {
                System.out.println("Starting without a json file!");
            }
        } catch (DataConversionException e) {
            System.out.println("Data file not in the correct format. Will be starting with an empty Itinerary");
            // todo: what to do about data? JUST OVERWRITE
        } catch (IOException e) {
            System.out.println("Problem while reading from the file. Will be starting with an empty Itinerary");
            // todo: what to do about data? JUST OVERWRITE
        }

        model.setItinerary(itinerary);
    }

    /**
     * Executes the command given by the user.
     * @param commandText the input given by the user.
     * @return the command result from the evaluation of the user input.
     * @throws CommandException when the command given is invalid.
     * @throws ParseException when the input and output is invalid and does not pass the parse test.
     */
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = itineraryParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            itineraryStorage.saveItinerary(model.getItinerary());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    /**
     * Returns an observe only expense list with current country specified in financial tracker.
     */
    public ObservableList<Event> getEventList() {
        return model.getSortedEventList();
    }
}
