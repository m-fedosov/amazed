package backend.academy.amazed.mazegenerator;

import backend.academy.amazed.skeleton.MazeGrid;
import java.util.ArrayList;
import java.util.Random;

public class MazeGeneratorPrim implements MazeGenerator {
    private MazeGrid mazeGrid;

    /** Типы ячеек в лабиринте */
    private static final int NOT_VISITED = 0;
    private static final int VISITED = 1;
    private static final int PATH = 2;

    /** Использовал алгоритм Прима из
     * <a href="https://weblog.jamisbuck.org/2011/1/10/maze-generation-prim-s-algorithm.html">этой статьи'</a>
     */
    @Override
    public MazeGrid generate(MazeGrid mazeGrid) {
        this.mazeGrid = mazeGrid;
        initializeMaze();

        Random random = new Random();
        MazeGrid.Cell randomStart = new MazeGrid.Cell(
            VISITED, random.nextInt(mazeGrid.height()), random.nextInt(mazeGrid.width())
        );
        ArrayList<MazeGrid.Cell> neighbors = new ArrayList<>();
        neighbors.add(randomStart);

        while (!neighbors.isEmpty()) {
            MazeGrid.Cell currentCell = selectRandomNeighbor(neighbors, random);
            mazeGrid.setCell(currentCell.type(2));

            ArrayList<MazeGrid.Cell> connectableCells = getConnectableCells(currentCell);
            if (!connectableCells.isEmpty()) {
                MazeGrid.Cell connectedCell = selectRandomNeighbor(connectableCells, random);
                connectCells(connectedCell, currentCell);
            }

            addValidNeighbors(currentCell, neighbors);
        }

        return mazeGrid;
    }

    /** Изначально каждая ячейка помечается индексом 0 - не посещена*/
    private void initializeMaze() {
        for (int i = 0; i < mazeGrid.height(); i++) {
            for (int j = 0; j < mazeGrid.width(); j++) {
                mazeGrid.setCell(mazeGrid.getCell(i, j).type(NOT_VISITED));
            }
        }
    }

    private MazeGrid.Cell selectRandomNeighbor(ArrayList<MazeGrid.Cell> cells, Random random) {
        return cells.remove(random.nextInt(cells.size()));
    }

    /** Ячейку типа ПУТЬ можно присоеднить только к другой ячейке типа ПУТЬ */
    private ArrayList<MazeGrid.Cell> getConnectableCells(MazeGrid.Cell cell) {
        ArrayList<MazeGrid.Cell> connectableCells = new ArrayList<>();
        addIfValidAndType(connectableCells, mazeGrid.getCell(cell.y() - 1, cell.x()), PATH); // North
        addIfValidAndType(connectableCells, mazeGrid.getCell(cell.y() + 1, cell.x()), PATH); // South
        addIfValidAndType(connectableCells, mazeGrid.getCell(cell.y(), cell.x() - 1), PATH); // West
        addIfValidAndType(connectableCells, mazeGrid.getCell(cell.y(), cell.x() + 1), PATH); // East
        return connectableCells;
    }

    /** Посещаем ячейки которые мы ещё не посетили */
    private void addValidNeighbors(MazeGrid.Cell cell, ArrayList<MazeGrid.Cell> neighbors) {
        addIfValidAndType(neighbors, mazeGrid.getCell(cell.y() - 1, cell.x()), NOT_VISITED); // North
        addIfValidAndType(neighbors, mazeGrid.getCell(cell.y() + 1, cell.x()), NOT_VISITED); // South
        addIfValidAndType(neighbors, mazeGrid.getCell(cell.y(), cell.x() - 1), NOT_VISITED); // West
        addIfValidAndType(neighbors, mazeGrid.getCell(cell.y(), cell.x() + 1), NOT_VISITED); // East
    }

    private void addIfValidAndType(ArrayList<MazeGrid.Cell> list, MazeGrid.Cell cell, int type) {
        if (cell != null && cell.type() == type) {
            list.add(cell);
            if (type == NOT_VISITED) {
                cell.type(VISITED);
            }
        }
    }

    /** Каждая ячейка содержит в себе стены */
    private void connectCells(MazeGrid.Cell first, MazeGrid.Cell second) {
        if (first.y() > second.y()) { // South cell
            first.northWall(false);
            second.southWall(false);
        } else if (first.y() < second.y()) { // North cell
            first.southWall(false);
            second.northWall(false);
        } else if (first.x() < second.x()) { // West cell
            first.eastWall(false);
            second.westWall(false);
        } else if (first.x() > second.x()) { // East cell
            first.westWall(false);
            second.eastWall(false);
        }
        mazeGrid.setCell(first);
        mazeGrid.setCell(second);
    }
}
