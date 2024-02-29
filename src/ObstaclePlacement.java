class ObstaclePlacement {
    private int x;
    private int y;
    private Obstacle obstacle;

    public ObstaclePlacement(int x, int y, Obstacle obstacle) {
        this.x = x;
        this.y = y;
        this.obstacle = obstacle;
    }

    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public Obstacle getObstacle() { return obstacle; }
}