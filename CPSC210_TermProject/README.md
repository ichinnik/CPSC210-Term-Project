# Digital D&D Character Sheet

## A CPSC 210 Role-Playing Aid

---

My personal project will be a **Dungeons & Dragons** character sheet implement, which will help track changes made during a campaign, as well as represent a player character's appearance, attributes, and inventory.

This project will be used by people looking for a useful accessory for a D&D campaign which would be more dynamic than pencil and paper. It also allows players to select and equip their favorite items and armor for their characters in an interactive fashion.

The idea of this project came to me because I personally have a handful of friends who enjoy D&D as a hobby, and I would find it highly rewarding to attempt to introduce a program which could assist them in their daring adventures.

## User Stories
- As a user, I want to be able to add an arbitrary amount of items to my inventory.
- As a user, I want to be able to view the items in my inventory.
- As a user, I want to be able to mark an item as equipped in my inventory.
- As a user, I want to be able to drop an item from my inventory.
- As a user, I want to be able to represent my character and adjust their various attributes (name, class, background, etc.)
- As a user, I want to be able to save my character and their inventory to file (if I so choose)
- As a user, I want to be able to load my character and their inventory to file (if I so choose)

## Instructions for End User
- You can add items to your inventory by specifying the necessary item fields, then clicking the "Add Item" button.
- You can remove items from your inventory by inputting the item name, then clicking the "Drop Item" button.
- You can equip/favorite items (or vice versa) from your inventory by inputting the item name, then clicking the "Drop" or "Equip" button.
- You can locate my visual component by booting up the application, in order to see the dragon GIF in the application.
- You can save the state of my application by clicking the "Save" button.
- You can reload the state of my application by clicking the "Load" button.

## Phase 4: Task 2
- Character created.
- Item added to character's inventory.     
- Item added to character's inventory.     
- Item to be dropped not found.
- Item dropped from character's inventory. 
- Favorited an item in the character's inventory.
- Equipped an item in the character's inventory.
- Unfavorited an item in the character's inventory.
- Unequipped an item in the character's inventory.
- Saved a character to file.

## Phase 4: Task 3
I believe that my application could have benefited more from separating the inventory from the character into its own specific Inventory class, as that would increase the cohesion of my project. I also believe that the ProjectApp class would work better if it executed the same logic as ProjectAppGUI, drawing from the well-defined methods in CharacterManager instead of manipulating Character itself. 