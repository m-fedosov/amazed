package backend.academy.amazed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class PathFinderBFS {

    public MazeGrid find(MazeGrid mazeGrid) {
        MazeGrid.Cell startCell = mazeGrid.startCell();
        MazeGrid.Cell onCell = startCell;
        MazeGrid.Cell endCell = mazeGrid.endCell();

        ArrayList<MazeGrid.Cell> frontier = new ArrayList<>();
        frontier.add(onCell);
        ArrayList<MazeGrid.Cell> explored = new ArrayList<>();
        explored.add(onCell);
        HashMap<MazeGrid.Cell, MazeGrid.Cell> bfsPath = new HashMap<>();

        while (!onCell.equals(endCell)) {
            onCell = frontier.removeFirst();

            ArrayList<MazeGrid.Cell> neighbours = new ArrayList<>();
            if (!onCell.northWall()) {
                neighbours.add(mazeGrid.getCell(onCell.y() - 1, onCell.x()));
            }
            if (!onCell.southWall()) {
                neighbours.add(mazeGrid.getCell(onCell.y() + 1, onCell.x()));
            }
            if (!onCell.westWall()) {
                neighbours.add(mazeGrid.getCell(onCell.y(), onCell.x() - 1));
            }
            if (!onCell.eastWall()) {
                neighbours.add(mazeGrid.getCell(onCell.y(), onCell.x() + 1));
            }

            for (MazeGrid.Cell neighbour : neighbours) {
                if (explored.contains(neighbour)) {
                    continue;
                }
                explored.add(neighbour);
                frontier.add(neighbour);
                bfsPath.put(neighbour, onCell);
            }
        }

        while (!onCell.equals(startCell)) {
            onCell.partOfPath(true);
            mazeGrid.setCell(onCell);
            onCell = bfsPath.get(onCell);
        }

        onCell.partOfPath(true);
        mazeGrid.setCell(onCell);

        return mazeGrid;
    }
}
