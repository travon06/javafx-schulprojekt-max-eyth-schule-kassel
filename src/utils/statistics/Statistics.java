package utils.statistics;

import java.util.ArrayList;

public class Statistics {
    public final static ArrayList<Statistic> STATISTICS = new ArrayList<>();
    
    public static String getStatisticValue(String argument) {
        for(Statistic statistic : Statistics.STATISTICS) {
            if(statistic.getArgument().equals(argument)) {
                return statistic.getValue();
            }
        }
        return null;
    }

    public static void setStatisticValue(String argument, String value) {
        for(Statistic statistic : Statistics.STATISTICS) {
            if(statistic.getArgument().equals(argument)) {
                statistic.setValue(value);
                StatisticWriter.writeStatistics();
            }
        }
    }
}
