package ui;

import model.*;
import model.Character;
import model.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// *** SOURCE: Java Swing Components Documentation, and various lecture lab starter files -
//             used as inspiration, or tools for building my GUI
//             cool_dragon.gif - @zelphire on Imgur, thank you!

public class ProjectAppGUI extends WindowAdapter {
    private CharacterManager manager;

    private JFrame frame;
    private JTextField nameField;
    private JTextField classField;
    private JTextField bgField;
    private JTextField lvlField;

    private JTextArea displayArea;

    private JTextField itemNameField;
    private JTextField itemTypeField;
    private JTextField itemWeightField;
    private JTextField itemValueField;

    // EFFECTS: calls all initializing methods, creates new manager
    // and sets to visible
    public ProjectAppGUI() {
        try {
            manager = new CharacterManager();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error initializing: " + e.getMessage());
            return;
        }

        frame = new JFrame("D&D Character Sheet");
        JPanel characterPanel = new JPanel();
        JPanel itemPanel = new JPanel();
        initFrame(frame);
        initCharacterPanel(characterPanel);
        initItemPanel(itemPanel);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                EventLog el = EventLog.getInstance();
                printLog(el);
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }

    private void printLog(EventLog el) {
        for (Event next: el) {
            System.out.println(next.getDescription());
        }
    }

    // EFFECTS: initializes frame
    private void initFrame(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);

