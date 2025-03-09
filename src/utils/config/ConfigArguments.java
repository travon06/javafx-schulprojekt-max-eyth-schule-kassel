package utils.config;

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

    public static void setConfigArgumentValue(String argument, String value) {
        for(ConfigArgument configArgument: ConfigArguments.configArguments) {
            if(configArgument.getArgument().equals(argument)) {
                configArgument.setValue(value);      
            }
        }
    }

    public static double getVolume() {
        return Double.parseDouble(getConfigArgumentValue("VOLUME")) / 100;
    }
}
