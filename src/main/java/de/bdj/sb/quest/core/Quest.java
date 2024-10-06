package de.bdj.sb.quest.core;

public abstract class Quest {

    private int islandId = -1;

    public Quest(int islandId) {
        this.islandId = islandId;
    }

    protected abstract void update();

}
