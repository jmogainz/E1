import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.awt.Point;

public class Lawnmower {
    private Point location;
    private Direction direction;
    private Map<Point, ScanResult> knowledgeMap;

    public Lawnmower(Point location, Direction direction) {
        this.location = location;
        this.direction = direction;
        this.knowledgeMap = new HashMap<>();
    }

    public MowerAction getNextAction() {
        // Check immediate surroundings for grass
        for (int steps = 1; steps <= 2; steps++) {
            Point nextLocation = getAdjustedLocation(this.location, this.direction, steps);
            ScanResult result = knowledgeMap.getOrDefault(nextLocation, ScanResult.UNKNOWN);

            if (result == ScanResult.GRASS) {
                // Move towards grass if found within 1 or 2 steps in the current direction
                return new MowerAction(ActionType.MOVE, this.direction, steps);
            } else if (result == ScanResult.FENCE || result == ScanResult.OBSTACLE) {
                // If an obstacle or fence is immediately in the direction, don't move, just change direction
                Direction newDirection = findBestDirectionForExploration();
                if (newDirection != null) {
                    return new MowerAction(ActionType.MOVE, newDirection, 0);
                }
            }
        }

        // If no grass is found in the current direction within 2 steps, decide on the next best action
        Direction newDirection = findBestDirectionForExploration();
        if (newDirection != null && newDirection != this.direction) {
            // Change direction if a better direction is found
            return new MowerAction(ActionType.MOVE, newDirection, 0);
        }

        // Default action if no better option is found
        return new MowerAction(ActionType.SCAN, this.direction, 0);
    }

    private Direction findBestDirectionForExploration() {
        // This method should analyze the knowledgeMap to determine the best direction to explore next
        // For simplicity, return a different direction as an example. Implement logic based on your requirements
        for (Direction dir : Direction.values()) {
            if (dir != this.direction) {
                return dir; // Simplified decision-making
            }
        }
        return null;
    }

    private Point getAdjustedLocation(Point currentLocation, Direction direction, int steps) {
        Point adjustedLocation = new Point(currentLocation.x, currentLocation.y);
        switch (direction) {
            case NORTH: adjustedLocation.y += steps; break;
            case NORTHEAST: adjustedLocation.x += steps; adjustedLocation.y += steps; break;
            case EAST: adjustedLocation.x += steps; break;
            case SOUTHEAST: adjustedLocation.x += steps; adjustedLocation.y -= steps; break;
            case SOUTH: adjustedLocation.y -= steps; break;
            case SOUTHWEST: adjustedLocation.x -= steps; adjustedLocation.y -= steps; break;
            case WEST: adjustedLocation.x -= steps; break;
            case NORTHWEST: adjustedLocation.x -= steps; adjustedLocation.y += steps; break;
        }
        return adjustedLocation;
    }

    public void receiveScanResults(List<ScanResult> scanResults) {
        // Assuming the scan results are received in a clockwise manner starting from NORTH
        Direction[] directions = Direction.values();
        for (int i = 0; i < scanResults.size(); i++) {
            Point scanLocation = getAdjustedLocation(this.location, directions[i], 1);
            this.knowledgeMap.put(scanLocation, scanResults.get(i));
        }
    }

    public void receiveMoveResult(MoveResult moveResult) {
        this.location = moveResult.getNewLocation();
        this.direction = moveResult.getNewDirection();
        // Assuming MoveResult includes a list of points where grass was cut
        for (Point grassCutLocation : moveResult.getGrassCutPositions()) {
            // Update knowledge map to reflect the grass has been cut
            this.knowledgeMap.put(grassCutLocation, ScanResult.EMPTY);
        }
        if (moveResult.isImmobilized()) {
            // Handle immobilization (not specified in detail, could involve setting a status or notifying the controller)
        }
    }

    // Getters and Setters

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Map<Point, ScanResult> getKnowledgeMap() {
        return knowledgeMap;
    }

    public void setKnowledgeMap(Map<Point, ScanResult> knowledgeMap) {
        this.knowledgeMap = knowledgeMap;
    }
}
