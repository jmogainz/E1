public class MowerAction {
    private ActionType actionType;
    private Direction direction;
    private int distance;

    public MowerAction(ActionType actionType, Direction direction, int distance) {
        this.actionType = actionType;
        this.direction = direction;
        this.distance = distance;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getDistance() {
        return distance;
    }
}
