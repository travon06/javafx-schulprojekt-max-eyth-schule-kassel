package utils;

import java.util.ArrayList;

public class ConfigArguments {
    private final static ArrayList<ConfigArgument> configArguments = new ArrayList<>();

    public static ArrayList<ConfigArgument> getConfigArguments() {
        return configArguments;
    }

    public static String getConfigArgumentValue(String argument) {
        for(ConfigArgument configArgument: ConfigArguments.configArguments) {
            if(configArgument.getArgument().equals(argument)) return configArgument.getValue();
        }
        throw new IllegalArgumentException("ConfigArgument '" + argument + "' does not exist");
    }
}
