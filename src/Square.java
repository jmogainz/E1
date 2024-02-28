public class Square {
    private boolean containsGrass;
    private Obstacle obstacle;

    public Square() {
        this.containsGrass = true; // Assuming all squares start with grass by default
        this.obstacle = null; // No obstacle by default
    }

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
