package backend.academy.amazed;

import java.util.ArrayList;

public interface PathFinder {
    MazeGrid find(MazeGrid mazeGrid);

    default ArrayList<MazeGrid.Cell> getNeighbours(MazeGrid mazeGrid, MazeGrid.Cell cell) {
        ArrayList<MazeGrid.Cell> neighbours = new ArrayList<>();
        if (!cell.northWall()) {
            neighbours.add(mazeGrid.getCell(cell.y() - 1, cell.x()));
        }
        if (!cell.southWall()) {
            neighbours.add(mazeGrid.getCell(cell.y() + 1, cell.x()));
        }
        if (!cell.westWall()) {
            neighbours.add(mazeGrid.getCell(cell.y(), cell.x() - 1));
        }
        if (!cell.eastWall()) {
            neighbours.add(mazeGrid.getCell(cell.y(), cell.x() + 1));
        }
        return neighbours;
    }
}
