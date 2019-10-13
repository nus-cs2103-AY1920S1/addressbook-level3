package com.typee.ui;

/**
 * Tab component for Typee Ui.
 */
public class Tab {
    private String name;
    private String url;

    public Tab() {
    }

    public Tab(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return name + ":(" + url + ")";
    }
}
