package backend.academy.amazed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MazeGeneratorKruskal {
    MazeGrid mazeGrid;
    HashMap<Integer, ArrayList<MazeGrid.Cell>> pathToCells;

    MazeGrid.Cell northCell;
    MazeGrid.Cell southCell;
    MazeGrid.Cell westCell;
    MazeGrid.Cell eastCell;

    private HashMap<Integer, ArrayList<MazeGrid.Cell>> getPathToCells(MazeGrid mazeGrid) {
        HashMap<Integer, ArrayList<MazeGrid.Cell>> pathToCells = new HashMap<>();
        for (int i = 0; i < mazeGrid.height(); i++) {
            for (int j = 0; j < mazeGrid.width(); j++) {
                ArrayList<MazeGrid.Cell> pathToCellsList = new ArrayList<>();
                MazeGrid.Cell onCell = mazeGrid.getCell(i, j);
                pathToCellsList.add(onCell);
                pathToCells.put(onCell.type(), pathToCellsList);
            }
        }
        return pathToCells;
    }

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

    private MazeGrid.Cell getRandomCellToMerge(MazeGrid.Cell onCell, MazeGrid mazeGrid) {
        northCell = mazeGrid.getCell(onCell.y() - 1, onCell.x());
        southCell = mazeGrid.getCell(onCell.y() + 1, onCell.x());
        westCell  = mazeGrid.getCell(onCell.y(), onCell.x() - 1);
        eastCell  = mazeGrid.getCell(onCell.y(), onCell.x() + 1);

        var canMergeWithCell = new ArrayList<MazeGrid.Cell>();
        if (northCell != null && northCell.type != onCell.type) {
            canMergeWithCell.add(northCell);
        }
        if (southCell != null && southCell.type != onCell.type) {
            canMergeWithCell.add(southCell);
        }
        if (eastCell != null && eastCell.type != onCell.type ) {
            canMergeWithCell.add(eastCell);
        }
        if (westCell != null && westCell.type != onCell.type) {
            canMergeWithCell.add(westCell);
        }

        if (canMergeWithCell.isEmpty()) {
            return null;
        }

        Collections.shuffle(canMergeWithCell);
        return canMergeWithCell.getFirst();
    }

    private void mergeCellWithOther(MazeGrid.Cell onCell, MazeGrid.Cell cellToMergeWith) {
        int cellToMergeWithType = cellToMergeWith.type;
        for (MazeGrid.Cell c : pathToCells.get(cellToMergeWithType)) {
            c.type(onCell.type);
            mazeGrid.setCell(c);
            pathToCells.get(onCell.type).add(c);
        }
        pathToCells.remove(cellToMergeWithType);

        if (cellToMergeWith.y() > onCell.y()) { // south cell
            onCell.southWall(false);
            mazeGrid.setCell(onCell);
            cellToMergeWith.northWall(false);
            mazeGrid.setCell(cellToMergeWith);
        } else if (cellToMergeWith.y() < onCell.y()) { // north cell
            onCell.northWall(false);
            mazeGrid.setCell(onCell);
            cellToMergeWith.southWall(false);
            mazeGrid.setCell(cellToMergeWith);
        } else if (cellToMergeWith.x() < onCell.x()) { // west cell
            onCell.westWall(false);
            mazeGrid.setCell(onCell);
            cellToMergeWith.eastWall(false);
            mazeGrid.setCell(cellToMergeWith);
        } else if (cellToMergeWith.x() > onCell.x()) { // east cell
            onCell.eastWall(false);
            mazeGrid.setCell(onCell);
            cellToMergeWith.westWall(false);
            mazeGrid.setCell(cellToMergeWith);
        }
    }

    public MazeGrid generate(MazeGrid mazeGrid) {
        this.mazeGrid = mazeGrid;
        pathToCells = getPathToCells(mazeGrid);
        while (pathToCells.keySet().size() != 1) {
            var mergeOneOfCells = getCellsWhereMinPathLength();
            for (MazeGrid.Cell c : mergeOneOfCells) {
                MazeGrid.Cell cellToMergeWith = getRandomCellToMerge(c, mazeGrid);
                if (cellToMergeWith != null) {
                    mergeCellWithOther(c, cellToMergeWith);
                    break;
                }
            }
        }
        return mazeGrid;
    }
}
