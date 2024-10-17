package backend.academy.amazed;

import java.util.ArrayList;
import java.util.HashMap;

public class PathFinderBFS implements PathFinder {

    /** Для BFS адоптировал под текущую задачу алгоритм из этого <a href="https://www.youtube.com/watch?app=desktop&v=D14YK-0MtcQ">видео'</a> */
    public MazeGrid find(MazeGrid mazeGrid) {
        MazeGrid.Cell startCell = mazeGrid.startCell();
        MazeGrid.Cell onCell = startCell;
        MazeGrid.Cell endCell = mazeGrid.endCell();

        ArrayList<MazeGrid.Cell> frontier = new ArrayList<>();
        frontier.add(onCell);
        ArrayList<MazeGrid.Cell> explored = new ArrayList<>();
        explored.add(onCell);
        HashMap<MazeGrid.Cell, MazeGrid.Cell> path = new HashMap<>();

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
                path.put(neighbour, onCell);
            }
        }

        /** Отрисовка пути в лабиринте */
        while (!onCell.equals(startCell)) {
            onCell.partOfPath(true);
            mazeGrid.setCell(onCell);
            onCell = path.get(onCell);
        }
        onCell.partOfPath(true);
        mazeGrid.setCell(onCell);

        return mazeGrid;
    }
}
