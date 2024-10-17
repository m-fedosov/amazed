package backend.academy.amazed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MazeGeneratorKruskal implements MazeGenerator {
    MazeGrid mazeGrid;
    /** Путь и ячейки в нём */
    HashMap<Integer, ArrayList<MazeGrid.Cell>> pathToCells;

    /** Изначально каждая точка это путь длинной 1*/
    private HashMap<Integer, ArrayList<MazeGrid.Cell>> initializePathToCells(MazeGrid mazeGrid) {
        HashMap<Integer, ArrayList<MazeGrid.Cell>> pathToCells = new HashMap<>();
        for (int i = 0; i < mazeGrid.height(); i++) {
            for (int j = 0; j < mazeGrid.width(); j++) {
                MazeGrid.Cell cell = mazeGrid.getCell(i, j);
                ArrayList<MazeGrid.Cell> pathToCellsList = new ArrayList<>();
                pathToCellsList.add(cell);
                pathToCells.put(cell.type(), pathToCellsList);
            }
        }
        return pathToCells;
    }

    /** Чтобы текучесь лабиринта была выше,
     * стоит объединять самые короткие отрезки пути,
     * пока они не образуют один путь */
    private ArrayList<MazeGrid.Cell> getCellsWhereMinPathLength() {
        ArrayList<Integer> keys = new ArrayList<>(pathToCells.keySet());
        Collections.shuffle(keys);
        int minPathLength = mazeGrid.width() * mazeGrid.height();
        for (int k : keys) {
            minPathLength = Math.min(minPathLength, pathToCells.get(k).size());
        }
        for (int k : keys) {
            if (pathToCells.get(k).size() == minPathLength) {
                return pathToCells.get(k);
            }
        }
        throw new RuntimeException("No cells with min path length");
    }

    /** Ячейку лабиринта можно объединить с другой ячейкой, если они не являются частью одного пути */
    private MazeGrid.Cell findMergeableNeighbor(MazeGrid.Cell cell) {
        ArrayList<MazeGrid.Cell> neighbors = findNeighbors(cell);
        Collections.shuffle(neighbors);
        for (MazeGrid.Cell neighbor : neighbors) {
            if (neighbor.type() != cell.type()) {
                return neighbor;
            }
        }
        return null;
    }

    private ArrayList<MazeGrid.Cell> findNeighbors(MazeGrid.Cell cell) {
        ArrayList<MazeGrid.Cell> neighbors = new ArrayList<>();
        addNeighborIfValid(neighbors, mazeGrid.getCell(cell.y() - 1, cell.x()));  // north
        addNeighborIfValid(neighbors, mazeGrid.getCell(cell.y() + 1, cell.x()));  // south
        addNeighborIfValid(neighbors, mazeGrid.getCell(cell.y(), cell.x() - 1));  // west
        addNeighborIfValid(neighbors, mazeGrid.getCell(cell.y(), cell.x() + 1));  // east
        return neighbors;
    }

    private void addNeighborIfValid(ArrayList<MazeGrid.Cell> neighbors, MazeGrid.Cell neighbor) {
        if (neighbor != null) {
            neighbors.add(neighbor);
        }
    }

    /** Объединять можно только ячейки, которые не являются частью того же пути */
    private void mergeCells(MazeGrid.Cell fromCell, MazeGrid.Cell toCell) {
        int cellToMergeWithType = toCell.type;
        for (MazeGrid.Cell c : pathToCells.get(cellToMergeWithType)) {
            c.type(fromCell.type);
            mazeGrid.setCell(c);
            pathToCells.get(fromCell.type).add(c);
        }
        pathToCells.remove(cellToMergeWithType);
        updateWalls(fromCell, toCell);
    }

    /** Каждая ячейка содержит в себе стены */
    private void updateWalls(MazeGrid.Cell fromCell, MazeGrid.Cell toCell) {
        if (toCell.y() > fromCell.y()) {
            fromCell.southWall(false);
            toCell.northWall(false);
        } else if (toCell.y() < fromCell.y()) {
            fromCell.northWall(false);
            toCell.southWall(false);
        } else if (toCell.x() < fromCell.x()) {
            fromCell.westWall(false);
            toCell.eastWall(false);
        } else if (toCell.x() > fromCell.x()) {
            fromCell.eastWall(false);
            toCell.westWall(false);
        }
        mazeGrid.setCell(fromCell);
        mazeGrid.setCell(toCell);
    }

    /** Использовал алгоритм Краскала из
     * <a href="https://weblog.jamisbuck.org/2011/1/3/maze-generation-kruskal-s-algorithm.html">этой статьи'</a>
     */
    public MazeGrid generate(MazeGrid mazeGrid) {
        this.mazeGrid = mazeGrid;
        pathToCells = initializePathToCells(mazeGrid);
        while (pathToCells.keySet().size() != 1) {
            var mergeOneOfCells = getCellsWhereMinPathLength();
            for (MazeGrid.Cell c : mergeOneOfCells) {
                MazeGrid.Cell cellToMergeWith = findMergeableNeighbor(c);
                if (cellToMergeWith != null) {
                    mergeCells(c, cellToMergeWith);
                    break;
                }
            }
        }
        return mazeGrid;
    }
}
