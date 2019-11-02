package seedu.address.calendar.parser;

import seedu.address.calendar.commands.AddCommand;
import seedu.address.calendar.commands.AddCommitmentIgnoreCommand;
import seedu.address.calendar.commands.AddTripIgnoreCommand;
import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.event.Commitment;
import seedu.address.calendar.model.event.EventType;
import seedu.address.calendar.model.event.Info;
import seedu.address.calendar.model.event.Name;
import seedu.address.calendar.model.event.Trip;

import java.util.Optional;

public class AddCommandAlternativeParser extends AddCommandParser {
    @Override
    AddCommand parse(EventType eventType, Name name, Date startDate, Date endDate, Optional<Info> info) {
        switch (eventType) {
        case COMMITMENT:
            Commitment commitment = new Commitment(name, startDate, endDate, info);
            return new AddCommitmentIgnoreCommand(commitment);
        default:
            assert eventType.equals(EventType.TRIP) : "There are only 4 valid types of add commands.";
            Trip trip = new Trip(name, startDate, endDate, info);
            return new AddTripIgnoreCommand(trip);
        }
    }
}
