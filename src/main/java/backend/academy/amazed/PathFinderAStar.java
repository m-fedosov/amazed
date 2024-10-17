package backend.academy.amazed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class PathFinderAStar implements PathFinder {

    /** Для A* адоптировал под текущую задачу алгоритм из этого <a href="https://youtu.be/W9zSr9jnoqY?feature=shared">видео'</a> */
    public MazeGrid find(MazeGrid mazeGrid) {
        MazeGrid.Cell startCell = mazeGrid.startCell();
        MazeGrid.Cell endCell = mazeGrid.endCell();
        HashMap<MazeGrid.Cell, Integer> gScore = new HashMap<>();

        for (int i = 0; i < mazeGrid.height(); i++) {
            for (int j = 0; j < mazeGrid.width(); j++) {
                gScore.put(mazeGrid.getCell(i, j), Integer.MAX_VALUE);
            }
        }
        gScore.put(startCell, 0);

        HashMap<MazeGrid.Cell, Integer> fScore = new HashMap<>();
        for (int i = 0; i < mazeGrid.height(); i++) {
            for (int j = 0; j < mazeGrid.width(); j++) {
                fScore.put(mazeGrid.getCell(i, j), Integer.MAX_VALUE);
            }
        }
        fScore.put(startCell, h(startCell, endCell));

        PriorityQueue<CellGF> open = new PriorityQueue<>();
        open.add(new CellGF(h(startCell, endCell), h(startCell, endCell), startCell));

        HashMap<MazeGrid.Cell, MazeGrid.Cell> path = new HashMap<>();

        MazeGrid.Cell onCell = open.remove().cell;

        while (!onCell.equals(endCell)) {
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
                int tempGScore = gScore.get(onCell) + 1;
                int tempFScore = tempGScore + h(neighbour, endCell);

                if (tempFScore < fScore.get(neighbour)) {
                    gScore.put(neighbour, tempGScore);
                    fScore.put(neighbour, tempFScore);
                    open.add(new CellGF(tempFScore, h(neighbour, endCell), neighbour));
                    path.put(neighbour, onCell);
                }
            }

            onCell = open.remove().cell;
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

    private Integer h(MazeGrid.Cell cell1, MazeGrid.Cell cell2) {
        return Math.abs(cell1.x() - cell2.x()) + Math.abs(cell1.y() - cell2.y());
    }

    static class CellGF implements Comparable<CellGF> {
        int f;
        int g;
        MazeGrid.Cell cell;

        CellGF(int f, int g, MazeGrid.Cell cell) {
            this.f = f;
            this.g = g;
            this.cell = cell;
        }

        /** Интерфейс сравнения для Priority Queue */
        @Override
        public int compareTo(CellGF other) {
            return Integer.compare(this.f, other.f);
        }
    }
}
