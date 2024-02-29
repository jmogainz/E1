import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lawnmower {
    private Point location; // Current location
    private Direction direction;
    private Map<Point, ScanResult> knowledgeMap; // Dynamic map to store known squares

    public Lawnmower(Point startingLocation, Direction startingDirection) {
        this.location = (Point) startingLocation.clone();
        this.direction = startingDirection;
        this.knowledgeMap = new HashMap<>();

        // Cut the grass at the starting location
        cutGrassAt(location);
        
    }

    private void cutGrassAt(Point location) {
        knowledgeMap.put(new Point(location), ScanResult.EMPTY); // Assuming grass is cut and now the square is empty
    }

    // method to select the lawnmmowers next action; the sophisticated algorithm of it all
    

    public MoveResult move(int steps, Direction newDirection, Lawn lawn) {
        Point currentStepLocation = new Point(location);
        boolean immobilized = false;
        List<Point> grassCutPositions = new ArrayList<>();

        for (int i = 1; i <= steps; i++) {
            Point nextStep = calculateNextStep(currentStepLocation, this.direction); // Use current direction for
                                                                                     // movement

            if (!lawn.isWithinBounds(nextStep.x, nextStep.y)) {
                System.out.println("Move stopped: Out of bounds.");
                immobilized = true;
                break;
            }

            currentStepLocation.setLocation(nextStep.x, nextStep.y); // move to the next step even if there's an obstacle
            if (lawn.isObstacle(nextStep.x, nextStep.y)) {
                System.out.println("Move stopped: Obstacle encountered.");
                immobilized = true;
                break;
            }

            if (lawn.getSquareStatus(nextStep.x, nextStep.y).containsGrass()) {
                cutGrassAt(nextStep);
                grassCutPositions.add(new Point(nextStep));
            }
        }

        // Update the lawnmower's location to the final step position
        location.setLocation(currentStepLocation);
        // Update the lawnmower's direction to the new direction after completing the
        // move
        this.direction = newDirection;

        return new MoveResult(location, this.direction, grassCutPositions, immobilized);
    }

    private Point calculateNextStep(Point currentLocation, Direction direction) {
        Point nextStep = new Point(currentLocation);
        switch (direction) {
            case NORTH:
                nextStep.y -= 1;
                break;
            case NORTHEAST:
                nextStep.x += 1;
                nextStep.y -= 1;
                break;
            case EAST:
                nextStep.x += 1;
                break;
            case SOUTHEAST:
                nextStep.x += 1;
                nextStep.y += 1;
                break;
            case SOUTH:
                nextStep.y += 1;
                break;
            case SOUTHWEST:
                nextStep.x -= 1;
                nextStep.y += 1;
                break;
            case WEST:
                nextStep.x -= 1;
                break;
            case NORTHWEST:
                nextStep.x -= 1;
                nextStep.y -= 1;
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }
        return nextStep;
    }

    public void scan(Lawn lawn) {
        // Scan in all eight directions from the lawnmower's current location
        for (Direction dir : Direction.values()) {
            Point scanLocation = getAdjacentLocation(location, dir);
            // Determine the result of scanning in this direction
            ScanResult result = determineScanResult(lawn, scanLocation);
            // Update the knowledge map with the result
            knowledgeMap.put(scanLocation, result);
        }
    }

    private ScanResult determineScanResult(Lawn lawn, Point location) {
        // Check if the location is out of bounds (considered as FENCE)
        if (!lawn.isWithinBounds(location.x, location.y)) {
            return ScanResult.FENCE;
        }
        // Check for obstacles at the location
        else if (lawn.isObstacle(location.x, location.y)) {
            return ScanResult.OBSTACLE;
        }
        // Check if there's grass at the location
        else if (lawn.getSquareStatus(location.x, location.y).containsGrass()) {
            return ScanResult.GRASS;
        }
        // If none of the above, the square is empty
        else {
            return ScanResult.EMPTY;
        }
    }

    private Point getAdjacentLocation(Point currentLocation, Direction direction) {
        // Calculate the adjacent location based on direction
        switch (direction) {
            case NORTH:
                return new Point(currentLocation.x, currentLocation.y - 1);
            case NORTHEAST:
                return new Point(currentLocation.x + 1, currentLocation.y - 1);
            case EAST:
                return new Point(currentLocation.x + 1, currentLocation.y);
            case SOUTHEAST:
                return new Point(currentLocation.x + 1, currentLocation.y + 1);
            case SOUTH:
                return new Point(currentLocation.x, currentLocation.y + 1);
            case SOUTHWEST:
                return new Point(currentLocation.x - 1, currentLocation.y + 1);
            case WEST:
                return new Point(currentLocation.x - 1, currentLocation.y);
            case NORTHWEST:
                return new Point(currentLocation.x - 1, currentLocation.y - 1);
            default:
                throw new IllegalArgumentException("Unknown direction: " + direction);
        }
    }
}
