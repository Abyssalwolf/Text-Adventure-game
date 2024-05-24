import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

class Room{
    private String roomname;
    private HashMap<String, Room> exits;
    public List<String> items;
    private String requiredItem;
    private String storyPlot;
    private boolean searched;
    
    public Room(String roomname){
        this.roomname=roomname;
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
    }

    public String getRoomName(){
        return roomname;
    }

    public void setExit(String name, Room neighbour){
        exits.put(name,neighbour);
    }

    public Room getExit(String name){
        return exits.get(name);
    }

    public void addItem(String item){
        items.add(item);
    }

    public List<String> getItems(){
        return items;
    }

    public String roomEntry(){
        return ("You have entered " + roomname + ".\n");
    }

    public String getExitString(){
        StringBuilder exitString = new StringBuilder("Exits: ");
        for (String areas : exits.keySet()){
            exitString.append(" ").append(areas);
        }
        return exitString.toString();
    }

    public void setRequiredItem(String item) {
        this.requiredItem = item;
    }

    public String getRequiredItem() {
        return requiredItem;
    }
    public String getLongDescription() {
        return "You are at " + roomname + ".\n" + "You can go to \n" + getExitString();
    }

    public void setStoryPlot(String plot){
        storyPlot=plot;
    }

    public String getStoryPlot() {
        return storyPlot;
    }

    public boolean hasBeenSearched() {
        return searched;
    }

    public void setSearched(boolean lookedAt) {
        this.searched = lookedAt;
    }

}

class Player {
    private Room currentRoom;
    private List<String> inventory;

    public Player(Room startRoom) {
        currentRoom = startRoom;
        inventory = new ArrayList<>();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        currentRoom = room;
    }

    public void addItem(String item) {
        inventory.add(item);
    }

    public List<String> getInventory() {
        return inventory;
    }
}

public class Game {
    private Player player;
    private Scanner scanner;

    public Game(){
        createRooms();
        scanner= new Scanner(System.in);
    }

    public void createRooms(){
        Room outside = new Room("Outside")  ;
        Room cabin =new Room("Cabin rooms");
        Room ruin = new Room("Ruins of Lazuli");
        Room house = new Room("House of the Hero");
        Room cabin1 =new Room("Cabin room 1");
        Room cabin2 =new Room("Cabin room 2");
        Room cabin3 =new Room("Cabin room 3");
        Room cabin4 =new Room("Cabin room 4");
        Room ruina = new Room("Altar room");
        Room ruinb = new Room("Area 1");
        Room ruinc = new Room("Area 2");
        Room houser1 = new Room("Storage");
        Room houser2 = new Room("Bedroom");
        Room houser3 = new Room("Study");
        Room houseb = new Room("Basement");
        Room demon = new Room("Demonic Lair");

        outside.setExit("North", house);
        outside.setExit("West", cabin);
        outside.setExit("East", ruin);
        outside.setExit("Demon", demon);

        cabin.setExit("Room-1", cabin1);
        cabin.setExit("Room-2", cabin2);
        cabin.setExit("Room-3", cabin3);
        cabin.setExit("Room-4", cabin4);
        cabin.setExit("Outside", outside);

        cabin.setStoryPlot("Entering upon the building you see some rooms within your line of site. Looks like a cabin of some sorts. Something might be useful might be in those rooms.");

        cabin1.setExit("Cabin", cabin);
        cabin2.setExit("Cabin", cabin);
        cabin3.setExit("Cabin", cabin);
        cabin4.setExit("Cabin", cabin);
        
        ruin.setExit("Altar", ruina);
        ruin.setExit("Area-1", ruinb);
        ruin.setExit("Area-2", ruinc);
        ruin.setExit("Outside", outside);

        ruin.setStoryPlot("You enter an ancient ruin. What mysteries lie inside needs to be explored to be found out!");

        ruina.setExit("Ruins", ruin);
        ruinb.setExit("Ruins", ruin);
        ruinc.setExit("Ruins", ruin);

        ruina.setStoryPlot("With great difficulty, you clear the path to a once grandeur altar room. You see a podium upon which lies an object. You can feel its pulsating power even from here. Take a closer look maybe?");

        house.setExit("Bedroom", houser2);
        house.setExit("Study", houser3);
        house.setExit("Storage", houser1);
        house.setExit("Basement", houseb);
        house.setExit("Outside", outside);

        house.setRequiredItem("House key (Story item)");

        house.setStoryPlot("You have entered into the locked house. On closer inspection, you find the house to have potraits of the hero spoken about in the legends, who saved the world from ruin few decades ago");

        houser1.setExit("House", house);
        houser2.setExit("House", house);
        houser3.setExit("House", house);
        houseb.setExit("House", house);

        houseb.setStoryPlot("As you walk into the decripit basement, you feel the presence of light energy around. But all you see are boxes all around. Take a closer look!");

        houseb.setRequiredItem("Basement key (Story item)");
        
        cabin1.addItem("Potion (common)");
        cabin1.addItem("Potion (common)");
        cabin2.addItem("Great Mage's Grimoire (Epic)");
        cabin3.addItem("House key (Story item)");
        cabin4.addItem("Revolver (uncommon)");
        cabin4.addItem("Potion (common)");

        ruina.addItem("Great Mage's Staff (Epic)");
        ruinb.addItem("Potion (common)");
        ruinc.addItem("Shattered stones (useless)");
        ruinc.addItem("Magic bullets (rare)");
        

        houser1.addItem("Flower (useless)");
        houser1.addItem("Mirror (useless)");
        houser1.addItem("Super potion (rare)");
        houser2.addItem("Old Hero Suit (useless)");
        houser3.addItem("Basement key (Story item)");
        houseb.addItem("Hero Sword (Legendary)");

        player = new Player(outside);
    }

