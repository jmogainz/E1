public class Lawn {
    private int width;
    private int height;
    private Square[][] grid;

    public Lawn(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Square[height][width];
        initializeGrid();
    }

    private void initializeGrid() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Square();
            }
        }
    }

    public void placeObstacle(int x, int y, Obstacle obstacle) {
        grid[y][x].setObstacle(obstacle);
    }

    public void markGrassCut(int x, int y) {
        grid[y][x].setContainsGrass(false);
    }

    public boolean isObstacle(int x, int y) {
        return grid[y][x].getObstacle() != null;
    }

    public Square getSquareStatus(int x, int y) {
        return grid[y][x];
    }
 
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    

}

