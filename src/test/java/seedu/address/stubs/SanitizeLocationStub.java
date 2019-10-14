package seedu.address.stubs;

import seedu.address.logic.internal.gmaps.SanitizeLocation;

public class SanitizeLocationStub extends SanitizeLocation {
    public SanitizeLocationStub(){
        super(new GmapsApiStub());
    }
}
