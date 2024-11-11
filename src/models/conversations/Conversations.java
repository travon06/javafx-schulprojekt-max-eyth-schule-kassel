package models.conversations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import utils.config.ConfigArgument;
import utils.config.ConfigArguments;

public class Conversations {
    private final static ArrayList<Conversation> conversations = new ArrayList<>();

    public static void readConversations() {
        Path configPath = Paths.get("src/utils/conversations/conversations.txt");

        // Convert to absolute path if required
        Path absoluteConfigPath = configPath.toAbsolutePath();
        System.out.println("Config file absolute path: " + absoluteConfigPath);

        if (Files.exists(absoluteConfigPath)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(absoluteConfigPath.toString()))) {
                String line;
                // Read the config file line by line
                while ((line = reader.readLine()) != null) {
                    // skip line if line is a comment
                    if(line.startsWith("#")) {
                        continue;
                        
                    }

                    // inserting data from file



                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Config file does not exist: " + absoluteConfigPath);
        }
    }

    public static ArrayList<Conversation> getConversations() {
        return Conversations.conversations;
    }

    public static Conversation getConversationByTitle(String title) {
        for(Conversation conversation : Conversations.getConversations()) {
            if (title.equals(conversation.getTitle())) {
                return conversation;
            }
        }
        return null;
    }

    public static void createConversation(String title) {
        Conversations.getConversations().add(new Conversation(title));
    }

    public static void addCommentToConversation(Conversation conversation, Comment comment) {
        if(!Conversations.getConversations().contains(conversation)) {
            throw new Error("Conversation does not exist");
        }

        conversation.getComments().add(comment);
    }

}
