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
}
