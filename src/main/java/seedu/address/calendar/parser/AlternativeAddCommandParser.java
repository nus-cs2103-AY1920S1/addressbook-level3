package seedu.address.calendar.parser;

import seedu.address.calendar.commands.AddCommand;
import seedu.address.calendar.commands.AddCommitmentIgnoreCommand;
import seedu.address.calendar.commands.AddTripIgnoreCommand;
import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.event.Commitment;
import seedu.address.calendar.model.event.EventType;
import seedu.address.calendar.model.event.Name;
import seedu.address.calendar.model.event.Trip;

public class AlternativeAddCommandParser extends AddCommandParser {
    @Override
    AddCommand parse(EventType eventType, Name name, Date startDate, Date endDate) {
        switch (eventType) {
        case COMMITMENT:
            Commitment commitment = new Commitment(name, startDate, endDate);
            return new AddCommitmentIgnoreCommand(commitment);
        default:
            assert eventType.equals(EventType.TRIP) : "There are only 2 valid types of add commands that "
                    + "detects clashes.";
            Trip trip = new Trip(name, startDate, endDate);
            return new AddTripIgnoreCommand(trip);
        }
    }
}
