package seedu.address.logic.commands.student;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import java.util.HashMap;

public class StudentEditCommand extends StudentCommand{

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new student\n"
            + "Parameters:\n"
            + "name/ [NAME]\n"
            + "Example: name/ Jeong Sock Hwee\n\n";

    private final Student student;
    private final String name;

    /**
     * Creates a QuestionEditCommand object.
     *
     * @param fields to edit.
     */
    public StudentEditCommand(Student student, HashMap<String, String> fields) {
        this.student = student;
        this.name = fields.get("name");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Student studentObj = model.getStudent(student);

        String studentName = (!this.name.isBlank()) ? this.name : studentObj.getName().toString();

        studentObj.setName(studentName);

        model.setStudent(index, studentName);
        return new CommandResult(generateSuccessMessage(studentObj));
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
