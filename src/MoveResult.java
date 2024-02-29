import java.awt.Point;
import java.util.List;

public class MoveResult {
    private Point newLocation;
    private Direction newDirection;
    private List<Point> grassCutPositions;
    private boolean immobilized;

    public MoveResult(Point newPosition, Direction newDirection, List<Point> grassCutPositions, boolean isObstacleEncountered) {
        this.newLocation = newPosition;
        this.newDirection = newDirection;
        this.grassCutPositions = grassCutPositions;
        this.immobilized = isObstacleEncountered;
    }

    // Getters
    public Point getNewLocation() {
        return newLocation;
    }

    public Direction getNewDirection() {
        return newDirection;
    }

    public List<Point> getGrassCutPositions() {
        return grassCutPositions;
    }

    public boolean isImmobilized() {
        return immobilized;
    }
}
