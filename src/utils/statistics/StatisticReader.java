package utils.statistics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import utils.config.ConfigArguments;

public class StatisticReader {
    public static void readStatistics() {
        Path statisticsPath = Paths.get(ConfigArguments.getConfigArgumentValue("STATISTICS_PATH"));
        Path absoluteStatisticsPath = statisticsPath.toAbsolutePath();
 
        if (Files.exists(absoluteStatisticsPath)) {
            try {
                String data = Files.readString(absoluteStatisticsPath);
                data = data.replaceAll("\\s", ""); // Remove all whitespaces

               for(String line : data.split(";")) {
                    String argument = line.split("=")[0];
                    String value = line.split("=")[1];
                    Statistics.STATISTICS.add(new Statistic(argument, value));
               }                
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println(String.format("File: '%s' does not exist!", absoluteStatisticsPath.toString()));
        }
    }
}
