package seedu.address.model.mapping;

/**
 * Represents the Role of the person to the group.
 */
public class Role {
    private String role;

    public Role(String role) {
        this.role = role;
    }

    private Role() {
        this.role = "";
    }

    public static Role emptyRole() {
        return new Role();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Checks if this role is equals to another.
     *
     * @param otherRole to be checked
     * @return boolean
     */
    public boolean equals(Role otherRole) {
        if (role.equals(otherRole.getRole())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return role;
    }
}
