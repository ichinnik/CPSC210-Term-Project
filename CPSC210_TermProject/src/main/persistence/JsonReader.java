package persistence;

import model.Character;
import model.Item;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// CITATION: Steps and inspiration taken from example file - JsonSerializationDemo
//           in order to specify, test, and implement all persistence classes!
// Represents a reader that reads a character from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads character from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Character read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCharacter(jsonObject);
    }

    // EFFECTS: reads source file as a string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Character from JSON objeect and returns it
    private Character parseCharacter(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String characterClass = jsonObject.getString("characterClass");
        int level = jsonObject.getInt("level");
        String background = jsonObject.getString("background");
        Character c = new Character(name, characterClass, background);
        c.setLevel(level);
        addItems(c, jsonObject);
        return c;
    }

    // MODIFIES: c
    // EFFECTS: parses items from JSON object and adds them to the Character
    private void addItems(Character c, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("inventory");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(c, nextItem);
        }
    }

    // MODIFIES: c
    // EFFECTS: parses item from JSON object and adds it to the Character
    private void addItem(Character c, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String itemType = jsonObject.getString("itemType");
        int weight = jsonObject.getInt("weight");
        int value = jsonObject.getInt("value");
        Boolean equipped = jsonObject.getBoolean("equipped");
        Boolean favorite = jsonObject.getBoolean("favorite");
        Item item = new Item(name, itemType, weight, value);
        if (equipped) {
            item.equip();
        }
        if (favorite) {
            item.favorite();
        }
        c.addItem(item);
    }
}
