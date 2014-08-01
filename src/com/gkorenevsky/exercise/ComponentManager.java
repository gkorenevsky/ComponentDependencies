package com.gkorenevsky.exercise;

import java.util.*;

/**
 * Created by engineer on 7/11/14.
 */
public class ComponentManager {

    private static class ComponentDependency {
        private Set<String> dependsOnSet;
        private Set<String> dependedOnSet;

        public ComponentDependency() {
            dependsOnSet = new HashSet<String>();
            dependedOnSet = new HashSet<String>();
        }

        public void addDependsOn(String componentName) {
            dependsOnSet.add(componentName);
        }

        public void addDependsOn(List<String> componentNames) {
            dependsOnSet.addAll(componentNames);
        }

        public void addDependedOn(String componentName) {
            dependedOnSet.add(componentName);
        }

        public void addDependedOn(List<String> componentNames) {
            dependedOnSet.addAll(componentNames);
        }

        public Set<String> getDependsOnSet() {
            return dependsOnSet;
        }

        public Set<String> getDependedOnSet() {
            return dependedOnSet;
        }
    }

    private Set<String> installedComponents = new HashSet<String>();
    private Map<String, ComponentDependency> dependencyMatrix = new HashMap<String, ComponentDependency>();

    public void installComponent(String componentName) {

        if (!installedComponents.contains(componentName)) {
            installedComponents.add(componentName);
            System.out.println("Installing " + componentName);
        }

        ComponentDependency dependency = dependencyMatrix.get(componentName);

        if (dependency != null && dependency.getDependsOnSet().size() > 0) {
            for (String requiredComponent : dependency.getDependsOnSet()) {
                installComponent(requiredComponent);
            }
        }
    }

    public void listComponents() {

        for (String componentName : installedComponents) {
            System.out.println(componentName);
        }
    }

    public void addDependency(String componentName, List<String> componentsDependedOn) {

        ComponentDependency dependency = dependencyMatrix.get(componentName);

        if (dependency == null) {
            dependency = new ComponentDependency();
            dependencyMatrix.put(componentName, dependency);
        }

        dependency.addDependsOn(componentsDependedOn);

        for (String componentDependedOn : componentsDependedOn) {
            dependency = dependencyMatrix.get(componentDependedOn);

            if (dependency == null) {
                addDependency(componentDependedOn, new ArrayList<String>());
                dependency = dependencyMatrix.get(componentDependedOn);
            }

            dependency.addDependedOn(componentName);
        }
    }

    public void removeComponent(String componentName) {
        removeComponent(componentName, false);
    }

    public void removeComponent(String componentName, boolean silent) {

        if (installedComponents.contains(componentName)) {

            boolean okToRemove = true;

            ComponentDependency dependency = dependencyMatrix.get(componentName);

            if (dependency != null && dependency.dependedOnSet.size() > 0) {
                for (String reverseDependency : dependency.dependedOnSet) {
                    if (installedComponents.contains(reverseDependency)) {
                        okToRemove = false;
                    }
                }

                if (!silent && !okToRemove) {
                    System.out.println(componentName + " is still needed");
                }
            }

            if (okToRemove) {

                installedComponents.remove(componentName);
                System.out.println("Removing " + componentName);

                if (dependency != null && dependency.dependsOnSet.size() > 0) {
                    for (String directDependency : dependency.dependsOnSet) {
                        if (installedComponents.contains(directDependency)) {
                            removeComponent(directDependency, true);
                        }
                    }
                }
            }
        }
    }
}