        displayArea.setEditable(false);
        frame.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        frame.add(new JLabel(new ImageIcon("cool_dragon.gif")), BorderLayout.EAST);
    }

    // EFFECTS: initializes character panel

    private void initCharacterPanel(JPanel characterPanel) {
        characterPanel.add(new JLabel("Name:"));
        nameField = new JTextField(10);
        characterPanel.add(nameField);

        characterPanel.add(new JLabel("Class:"));
        classField = new JTextField(10);
        characterPanel.add(classField);

        characterPanel.add(new JLabel("Background:"));
        bgField = new JTextField(10);
        characterPanel.add(bgField);

        characterPanel.add(new JLabel("Level:"));
        lvlField = new JTextField(3);
        characterPanel.add(lvlField);

        JButton createBtn = new JButton("Create Character");
        createBtn.addActionListener(e -> createCharacter());
        characterPanel.add(createBtn);

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> saveCharacter());
        characterPanel.add(saveBtn);

        JButton loadBtn = new JButton("Load");
        loadBtn.addActionListener(e -> loadCharacter());
        characterPanel.add(loadBtn);

        frame.add(characterPanel, BorderLayout.NORTH);

        characterPanel.setPreferredSize(new Dimension(600, 100));
    }

    // EFFECTS: initializes item panel
    private void initItemPanel(JPanel itemPanel) {
        itemPanel.setLayout(new GridLayout(3, 4));
        itemNameField = new JTextField();
        itemTypeField = new JTextField();
        itemWeightField = new JTextField();
        itemValueField = new JTextField();

        itemPanel.add(new JLabel("Item Name:"));
        itemPanel.add(itemNameField);

        itemPanel.add(new JLabel("Type:"));
        itemPanel.add(itemTypeField);

        itemPanel.add(new JLabel("Weight:"));
        itemPanel.add(itemWeightField);

        itemPanel.add(new JLabel("Value:"));
        itemPanel.add(itemValueField);

        addBtns(itemPanel);

        frame.add(itemPanel, BorderLayout.SOUTH);
        itemPanel.setPreferredSize(new Dimension(600, 120));
    }

    // MODIFIES: this
    // EFFECTS: helper method for initItemPanel(), creates buttons for item panel
    private void addBtns(JPanel itemPanel) {
        JButton addItemBtn = new JButton("  Add Item");
        addItemBtn.addActionListener(e -> addItem());
        itemPanel.add(addItemBtn);

        JButton dropBtn = new JButton("  Drop Item");
        dropBtn.addActionListener(e -> dropItem());
        itemPanel.add(dropBtn);

        JButton favBtn = new JButton("  Favorite");
        favBtn.addActionListener(e -> favoriteItem());
        itemPanel.add(favBtn);

        JButton eqpBtn = new JButton("  Equip");
        eqpBtn.addActionListener(e -> equipItem());
        itemPanel.add(eqpBtn);
    }

    // MODIFIES: this
    // EFFECTS: calls the create character method in CharacterManager,
    // fails if fields are empty
    private void createCharacter() {
        String name = nameField.getText();
        String charClass = classField.getText();
        String background = bgField.getText();
        String level = lvlField.getText();

        if (name.isEmpty() || charClass.isEmpty() || background.isEmpty() || level.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Fill all character fields.");
            return;
        }

        try {
            manager.createCharacter(name, charClass, background, level);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Level only accepts integers.");
            return;
        }
        showCharacter();
    }

    // MODIFIES: this
    // EFFECTS: shows the character and inventory
    // in the display area
    private void showCharacter() {
        Character c = manager.getCharacter();
        if (c == null) {
            displayArea.setText("No character loaded.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(c.getCharacterName()).append("\n");
        sb.append("Class: ").append(c.getCharacterClass()).append("\n");
        sb.append("Background: ").append(c.getBackground()).append("\n");
        sb.append("Level: ").append(c.getLevel()).append("\n");
        sb.append("Inventory:\n");

        for (Item item : c.getInventory()) {

            sb.append("- ").append(item.getItemName()).append(" (").append(item.getType())
                    .append(", ").append(item.getWeight()).append(" lbs, ")
                    .append(item.getValue()).append(" gp)").append("\n");
            if (item.isFavorite()) {
                sb.append(" *** FAVORITE ***\n");
            }
            if (item.isEquipped()) {
                sb.append(" *** EQUIPPED ***\n");
            }
        }

        displayArea.setText(sb.toString());
    }

    // MODIFIES: this
    // EFFECTS: adds an item to the character's inventory and displays it,
    // displayes dialog if w and v fields are inputted incorrectly
    private void addItem() {
        try {
            String name = itemNameField.getText();
            String type = itemTypeField.getText();
            int weight = Integer.parseInt(itemWeightField.getText());
            int value = Integer.parseInt(itemValueField.getText());

            Item item = new Item(name, type, weight, value);
            manager.addItem(item);
            showCharacter();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Weight and value must be numbers.");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds an item to the character's inventory and displays it,
    // displayes dialog if w and v fields are inputted incorrectly
    private void dropItem() {
        try {
            String dropName = JOptionPane.showInputDialog("Which item would you like to drop?");
            Character character = manager.getCharacter();

            if (manager.dropItem(character, dropName) != true) {
                JOptionPane.showMessageDialog(frame, "Item was not found.");
                return;
            }

            showCharacter();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error!");
        }
    }

    // MODIFIES: this
    // EFFECTS: favorites an item and reflects the change in the character's
    // inventory
    private void favoriteItem() {
        try {
            String itemName = JOptionPane.showInputDialog("Which item would you like to favorite/unfavorite?");
            Character character = manager.getCharacter();

            if (manager.favoriteItem(character, itemName) != true) {
                JOptionPane.showMessageDialog(frame, "Item could not be favorited.");
                return;
            }

            showCharacter();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error!");
        }
    }

    // MODIFIES: this
    // EFFECTS: equips an item and reflects the change in the character's inventory
    private void equipItem() {
        try {
            String itemName = JOptionPane.showInputDialog("Which item would you like to equip/unequip?");
            Character character = manager.getCharacter();

            if (manager.equipItem(character, itemName) != true) {
                JOptionPane.showMessageDialog(frame, "Item could not be equipped.");
                return;
            }

            showCharacter();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error!");
        }
    }

    // MODIFIES: this, character.json
    // EFFECTS: saves a character and their inventory to file
    private void saveCharacter() {
        try {
            manager.saveCharacter();
            JOptionPane.showMessageDialog(frame, "Character saved.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error saving: " + e.getMessage());
        }
    }

    // EFFECTS: loads a character and their inventory from file
    private void loadCharacter() {
        try {
            manager.loadCharacter();
            showCharacter();
            JOptionPane.showMessageDialog(frame, "Character loaded.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error loading: " + e.getMessage());
        }
    }

    // EFFECTS: runs the program!
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProjectAppGUI::new);
    }
}
