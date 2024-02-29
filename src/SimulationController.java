import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

import exceptions.MowerCrashException;

public class SimulationController {
    private Lawn lawn;
    private List<Lawnmower> lawnmowers;

    public SimulationController(Lawn lawn, List<Lawnmower> lawnmowers) {
        this.lawn = lawn;
        this.lawnmowers = lawnmowers;
    }

    public void advanceLawnmowers() throws MowerCrashException{
        for (Lawnmower mower : lawnmowers) {
            MowerAction action = mower.getNextAction();
            switch (action.getActionType()) {
                case MOVE:
                    MoveResult result = move(mower, action.getDistance(), action.getDirection(), lawn);
                    mower.receiveMoveResult(result);
                    if (result.isImmobilized()) {
                        throw new MowerCrashException("Lawnmower crashed at " + mower.getLocation());
                    }
                    break;
                case SCAN:
                    scan(mower);
                    break;
                case SHUTOFF:
                    // Handle lawnmower shutoff logic
                    break;
            }
        }
    }

    private MoveResult move(Lawnmower mower, int steps, Direction newDirection, Lawn lawn) {
        Point currentStepLocation = mower.getLocation();
        boolean immobilized = false;
        List<Point> grassCutPositions = new ArrayList<>();

        for (int i = 1; i <= steps; i++) {
            Point nextStep = calculateNextStep(currentStepLocation, mower.getDirection()); // Use mower's current direction for movement

            if (!lawn.isWithinBounds(nextStep.x, nextStep.y)) {
                immobilized = true;
                break;
            }

            currentStepLocation.setLocation(nextStep.x, nextStep.y); // move to the next step
            if (lawn.isObstacle(nextStep.x, nextStep.y)) {
                immobilized = true;
                break;
            }

            if (lawn.getSquareStatus(nextStep.x, nextStep.y).containsGrass()) {
                lawn.markGrassCut(nextStep.x, nextStep.y);
                grassCutPositions.add(new Point(nextStep));
            }
        }

        mower.setLocation(currentStepLocation); // Update mower's location
        mower.setDirection(newDirection); // Update mower's direction

        return new MoveResult(mower.getLocation(), mower.getDirection(), grassCutPositions, immobilized);
    }

    private Point calculateNextStep(Point currentLocation, Direction direction) {
        Point nextStep = new Point(currentLocation.x, currentLocation.y);
        switch (direction) {
            case NORTH:
                nextStep.y += 1;
                break;
            case NORTHEAST:
                nextStep.x += 1;
                nextStep.y += 1;
                break;
            case EAST:
                nextStep.x += 1;
                break;
            case SOUTHEAST:
                nextStep.x += 1;
                nextStep.y -= 1;
                break;
            case SOUTH:
                nextStep.y -= 1;
                break;
            case SOUTHWEST:
                nextStep.x -= 1;
                nextStep.y -= 1;
                break;
            case WEST:
                nextStep.x -= 1;
                break;
            case NORTHWEST:
                nextStep.x -= 1;
                nextStep.y += 1;
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }
        return nextStep;
    }

    public void scan(Lawnmower mower) {
        // Assuming Lawnmower has a method to receive a List<ScanResult>
        List<ScanResult> scanResults = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            Point scanLocation = getAdjacentLocation(mower.getLocation(), dir);
            ScanResult result = determineScanResult(lawn, scanLocation);
            scanResults.add(result);
        }
        mower.receiveScanResults(scanResults);
    }

    private ScanResult determineScanResult(Lawn lawn, Point location) {
        if (!lawn.isWithinBounds(location.x, location.y)) {
            return ScanResult.FENCE;
        } else if (lawn.isObstacle(location.x, location.y)) {
            return ScanResult.OBSTACLE;
        } else if (lawn.getSquareStatus(location.x, location.y).containsGrass()) {
            return ScanResult.GRASS;
        } else {
            return ScanResult.EMPTY;
        }
    }

    private Point getAdjacentLocation(Point currentLocation, Direction direction) {
        switch (direction) {
            case NORTH:
                return new Point(currentLocation.x, currentLocation.y + 1);
            case NORTHEAST:
                return new Point(currentLocation.x + 1, currentLocation.y + 1);
            case EAST:
                return new Point(currentLocation.x + 1, currentLocation.y);
            case SOUTHEAST:
                return new Point(currentLocation.x + 1, currentLocation.y - 1);
            case SOUTH:
                return new Point(currentLocation.x, currentLocation.y - 1);
            case SOUTHWEST:
                return new Point(currentLocation.x - 1, currentLocation.y - 1);
            case WEST:
                return new Point(currentLocation.x - 1, currentLocation.y);
            case NORTHWEST:
                return new Point(currentLocation.x - 1, currentLocation.y + 1);
            default:
                throw new IllegalArgumentException("Unknown direction: " + direction);
        }
    }
}
