package utils;

public class ConfigArgument {
    private final String argument;
    private final String value;

    public ConfigArgument(String argument, String value) {
        this.argument = argument;
        this.value = value;
    }

    public String getArgument() {
        return this.argument;
    }

    public String getValue() {
        return this.value;
    }
}