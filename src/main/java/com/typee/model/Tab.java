package com.typee.model;

/**
 * Tab component for Typee Ui.
 */
public class Tab {
    private String name;

    public Tab(String name) {
        this.name = name;
    }

    public Tab() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
