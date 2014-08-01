package com.gkorenevsky.exercise;

import java.util.List;

/**
 * Created by engineer on 7/11/14.
 */
public class CommandProcessor {

    ComponentManager cm;

    public CommandProcessor(ComponentManager cm) {
        this.cm = cm;
    }

    public void processDepend(String componentName, List<String> dependencies) {
        cm.addDependency(componentName, dependencies);
    }

    public void processInstall(String componentName) {
        cm.installComponent(componentName);
    }

    public void processRemove(String componentName) {
        cm.removeComponent(componentName);
    }

    public void processList() {
        cm.listComponents();
    }
}
