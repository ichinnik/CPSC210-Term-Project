package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Tally-ho! Welcome to the Dungeons & Dragons Character Sheet Utility!");
            new ProjectApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
