public class Square {
    private boolean containsGrass;
    private Obstacle obstacle;

    public Square(boolean containsGrass, Obstacle obstacle) {
        this.containsGrass = containsGrass;
        this.obstacle = obstacle;
    }

    // Getters and setters
    public boolean containsGrass() {
        return containsGrass;
    }

    public void setContainsGrass(boolean containsGrass) {
        this.containsGrass = containsGrass;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }
}
