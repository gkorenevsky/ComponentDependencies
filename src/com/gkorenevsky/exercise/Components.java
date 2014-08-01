package com.gkorenevsky.exercise;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Components {

    public static void main(String[] args) {

        java.io.InputStream input;
        BufferedReader reader = null;

        ComponentManager cm = new ComponentManager();
        CommandProcessor cp = new CommandProcessor(cm);

        try {
//            input = new FileInputStream("./input.txt");
            input = Components.class.getResourceAsStream("input.txt");
            reader = new BufferedReader(new InputStreamReader(input));

            String commandLine;
            if (reader != null) {

                while ((commandLine = reader.readLine()) != null) {

                    System.out.println(commandLine);
                    String[] parsedCommand = commandLine.split("\\t");

                    List<String> commandOptions = new ArrayList<String>();
                    commandOptions.addAll(Arrays.asList(parsedCommand));
                    String commandVerb = commandOptions.get(0);
                    commandOptions.remove(0);

                    if (commandVerb.equals("DEPEND")) {
                        String componentName = commandOptions.get(0);
                        commandOptions.remove(0);
                        cp.processDepend(componentName, commandOptions);
                    } else if (commandVerb.equals("INSTALL")) {
                        String componentName = commandOptions.get(0);
                        cp.processInstall(componentName);
                    } else if (commandVerb.equals("REMOVE")) {
                        String componentName = commandOptions.get(0);
                        cp.processRemove(componentName);
                    } else if (commandVerb.equals("LIST")) {
                        cp.processList();
                    } else if (commandVerb.equals("END")) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
    }
}
