import java.util.List;

public class Lawn {
    private int width;
    private int height;
    private int initialGrassSquares;
    private int currentGrassSquares;
    private Square[][] grid;

    public Lawn(int width, int height, List<ObstaclePlacement> obstacles) {
        this.width = width;
        this.height = height;
        this.grid = new Square[height][width];
        this.initialGrassSquares = width * height;
        this.currentGrassSquares = initialGrassSquares;
        
        // Initialize the grid with grass squares
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Square(true, null);
            }
        }

        // Place obstacles and update grass squares
        for (ObstaclePlacement placement : obstacles) {
            placeObstacle(placement.getX(), placement.getY(), placement.getObstacle());
        }
    }

    private void placeObstacle(int x, int y, Obstacle obstacle) {
        if (isWithinBounds(x, y)) {
            grid[y][x].setObstacle(obstacle);
            grid[y][x].setContainsGrass(false);
            currentGrassSquares--;
        }
    }

    public void markGrassCut(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height && grid[y][x].containsGrass()) {
            grid[y][x].setContainsGrass(false);
            currentGrassSquares--;
        }
    }

    public int getTotalGrassSquares() {
        return currentGrassSquares;
    }

    public Square getSquareStatus(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return grid[y][x];
        }
        return null; // Return null or consider throwing an exception for invalid coordinates
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean isObstacle(int x, int y) {
        return isWithinBounds(x, y) && grid[y][x].getObstacle() != null;
    }
}
