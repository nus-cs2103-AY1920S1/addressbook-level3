package seedu.address.logic.commands.itinerary.days.edit;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.model.Model;
import seedu.address.model.itinerary.Description;
import seedu.address.model.itinerary.Expenditure;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.day.Day;
import seedu.address.model.itinerary.event.EventList;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.isAllPresent;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;

public class EditDayFieldCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of your form "
            + "by the index of the form field as displayed, or by the various prefixes of the fields. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DATE_START + "START DATE] "
            + "[" + PREFIX_DATE_END + "END DATE] "
            + "[" + PREFIX_LOCATION + "DESTINATION] "
            + "[" + PREFIX_BUDGET + "TOTAL BUDGET] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]...\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_DESCRIPTION + " First Day"
            + PREFIX_BUDGET + "3000";

    public static final String MESSAGE_NOT_EDITED = "At least one field to must be provided!";
    public static final String MESSAGE_EDIT_SUCCESS = "Edited the current form:%1$s";

    private final EditDayFieldCommand.EditDayDescriptor editDayDescriptor;

    /**
     * @param editDayDescriptor details to edit the person with
     */
    public EditDayFieldCommand(EditDayDescriptor editDayDescriptor) {
        requireNonNull(editDayDescriptor);

        this.editDayDescriptor = editDayDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EditDayDescriptor currentDescriptor = model.getPageStatus().getEditDayDescriptor();
        EditDayDescriptor newEditDayDescriptor = currentDescriptor == null
                ? new EditDayDescriptor(editDayDescriptor)
                : new EditDayDescriptor(currentDescriptor, editDayDescriptor);

        model.setPageStatus(
                model.getPageStatus().withNewEditDayDescriptor(newEditDayDescriptor));

        return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, editDayDescriptor));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditDayFieldCommand)) {
            return false;
        }

        // state check
        EditDayFieldCommand e = (EditDayFieldCommand) other;
        return editDayDescriptor.equals(e.editDayDescriptor);
    }

    /**
     * Stores the details to edit the day with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditDayDescriptor {
        private Optional<Name> name;
        private Optional<LocalDateTime> startDate;
        private Optional<LocalDateTime> endDate;
        private Optional<Location> destination;
        private Optional<Expenditure> totalBudget;
        private Optional<Description> description;

        public EditDayDescriptor() {
            name = Optional.empty();
            startDate = Optional.empty();
            endDate = Optional.empty();
            destination = Optional.empty();
            totalBudget = Optional.empty();
            description = Optional.empty();
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDayDescriptor(EditDayFieldCommand.EditDayDescriptor toCopy) {
            name = toCopy.getName();
            startDate = toCopy.getStartDate();
            endDate = toCopy.getEndDate();
            destination = toCopy.getDestination();
            totalBudget = toCopy.getBudget();
            description = toCopy.getDescription();
        }


        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDayDescriptor(Day toCopy) {
            setName(toCopy.getName());
            setStartDate(toCopy.getStartDate());
            setEndDate(toCopy.getEndDate());
            setDestination(toCopy.getDestination());
            setBudget(toCopy.getTotalBudget());
            setDescription(toCopy.getDescription());
        }


        /**
         * Overwrite constructor.
         * Constructs a new {@code EditEventDescriptor} using an {@code oldDescriptor}, overwritten with
         * values of the {@code newDescriptor} where they exist.
         *
         * @param oldDescriptor Old {@code EditEventDescriptor} to use.
         * @param newDescriptor New {@code EditEventDescriptor} to use.
         */
        public EditDayDescriptor(EditDayFieldCommand.EditDayDescriptor oldDescriptor, EditDayFieldCommand.EditDayDescriptor newDescriptor) {
            this();
            newDescriptor.name.ifPresentOrElse(this::setName,
                    () -> oldDescriptor.name.ifPresent(this::setName));

            newDescriptor.startDate.ifPresentOrElse(this::setStartDate,
                    () -> oldDescriptor.startDate.ifPresent(this::setStartDate));

            newDescriptor.endDate.ifPresentOrElse(this::setEndDate,
                    () -> oldDescriptor.endDate.ifPresent(this::setEndDate));

            newDescriptor.destination.ifPresentOrElse(this::setDestination,
                    () -> oldDescriptor.destination.ifPresent(this::setDestination));

            newDescriptor.totalBudget.ifPresentOrElse(this::setBudget,
                    () -> oldDescriptor.totalBudget.ifPresent(this::setBudget));

            newDescriptor.description.ifPresentOrElse(this::setDescription,
                    () -> oldDescriptor.description.ifPresent(this::setDescription));
        }


        /**
         * Builds a new {@code Day} instance.
         * Requires name, startDate, destination to have been set minimally.
         * Uses the Optional constructor for day to accommodate missing optional fields.
         *
         * @return New {@code Day} created.
         * @throws NullPointerException If any of the fields are empty.
         */
        public Day buildDay() {
            if (isAllPresent(name, startDate, endDate, destination)) {
                return new Day(name.get(), startDate.get(), endDate.get(), description,
                        destination.get(), totalBudget, new EventList());
            } else {
                throw new NullPointerException();
            }
        }

        /**
         * Builds an edited {@code Day} instance from this {@code EditEventDescriptor}.
         * Uses the original day information first, overwriting where the values exist.
         *
         * @param day Source {@code Day} instance.
         * @return Edited {@code Day} instance.
         */
        public Day buildDay(Day day) {
            Name dayName = day.getName();
            LocalDateTime startDate = day.getStartDate();
            LocalDateTime endDate = day.getEndDate();
            Location destination = day.getDestination();
            Optional<Expenditure> budget = day.getTotalBudget();
            Optional<Description> description = day.getDescription();
            if (this.name.isPresent()) {
                dayName = this.name.get();
            }
            if (this.startDate.isPresent()) {
                startDate = this.startDate.get();
            }
            if (this.endDate.isPresent()) {
                endDate = this.endDate.get();
            }
            if (this.destination.isPresent()) {
                destination = this.destination.get();
            }
            if (this.totalBudget.isPresent()) {
                budget = this.totalBudget;
            }
            if (this.description.isPresent()) {
                description = this.description;
            }

            return new Day(dayName, startDate, endDate, description, destination, budget, day.getEventList());
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(name, startDate, endDate, destination, totalBudget, description);
        }



        public void setName(Name name) {
            this.name = Optional.of(name);
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setStartDate(LocalDateTime startDate) {
            this.startDate = Optional.of(startDate);
        }

        public Optional<LocalDateTime> getStartDate() {
            return startDate;
        }

        public void setEndDate(LocalDateTime endDate) {
            this.endDate = Optional.of(endDate);
        }

        public Optional<LocalDateTime> getEndDate() {
            //end date is optional, but return null to conform with the rest.
            return endDate;
        }

        public void setDestination(Location destination) {
            this.destination = Optional.of(destination);
        }

        public Optional<Location> getDestination() {
            return destination;
        }

        private void setDescription(Description description) {
            this.description = Optional.of(description);
        }

        public void setBudget(Expenditure totalBudget) {
            this.totalBudget = Optional.of(totalBudget);
        }

        // Support optional fields from Day
        public void setBudget(Optional<Expenditure> totalBudget) {
            this.totalBudget = totalBudget;
        }

        private void setDescription(Optional<Description> description) {
            this.description = description;
        }

        public Optional<Expenditure> getBudget() {
            return totalBudget;
        }
        public Optional<Description> getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCommand.EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditDayFieldCommand.EditDayDescriptor e = (EditDayFieldCommand.EditDayDescriptor) other;

            return getName().equals(e.getName())
                    && getStartDate().equals(e.getStartDate())
                    && getEndDate().equals(e.getEndDate())
                    && getDestination().equals(e.getDestination())
                    && getBudget().equals(e.getBudget())
                    && getDescription().equals(e.getDescription());
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();

            this.name.ifPresent(name -> builder.append(" Name of day: ").append(name));
            this.startDate.ifPresent(startDate ->
                    builder.append(" Start date: ").append(ParserDateUtil.getDisplayTime(startDate)));
            this.endDate.ifPresent(endDate ->
                    builder.append(" End date: ").append(ParserDateUtil.getDisplayTime(endDate)));
            this.destination.ifPresent(destination -> builder.append(" Destination: ").append(destination));
            this.totalBudget.ifPresent(totalBudget -> builder.append(" Total Budget: ").append(totalBudget));
            this.description.ifPresent(description -> builder.append(" Description: ").append(description));

            return builder.toString();
        }
    }

}
