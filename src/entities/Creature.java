package entities;

import supportClasses.Coordinate;
import supportClasses.MapPrinter;
import supportClasses.PathBuilder;
import supportClasses.WorldMap;

import java.util.List;

public abstract class Creature extends Entity {
    protected int health;
    protected int speed;
    protected int stepsLeft;
    protected boolean isAlive;
    protected Class<?> food;

    public boolean isAlive() {
        return isAlive;
    }

    protected void kill(Creature creature) {
        creature.isAlive = false;
    }

    public void reduceHealth(int damage) {
        health -= damage;
    }

    public int getHealth() {
        return health;
    }

    private int getSpeed() {
        return speed;
    }

    public Class<?> getFood() {
        return food;
    }

    public void makeMove(WorldMap worldMap) {
        stepsLeft = this.getSpeed();
        PathBuilder pathBuilder = new PathBuilder(worldMap, this);
        while (stepsLeft > 0) {
            List<Coordinate> path = pathBuilder.getPath();
            if (path.isEmpty()) {
                break;
            }
            path.removeLast();
            if (worldMap.isEmpty(path.getLast())) {
                takeStep(worldMap, path.getLast());
            } else if (worldMap.get(path.getLast()).getClass() == this.food) {
                eat(worldMap, path.getLast());
            }
        }
    }

    protected void takeStep(WorldMap worldMap, Coordinate coordinate) {
        Coordinate from = worldMap.getCoordinate(this);
        worldMap.put(coordinate, this);
        worldMap.remove(from);
        stepsLeft--;
    }

    protected abstract void eat(WorldMap worldMap, Coordinate coordinate);
}
