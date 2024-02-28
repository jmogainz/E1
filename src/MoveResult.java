import java.awt.Point;
import java.util.List;

public class MoveResult {
    private Point newPosition;
    private Direction newDirection;
    private List<Point> grassCutPositions;
    private boolean isObstacleEncountered;

    public MoveResult(Point newPosition, Direction newDirection, List<Point> grassCutPositions, boolean isObstacleEncountered) {
        this.newPosition = newPosition;
        this.newDirection = newDirection;
        this.grassCutPositions = grassCutPositions;
        this.isObstacleEncountered = isObstacleEncountered;
    }

    // Getters
    public Point getNewPosition() {
        return newPosition;
    }

    public Direction getNewDirection() {
        return newDirection;
    }

    public List<Point> getGrassCutPositions() {
        return grassCutPositions;
    }

    public boolean isObstacleEncountered() {
        return isObstacleEncountered;
    }
}
