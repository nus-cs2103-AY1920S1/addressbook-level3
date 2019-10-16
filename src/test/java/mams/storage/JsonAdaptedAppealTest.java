package mams.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mams.commons.exceptions.IllegalValueException;
import mams.model.appeal.Appeal;

import mams.testutil.Assert;
import mams.testutil.TypicalAppeals;

public class JsonAdaptedAppealTest {
    private static final String INVALID_APPEAL_ID = "C!22222";
    private static final String INVALID_APPEAL_TYPE = "addmod";
    private static final String INVALID_ACADEMIC_YEAR = "2019";
    private static final int INVALID_WORKLOAD = 0;
    private static final String INVALID_MATRICID = " ";

    private static final String VALID_APPEAL_ID = TypicalAppeals.APPEAL2.getAppealId();
    private static final String VALID_APPEAL_TYPE = TypicalAppeals.APPEAL2.getAppealType();
    private static final String VALID_STUDENT_ID = TypicalAppeals.APPEAL2.getStudentId();
    private static final String VALID_ACADEMIC_YEAR = TypicalAppeals.APPEAL2.getAcademicYear();
    private static final int VALID_WORKLOAD = TypicalAppeals.APPEAL2.getStudentWorkload();
    private static final String VALID_DESCRIPTION = TypicalAppeals.APPEAL2.getAppealDescription();
    private static final String VALID_MODULES = TypicalAppeals.APPEAL2.getModule_to_drop();
    private static final boolean VALID_IS_RESOLVED = TypicalAppeals.APPEAL2.isResolved();
    private static final String VALID_REMARK = TypicalAppeals.APPEAL2.getRemark();

    @Test
    public void toModelType_validAppealDetails_returnsAppeal() throws Exception {
        JsonAdaptedAppeal appeal = new JsonAdaptedAppeal(TypicalAppeals.APPEAL2);
        Assertions.assertEquals(TypicalAppeals.APPEAL2, appeal.toModelType());
    }

    @Test
    public void toModelType_invalidAppealId_throwsIllegalValueException() {
        JsonAdaptedAppeal appeal =
                new JsonAdaptedAppeal(INVALID_APPEAL_ID, VALID_APPEAL_TYPE,
                        VALID_STUDENT_ID, VALID_ACADEMIC_YEAR,
                        VALID_WORKLOAD, VALID_DESCRIPTION,
                        VALID_MODULES, VALID_MODULES,
                        VALID_MODULES, VALID_MODULES,
                        false, "pending",
                        VALID_REMARK);
        String expectedMessage = Appeal.MESSAGE_CONSTRAINTS_APPEAL_ID;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appeal::toModelType);
    }

    @Test
    public void toModelType_nullID_throwsIllegalValueException() {
        JsonAdaptedAppeal appeal =
                new JsonAdaptedAppeal(null, VALID_APPEAL_TYPE,
                        VALID_STUDENT_ID, VALID_ACADEMIC_YEAR,
                        VALID_WORKLOAD, VALID_DESCRIPTION,
                        VALID_MODULES, VALID_MODULES,
                        VALID_MODULES, VALID_MODULES,
                        false, "pending",
                        VALID_REMARK);
        String expectedMessage = String.format(JsonAdaptedAppeal.MISSING_FIELD_MESSAGE_FORMAT,
                "appealId");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appeal::toModelType);
    }


}
