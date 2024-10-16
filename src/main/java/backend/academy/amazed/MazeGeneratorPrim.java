package backend.academy.amazed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MazeGeneratorPrim {
    MazeGrid mazeGrid;

    private void makeConnection(MazeGrid.Cell first, MazeGrid.Cell second) {
        if (first.y() > second.y()) { // south cell
            first.northWall(false);
            mazeGrid.setCell(first);
            second.southWall(false);
            mazeGrid.setCell(second);
        } else if (first.y() < second.y()) { // north cell
            first.southWall(false);
            mazeGrid.setCell(first);
            second.northWall(false);
            mazeGrid.setCell(second);
        } else if (first.x() < second.x()) { // west cell
            first.eastWall(false);
            mazeGrid.setCell(first);
            second.westWall(false);
            mazeGrid.setCell(second);
        } else if (first.x() > second.x()) { // east cell
            first.westWall(false);
            mazeGrid.setCell(first);
            second.eastWall(false);
            mazeGrid.setCell(second);
        }
    }

    public MazeGrid generate(MazeGrid mazeGrid) {
        this.mazeGrid = mazeGrid;
        for (int i = 0; i < mazeGrid.height(); i++) {
            for (int j = 0; j < mazeGrid.width(); j++) {
                mazeGrid.setCell(mazeGrid.getCell(i, j).type(0));
            }
        }


        Random r = new Random();
        MazeGrid.Cell randomStart = new MazeGrid.Cell(1, r.nextInt(mazeGrid.height()), r.nextInt(mazeGrid.width()));
        ArrayList<MazeGrid.Cell> neighbours = new ArrayList<>();
        neighbours.add(randomStart);
        while (!neighbours.isEmpty()) {
            MazeGrid.Cell randomNeighbour = neighbours.remove(r.nextInt(neighbours.size()));
            mazeGrid.setCell(randomNeighbour.type(2));

            MazeGrid.Cell pathNorth = mazeGrid.getCell(randomNeighbour.y() - 1, (randomNeighbour.x()));
            MazeGrid.Cell pathSouth = mazeGrid.getCell(randomNeighbour.y() + 1, (randomNeighbour.x()));
            MazeGrid.Cell pathWest  = mazeGrid.getCell(randomNeighbour.y(), (randomNeighbour.x() - 1));
            MazeGrid.Cell pathEast  = mazeGrid.getCell(randomNeighbour.y(), (randomNeighbour.x() + 1));

            ArrayList<MazeGrid.Cell> connectPathTo = new ArrayList<>();
            if (pathNorth != null && pathNorth.type == 2) {
                connectPathTo.add(pathNorth);
            }
            if (pathSouth != null && pathSouth.type == 2) {
                connectPathTo.add(pathSouth);
            }
            if (pathWest != null && pathWest.type == 2) {
                connectPathTo.add(pathWest);
            }
            if (pathEast != null && pathEast.type == 2) {
                connectPathTo.add(pathEast);
            }

            if (!connectPathTo.isEmpty()) {
                Collections.shuffle(connectPathTo);
                MazeGrid.Cell pathTo = connectPathTo.getFirst();
                makeConnection(pathTo, randomNeighbour);
            }

            if (pathNorth != null && pathNorth.type == 0) {
                pathNorth.type(1);
                neighbours.add(pathNorth);
            }
            if (pathSouth != null && pathSouth.type == 0) {
                pathSouth.type(1);
                neighbours.add(pathSouth);
            }
            if (pathWest != null && pathWest.type == 0) {
                pathWest.type(1);
                neighbours.add(pathWest);
            }
            if (pathEast != null && pathEast.type == 0) {
                pathEast.type(1);
                neighbours.add(pathEast);
            }
        }

        return this.mazeGrid;
    }
}
