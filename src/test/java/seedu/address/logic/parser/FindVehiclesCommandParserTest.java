package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_VEHICLE_NUMBER;
import static seedu.address.commons.core.Messages.MESSAGE_NO_SUCH_VTYPE;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindVehiclesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.DistrictKeywordsPredicate;
import seedu.address.model.vehicle.VNumKeywordsPredicate;
import seedu.address.model.vehicle.VTypeKeywordsPredicate;
import seedu.address.model.vehicle.VehicleNumber;
import seedu.address.model.vehicle.VehicleType;

public class FindVehiclesCommandParserTest {

    @Test
    public void parse_validD_findVCommandCreated() {
        try {
            FindVehiclesCommand fc = new FindVehiclesCommandParser().parse("find-v dist/1 2 3");
            List<District> districts = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                districts.add(new District(i + 1));
            }
            FindVehiclesCommand ec = new FindVehiclesCommand(new DistrictKeywordsPredicate(districts));
            assertEquals(fc, ec);
        } catch (ParseException e) {
            assert(false);
        }
    }

    @Test
    public void parse_invalidD_exceptionThrown() {
        try {
            FindVehiclesCommand fc = new FindVehiclesCommandParser().parse("find-v dist/200");
        } catch (ParseException e) {
            assertEquals(e.getErrorMessage(), District.MESSAGE_CONSTRAINTS);
        }
    }

    @Test
    public void parse_validVType_findVCommandCreated() {
        try {
            FindVehiclesCommand fc = new FindVehiclesCommandParser().parse("find-v vtype/ambULancE");
            FindVehiclesCommand ec = new FindVehiclesCommand(new VTypeKeywordsPredicate(new VehicleType("Ambulance")));
            assertEquals(fc, ec);
        } catch (ParseException e) {
            assert(false);
        }
    }

    @Test
    public void parse_invalidVType_exceptionThrown() {
        try {
            FindVehiclesCommand fc = new FindVehiclesCommandParser().parse("find-v vtype/amulance");
        } catch (ParseException e) {
            assertEquals(e.getErrorMessage(), MESSAGE_NO_SUCH_VTYPE);
        }
    }

    @Test
    public void parse_validVNum_findVCommandCreated() {
        try {
            FindVehiclesCommand fc = new FindVehiclesCommandParser().parse("find-v vnum/ABC1234D");
            FindVehiclesCommand ec = new FindVehiclesCommand(new VNumKeywordsPredicate(new VehicleNumber("ABC1234D")));
            assertEquals(fc, ec);
        } catch (ParseException e) {
            assert(false);
        }
    }

    @Test
    public void parse_invalidVNum_exceptionThrown() {
        try {
            FindVehiclesCommand fc = new FindVehiclesCommandParser().parse("find-v vnum/Ad232rfew");
        } catch (ParseException e) {
            assertEquals(e.getErrorMessage(), MESSAGE_INVALID_VEHICLE_NUMBER);
        }
    }
}
