import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.Point;

import exceptions.MowerCrashException;

public class SimulationManager {
    private SimulationController simulationController;

    public SimulationManager(String filename) {
        try {
            // Create file and scanner objects to read the configuration file
            File configFile = new File(filename);
            Scanner scanner = new Scanner(configFile);

            // Read lawn dimensions
            int lawnHeight = scanner.nextInt();
            int lawnWidth = scanner.nextInt();

            // Read number of lawnmowers
            int numLawnmowers = scanner.nextInt();
            List<Lawnmower> lawnmowers = new ArrayList<>();

            // Read number of craters
            int numCraters = scanner.nextInt();
            List<ObstaclePlacement> obstaclePlacements = new ArrayList<>();
            for (int i = 0; i < numCraters; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                obstaclePlacements.add(new ObstaclePlacement(x, y, new Crater()));
            }

            // Initialize the lawn with the dimensions and obstacles
            Lawn lawn = new Lawn(lawnWidth, lawnHeight, obstaclePlacements);

            // Initialize lawnmowers with their positions and directions
            for (int i = 0; i < numLawnmowers; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                String direction = scanner.next();
                Lawnmower lawnmower = new Lawnmower(new Point(x, y), Direction.valueOf(direction.toUpperCase()));
                lawnmowers.add(lawnmower);
            }

            // Initialize the simulation controller with the lawn and lawnmowers
            this.simulationController = new SimulationController(lawn, lawnmowers);

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Configuration file not found: " + filename);
        }
    }

    public void playSimulation() {
        while (true) {
            try {
                simulationController.advanceLawnmowers();
            } catch (MowerCrashException e) {
                System.out.println("Simulation halted: " + e.getMessage());
                break;
            }
            // TODO: Add logic to check for simulation end conditions and break the loop, complete if all mowers shut off
        }
    }
}
