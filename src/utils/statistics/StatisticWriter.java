package utils.statistics;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import utils.config.ConfigArguments;

public class StatisticWriter {
    public static void resetStatistics() {
        for(Statistic statistic : Statistics.STATISTICS) {
            if(statistic.getArgument().equals("FIRST_TIME_IN_GAME")) {
                Statistics.setStatisticValue("FIRST_TIME_IN_GAME", "TRUE");
            } else if (statistic.getArgument().equals("PLAYED_MINUTES")) {
                Statistics.setStatisticValue("MINUTES_PLAYED", "0");
            } else if (statistic.getArgument().equals("TIMES_CAUGHT")) {
                Statistics.setStatisticValue("TIMES_CAUGHT", "0");
            } else if (statistic.getArgument().equals("LAST_LEVEL_INDEX")) {
                Statistics.setStatisticValue("LAST_LEVEL_INDEX", "0");
            }
        }
    }
    public static void writeStatistics() {
        Path statisticsPath = Paths.get(ConfigArguments.getConfigArgumentValue("STATISTICS_PATH"));
        Path absoluteStatisticsPath = statisticsPath.toAbsolutePath();

        if(Files.exists(absoluteStatisticsPath)) {
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(ConfigArguments.getConfigArgumentValue("STATISTICS_PATH")))) {
                
                String statisticsString = "";

                for(Statistic statistic : Statistics.STATISTICS) {
                    statisticsString += String.format("%s=%s;\n", statistic.getArgument(), statistic.getValue());
                }
                writer.write(statisticsString);

            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
