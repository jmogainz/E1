public class Main {
    public static void main(String[] args) {
        // Assuming the configuration file is in src/config and the src directory is included in the classpath
        String configFilePath = "config/lawn_layout.conf";
        SimulationManager simulationManager = new SimulationManager(configFilePath);
        simulationManager.playSimulation();
    }
}