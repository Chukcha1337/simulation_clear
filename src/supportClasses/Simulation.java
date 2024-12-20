package supportClasses;

import actions.*;
import entities.unalive.Grass;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final WorldMap worldMap;
    private final MapPrinter mapPrinter;
    private final List<Action> initActions;
    private final List<Action> turnActions;

    public Simulation(WorldMap worldMap) {
        this.worldMap = worldMap;
        this.mapPrinter = new MapPrinter(worldMap);
        this.initActions = new ArrayList<>();
        this.turnActions = new ArrayList<>();
    }

    public void nextTurn() {
        for (Action action : turnActions) {
            action.execute(worldMap);
        }
        mapPrinter.printMap();

    }

    public void startSimulation() {
        initActions.add(new RockSpawnAction());
        initActions.add(new TreeSpawnAction());
        initActions.add(new GrassSpawnAction());
        initActions.add(new HerbivoreSpawnAction());
        initActions.add(new PredatorSpawnAction());

        turnActions.add(new CreatureMoveAction());
        turnActions.add(new GrassSpawnAction());
        turnActions.add(new HerbivoreSpawnAction());

            for (Action action : initActions) {
                action.execute(worldMap);
            }
            int counter = 0;
            while(true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                nextTurn();
                counter++;
                System.out.println(counter);
            }

    }

    public void pauseSimulation() {

    }

}
