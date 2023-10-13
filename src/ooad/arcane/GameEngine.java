package ooad.arcane;


import ooad.arcane.Adventurer.*;
import ooad.arcane.Creature.*;
import ooad.arcane.Manager.AdventurerManager;
import ooad.arcane.Manager.CreatureManager;
import ooad.arcane.Manager.FloorManager;
import ooad.arcane.Utility.Logger;
import ooad.arcane.Utility.Observer;
import ooad.arcane.Utility.Subject;
import ooad.arcane.Utility.Tracker;
import java.util.ArrayList;

public class GameEngine implements Subject {
    private int turn = 0;
    private int numTreasures = 0;
    private int totalValue = 0;
    private int numCreatures = -1;
    private int numAdventurers = -1;
    private final boolean shouldRender;

    FloorManager floorManager = new FloorManager();
    CreatureManager creatureManager = new CreatureManager(floorManager);
    AdventurerManager adventurerManager = new AdventurerManager(creatureManager, floorManager);
    GameBoard renderer = new GameBoard();

    public GameEngine(boolean shouldRender) {
        this.shouldRender = shouldRender;
        creatureManager.setAdventurerManager(adventurerManager);

        Observer logger = new Logger();
        Observer tracker = new Tracker();

        addObserver(logger);
        addObserver(tracker);

        for (Adventurer adventurer : adventurerManager.getAdventurers()) {
            adventurer.addObserver(logger);
            adventurer.addObserver(tracker);
        }
    }

    // This method returns 1 if adventurers win, 0 if creatures win
    public int Simulate() {
        // Main loop
        while(numTreasures < 24 && totalValue < 15000  && numAdventurers != 0 && numCreatures != 0) {
            turn++;

            if (shouldRender)
                renderer.Render(turn, floorManager);

            // Reset values
            numTreasures = 0;
            totalValue = 0;
            numCreatures = 0;
            numAdventurers = 0;

            ArrayList<Adventurer> adventurers = adventurerManager.getAdventurers();

            for (Adventurer adventurer : adventurers) {
                numAdventurers++;
                adventurer.Turn();
            }

            numTreasures += adventurerManager.getTotalTreasures();
            totalValue += adventurerManager.getTotalValue();

            ArrayList<Creature> creatures = creatureManager.getLivingCreatures();

            for (Creature creature : creatures) {
                numCreatures++;
                creature.Turn();
            }
        }

        System.out.println("...");

        if (numCreatures == 0) {
            System.out.println("Adventurers won ... All creatures killed");
            return 1;
        }
        else if (numTreasures == 24) {
            System.out.println("Adventurers won ... All treasures found");
            return 1;
        }
        else if (totalValue >= 15000) {
            System.out.println("Adventurers won ... Treasures worth 15000 found");
            return 1;
        }
        else {
            System.out.println("Creatures won ... All adventurers killed");
            return 0;
        }
    }

}
