package seedu.address.logic.commands.student;

import java.util.HashMap;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;

/**
 * Represents a student edit command.
 */
public class StudentEditCommand extends StudentCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new student\n"
            + "Parameters:\n"
            + "name/ [NAME]\n"
            + "Example: name/ Jeong Sock Hwee\n\n";

    private final Index index;
    private final String name;

    /**
     * Creates a StudentEditCommand object.
     *
     * @param fields to edit.
     */
    public StudentEditCommand(Index index, HashMap<String, String> fields) {
        this.index = index;
        this.name = fields.get("name");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
//        Student studentObj = model.getStudent(index);
//        String studentName = (!this.name.isBlank()) ? this.name : studentObj.getName().toString();
//        studentObj.setName(studentName);
//        model.setStudent(index, studentObj);
        return null; //new CommandResult(generateSuccessMessage(studentObj));
    }

    /**
     * Generates a command execution success message.
     *
     * @param student that has been added.
     */
    private String generateSuccessMessage(Student student) {
        return "Edited student: " + student;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentEditCommand)) {
            return false;
        }

        // state check
        StudentEditCommand e = (StudentEditCommand) other;
        return name.equals(e.name);
    }
}
