package ooad.arcane.Adventurer.Treasure.Decorators;

import ooad.arcane.Adventurer.Adventurer;
import ooad.arcane.Adventurer.Treasure.Treasure;
import ooad.arcane.Adventurer.Treasure.TreasureBag;
import ooad.arcane.Adventurer.Treasure.TreasureDecorator;
import ooad.arcane.Creature.Creature;

public class Armor extends TreasureDecorator {
    int value = 800;

    public Armor(Treasure treasure) {
        super(treasure);
    }

    @Override
    public int getNumTreasures() {
        return super.getNumTreasures() + 1;
    }

    @Override
    public void applyBonus(Adventurer adventurer, Creature creature) {
        
    }
}