    public void play(){
        printWelcome();
        boolean end = false;
        while(!end){
            System.out.print(">>> ");
            String inputLine = scanner.nextLine();
            String[] words = inputLine.split(" ");
            String command = words[0];
            switch (command) {
                case "go" :
                    if (words.length > 1){
                        goRoom(words[1]);
                    }
                    else{
                        System.out.println("Go Where?\n");
                    }
                    break;
                case "search" :
                    search();
                    break;
                case "take":
                    take();
                    break;
                case "inventory":
                    showInventory();
                    break;
                case "quit":
                    end=true;
                    break;
                default:
                    System.out.println("I don't understand that command.");
                    break;
            }
        }
        System.out.println("Thank you for playing. Goodbye!");
    }

    private void printWelcome() {
        System.out.println("Welcome to the Adventure Game! Your goal is to defeat the Demon King by using the items scattered in your world.");
        System.out.println("Game Instructions:\nThe commands that can be used are as follows - 1. go [roomname]\n2. search\n3. take\n4. inventory\n5. quit\n Enter a valid command when prompted during the game. Goodluck and have fun!");
        System.out.println();
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    private void goRoom(String room){
        Room nextRoom = player.getCurrentRoom().getExit(room);
        if (nextRoom == null)
            System.out.println("There is no path!");
        else {
            String reqItem = nextRoom.getRequiredItem();
            if (reqItem!=null && !player.getInventory().contains(reqItem)){
                System.out.println("You need a "+reqItem+" to enter this place. ");
            }
            else{
                player.setCurrentRoom(nextRoom);
                System.out.println(player.getCurrentRoom().getLongDescription());
                if (nextRoom.getStoryPlot() != null){
                    System.out.println(nextRoom.getStoryPlot());
                }
                if (nextRoom.getRoomName()=="Demonic Lair"){
                    showEnding();
                }
            }
        }
    }

    private void search(){
        player.getCurrentRoom().setSearched(true);
        List<String> items = player.getCurrentRoom().getItems();
        if (items.isEmpty() || items==null) {
            System.out.println("There are no items here.");
        } else {
            System.out.println("You see: " + String.join(", ", items));
        }
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    private void take(){
        if (player.getCurrentRoom().hasBeenSearched()){
            List<String> items = player.getCurrentRoom().getItems();
            if (items.isEmpty()){
                System.out.println("There are no items here!");
            }
            else{
                for (String i : items){
                    player.addItem(i);
                }
                items.clear();
                player.getCurrentRoom().items.clear();
                System.out.println("You have taken all the items in the room.");
            }
        }
        else{
            System.out.println("You haven't checked around to pick up Items yet!");
        }
    }

    private void showInventory(){
        List<String> inventory = player.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("You are carrying nothing.");
        } else {
            System.out.println("You are carrying: " + String.join(", ", inventory));
        }
    }

    private void showEnding(){
        List<String> inventory = player.getInventory();
        if (inventory.contains("Hero Sword (Legendary)")){
            System.out.println("The time has come to beat the demon. You feel a holy presence within you as you near the demonic creature. The sword within your inventory jumps out between you and the evil entity. It sends the demon recoiling, making it jump backwards while screetching demonic words. However, the power within the sword helps you understand that it can be beaten. You raise your sword uttering the words 'SunSplitter' and all you see is white around you.\nYou wake up a bit dizzy to find charred remains of the Demon and the broken remains of the Holy Sword.\n\nGame Cleared\nENDING NO 1 OF 3 - THE HERO'S PATH\n");
        }
        else if(inventory.contains("Great Mage's Grimoire (Epic)") && inventory.contains("Great Mage's Staff (Epic)") && inventory.contains("Potion (common)")){
            System.out.println("You enter the clearing and find the demon at the end, staring at you menacingly. It growls incomprehensible demonic words while you begin to walk forward. The staff and the grimoire appear in your hand, your body overflowing with the power of the World's mana. As the demon jumps towards you, you send a fireball at it. Although it made contact, the demon bears the pain and reaches you to slash you with its claws. You quickly put up a barrier which blocks one attack but breaks allowing the demon to strike you. You are sent flying and hit a tree. Your inexperience with magic shows frustrating yourself. But you feel yourself filling with determination. You pop open a potion and down it, healing a part of your injuries, then turn to the final page of the grimoire and begin chanting. The demon who was mocking you as if saying all struggle is futile slowly realizes that something is terribly wrong. It senses danger from you and rushes to stop the remaining of the cast, but it is too late. A barrier pops up blocking its strike while you look up at it and say 'Final Requiem'. The world goes white and you wake up later to the charred remains of the demon and a broken magical staff.\n\nGame Cleared\nENDING NO 2 OF 3 - THE GREAT MAGE");
        }
        else if (inventory.contains("Revolver (uncommon)") && inventory.contains("Magic bullets (rare)") && inventory.contains("Super potion (rare)")){
            System.out.println("You enter the clearing and find the demon at the end, staring at you menacingly. It growls incomprehensible demonic words while you begin to walk forward. You take out your revolver and fill it with the magic bullets. The demon lunges at you and you start firing. You are able to get three shots in which stops the demon at its path. Satisfied that you are able to damage it, you let your gaurd down for a single moment during which the demon lunges. It hits you with such force that you are sent flying and hit a tree with great force. Multiple bones have been broken and you are unable to move a lot. The demon also is in pain but it manages to get up and slowly startwalking towards you. You feel this is the end as you realise normal potions are not enough to get you up and moving. As your life begins to flash through your eyes, you remember about the mysterious potion you found at the hero's house. In a last ditch attempt, you unscrew and drink it to miraculously find yourself being healed at a quick rate. You get up and jump back just in time to dodge a claw attack from the demon and unload your remaing bullets at it. You feel a sense of victory as you gaze at the now dead demon and leave, although with many injuries, but alive nonetheless.\n\nGame Cleared\nENDING NO 3 OF 3 - THE SOLDIER");
        }
        else{
            System.out.println("You are unprepared to fight the demon and it kills you in 2 hits. THE END\nTry again.");
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }

}
