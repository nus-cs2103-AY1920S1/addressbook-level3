package seedu.address.logic.parser.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalWorkers.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.worker.Worker;
import seedu.address.testutil.WorkerBuilder;

//@@author ambervoong
class UpdateWorkerDescriptorTest {

    @Test
    void isAnyFieldEdited_fieldEdited_true() {
        UpdateWorkerDescriptor descriptor = new UpdateWorkerDescriptor();
        descriptor.setDesignation("Technician");
        assertTrue(descriptor.isAnyFieldEdited());
    }

    @Test
    void isAnyFieldEdited_fieldNotEdited_false() {
        UpdateWorkerDescriptor descriptor = new UpdateWorkerDescriptor();
        assertFalse(descriptor.isAnyFieldEdited());
    }

    @Test
    void apply_fieldsPresent_success() {
        Worker worker = new WorkerBuilder(ALICE).build();
        UpdateWorkerDescriptor descriptor = new UpdateWorkerDescriptor();
        descriptor.setSex(Sex.MALE);
        Worker workerCopy = new WorkerBuilder(ALICE).build();
        workerCopy.setSex(Sex.MALE);
        assertTrue(descriptor.apply(worker).equals(workerCopy));
    }

    @Test
    void apply_allFieldsNotPresent_success() {
        //  Success because apply does not check whether fields are present or not.
        Worker worker = new WorkerBuilder(ALICE).build();
        UpdateWorkerDescriptor descriptor = new UpdateWorkerDescriptor();
        Worker workerCopy = new WorkerBuilder(ALICE).build();
        assertTrue(descriptor.apply(worker).equals(workerCopy));
    }


    @Test
    void getSetPhone() {
        Worker worker = new WorkerBuilder().build();
        UpdateWorkerDescriptor descriptor = new UpdateWorkerDescriptor(worker);
        descriptor.setPhone(new PhoneNumber("98731567"));
        assertEquals(new PhoneNumber("98731567"), descriptor.getPhone().get());
    }

    @Test
    void getSetSex() {
        Worker worker = new WorkerBuilder().build();
        UpdateWorkerDescriptor descriptor = new UpdateWorkerDescriptor(worker);
        descriptor.setSex(Sex.FEMALE);
        assertEquals(Sex.FEMALE, descriptor.getSex().get());
    }

    @Test
    void getSetDateOfBirth() throws ParseException {
        Worker worker = new WorkerBuilder().build();
        UpdateWorkerDescriptor descriptor = new UpdateWorkerDescriptor(worker);
        descriptor.setDateOfBirth(ParserUtil.parseDate("06/11/1987"));
        assertEquals(ParserUtil.parseDate("06/11/1987"),
                descriptor.getDateOfBirth().get());
    }

    @Test
    void getSetDateJoined() throws ParseException {
        Worker worker = new WorkerBuilder().build();
        UpdateWorkerDescriptor descriptor = new UpdateWorkerDescriptor(worker);
        descriptor.setDateJoined(ParserUtil.parseDate("06/11/1987"));
        assertEquals(ParserUtil.parseDate("06/11/1987"), descriptor.getDateJoined().get());
    }

    @Test
    void getSetDesignation() {
        Worker worker = new WorkerBuilder().build();
        UpdateWorkerDescriptor descriptor = new UpdateWorkerDescriptor(worker);
        descriptor.setDesignation("manager");
        assertEquals("manager", descriptor.getDesignation().get());
    }

    @Test
    void getSetEmploymentStatus() {
        Worker worker = new WorkerBuilder().build();
        UpdateWorkerDescriptor descriptor = new UpdateWorkerDescriptor(worker);
        descriptor.setEmploymentStatus("Probation");
        assertEquals("Probation", descriptor.getEmploymentStatus().get());
    }

    @Test
    void equals() {
        Worker worker = new WorkerBuilder().build();
        UpdateWorkerDescriptor descriptor = new UpdateWorkerDescriptor(worker);
        UpdateWorkerDescriptor copyDescriptor = new UpdateWorkerDescriptor(worker);
        assertEquals(descriptor, copyDescriptor);
        assertEquals(descriptor.hashCode(), copyDescriptor.hashCode());
    }
}
