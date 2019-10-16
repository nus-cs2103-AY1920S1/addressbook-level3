package seedu.address.testutil;

import seedu.address.logic.commands.editcommand.EditTeamCommand.EditTeamDescriptor;
import seedu.address.model.entity.Location;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.ProjectType;
import seedu.address.model.entity.Score;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;

/**
 * A utility class to help with building {@code EditTeamDescriptor} objects.
 */
public class EditTeamDescriptorBuilder {

    private EditTeamDescriptor descriptor;

    public EditTeamDescriptorBuilder() {
        this.descriptor = new EditTeamDescriptor();
    }

    public EditTeamDescriptorBuilder(EditTeamDescriptor descriptor) {
        this.descriptor = new EditTeamDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTeamDescriptor} with fields containing {@code Team}'s details
     */
    public EditTeamDescriptorBuilder(Team team) {
        descriptor = new EditTeamDescriptor();
        descriptor.setName(team.getName());
        descriptor.setSubject(team.getSubject());
        descriptor.setScore(team.getScore());
        descriptor.setProjectName(team.getProjectName());
        descriptor.setProjectType(team.getProjectType());
        descriptor.setLocation(team.getLocation());
    }

    /**
     * Sets the {@code Name} of the {@code EditTeamDescriptor} that we are building.
     */
    public EditTeamDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code SubjectName} of the {@code EditTeamDescriptor} that we are building.
     */
    public EditTeamDescriptorBuilder withSubject(String subject) {
        descriptor.setSubject(SubjectName.valueOf(subject.toUpperCase()));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditTeamDescriptor} that we are building.
     */
    public EditTeamDescriptorBuilder withScore(int score) {
        descriptor.setScore(new Score(score));
        return this;
    }

    /**
     * Sets the {@code ProjectName} of the {@code EditTeamDescriptor} that we are building.
     */
    public EditTeamDescriptorBuilder withProjectName(String projectName) {
        descriptor.setProjectName(new Name(projectName));
        return this;
    }

    /**
     * Sets the {@code ProjectType} of the {@code EditTeamDescriptor} that we are building.
     */
    public EditTeamDescriptorBuilder withProjectType(String projectType) {
        descriptor.setProjectType(ProjectType.valueOf(projectType.toUpperCase()));
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code EditTeamDescriptor} that we are building.
     */
    public EditTeamDescriptorBuilder withLocation(int location) {
        descriptor.setLocation(new Location(location));
        return this;
    }

    /**
     * Constructor.
     */
    public EditTeamDescriptor build() {
        return descriptor;
    }

}
