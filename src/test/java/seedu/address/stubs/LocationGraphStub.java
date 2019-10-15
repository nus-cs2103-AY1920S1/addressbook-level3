package seedu.address.stubs;

import java.net.ConnectException;

import seedu.address.commons.exceptions.TimeBookInvalidState;
import seedu.address.model.gmaps.LocationGraph;

/**
 * This is a stub for LocationGraph
 */
public class LocationGraphStub extends LocationGraph {
    public LocationGraphStub() throws TimeBookInvalidState, ConnectException {
        super(new ProcessVenuesStub());
    }
}
