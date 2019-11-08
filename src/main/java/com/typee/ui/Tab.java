package com.typee.ui;

import javafx.scene.layout.Region;

/**
 * Tab component for Typee Ui.
 */
public class Tab {
    private String name;
    private String url;
    private UiPart<Region> controller;

    public Tab() {
    }

    public Tab(String name) {
        this.name = name;
    }

    public Tab(String name, String url, UiPart<Region> controller) {
        this.name = name;
        this.url = url;
        this.controller = controller;
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

    public UiPart<Region> getController() {
        return controller;
    }

    public void setController(UiPart<Region> controller) {
        this.controller = controller;
    }

    @Override
    public String toString() {
        return name + ":(" + url + ") controller: " + controller;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof Tab)) {
            return false;
        } else {
            Tab otherTab = (Tab) other;
            return name.equals(otherTab.name)
                    && url.equals(otherTab.url)
                    && controller.equals(otherTab.controller);
        }
    }
}
