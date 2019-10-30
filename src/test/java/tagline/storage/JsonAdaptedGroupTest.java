//@@author e0031374
package tagline.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tagline.storage.group.JsonAdaptedGroup.MISSING_FIELD_MESSAGE_FORMAT;
import static tagline.testutil.Assert.assertThrows;
import static tagline.testutil.TypicalGroups.HYDRA;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import tagline.commons.exceptions.IllegalValueException;
import tagline.model.group.GroupDescription;
import tagline.model.group.GroupName;
import tagline.storage.group.JsonAdaptedGroup;
import tagline.storage.group.JsonAdaptedMemberId;

public class JsonAdaptedGroupTest {
    private static final String INVALID_GROUPNAME = ""; // empty string is invalid
    // there are no invalid groupdescriptions private static final String INVALID_GROUPDESCRIPTION = "+651234";
    private static final String INVALID_CONTACTID = "9a444";

    private static final String VALID_GROUPNAME = HYDRA.getGroupName().toString();
    private static final String VALID_GROUPDESCRIPTION = HYDRA.getGroupDescription().toString();
    private static final List<JsonAdaptedMemberId> VALID_CONTACTIDS = HYDRA.getMemberIds().stream()
            .map(JsonAdaptedMemberId::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validGroupDetails_returnsGroup() throws Exception {
        JsonAdaptedGroup group = new JsonAdaptedGroup(HYDRA);
        assertEquals(HYDRA, group.toModelType());
    }

    @Test
    public void toModelType_invalidGroupName_throwsIllegalValueException() {
        JsonAdaptedGroup group =
                new JsonAdaptedGroup(INVALID_GROUPNAME, VALID_GROUPDESCRIPTION, VALID_CONTACTIDS);
        String expectedMessage = GroupName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, group::toModelType);
    }

    @Test
    public void toModelType_nullGroupName_throwsIllegalValueException() {
        JsonAdaptedGroup group = new JsonAdaptedGroup(null, VALID_GROUPDESCRIPTION,
                VALID_CONTACTIDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, GroupName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, group::toModelType);
    }

    // There are no invalid group descriptions
    //@Test
    //public void toModelType_invalidGroupDescription_throwsIllegalValueException() {
    //    JsonAdaptedGroup group =
    //            new JsonAdaptedGroup(VALID_GROUPNAME, INVALID_GROUPDESCRIPTION, VALID_CONTACTIDS);
    //    String expectedMessage = GroupDescription.MESSAGE_CONSTRAINTS;
    //    assertThrows(IllegalValueException.class, expectedMessage, group::toModelType);
    //}

    @Test
    public void toModelType_nullGroupDescription_throwsIllegalValueException() {
        JsonAdaptedGroup group = new JsonAdaptedGroup(VALID_GROUPNAME, null,
                VALID_CONTACTIDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, GroupDescription.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, group::toModelType);
    }

    @Test
    public void toModelType_invalidContactIds_throwsIllegalValueException() {
        List<JsonAdaptedMemberId> invalidContactIds = new ArrayList<>(VALID_CONTACTIDS);
        invalidContactIds.add(new JsonAdaptedMemberId(INVALID_CONTACTID));
        JsonAdaptedGroup person =
                new JsonAdaptedGroup(VALID_GROUPNAME, VALID_GROUPDESCRIPTION, invalidContactIds);
        assertThrows(IllegalValueException.class, person::toModelType);
    }
}
