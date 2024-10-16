package backend.academy.amazed;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MazeGrid {

    private final String WALL  = "⬜️";
    private final String SPACE = "⬛️";
    private final String START = "🟩";
    private final String END   = "🟥";
    private final String PATH  = "🟨";

    private Cell startCell;
    private Cell endCell;
    private final int width;
    private final int height;
    private final Cell[][] grid;

    // Конструктор, создающий сетку лабиринта заданного размера
    public MazeGrid(int height, int width) {
        this.height = height;
        this.width = width;
        grid = new Cell[height][width];

        int cnt = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Cell(cnt++, i, j);
            }
        }
    }

    // Метод для построения строки с отображением лабиринта
    public String draw() {
        StringBuilder builder = new StringBuilder();

        // Проходим по каждой клетке
        for (int y = 0; y < height(); y++) {
            // нарисовать северную границу клетки
            for (int x = 0; x < width(); x++) {
                builder.append(WALL);
                if (grid[y][x].northWall())  {
                    builder.append(WALL);
                } else if (grid[y][x].partOfPath() && grid[y - 1][x].partOfPath()) {
                    builder.append(PATH);
                } else {
                    builder.append(SPACE);
                }
            }
            builder.append(WALL);
            builder.append("\n");

            // нарисовать середину клетки
            for (int x = 0; x < width(); x++) {
                if (grid[y][x].westWall())  {
                    builder.append(WALL);
                } else if (grid[y][x].partOfPath() && grid[y][x - 1].partOfPath()) {
                    builder.append(PATH);
                } else {
                    builder.append(SPACE);
                }
                if (grid[y][x].equals(startCell)) {
                    builder.append(START);
                } else if (grid[y][x].equals(endCell)) {
                    builder.append(END);
                } else if (grid[y][x].partOfPath()) {
                    builder.append(PATH);
                } else {
                    builder.append(SPACE);
                }
            }
            builder.append(WALL);
            builder.append("\n");
        }

        // нарисовать низ
        for (int x = 0; x < width(); x++) {
            builder.append(WALL).append(WALL);
        }
        builder.append(WALL);
        builder.append("\n");

        return builder.toString();
    }

    Cell getCell(int y, int x) {
        if (y < 0 || y >= height() || x < 0 || x >= width()) {
            return null;
        }
        return grid[y][x];
    }

    void setCell(Cell cell) {
        grid[cell.y()][cell.x()] = cell;
    }

    void startCell(Cell cell) {
        cell.northWall(grid[cell.y()][cell.x()].northWall);
        cell.southWall(grid[cell.y()][cell.x()].southWall);
        cell.westWall(grid[cell.y()][cell.x()].westWall);
        cell.eastWall(grid[cell.y()][cell.x()].eastWall);
        startCell = cell;
    }

    void endCell(Cell cell) {
        cell.northWall(grid[cell.y()][cell.x()].northWall);
        cell.southWall(grid[cell.y()][cell.x()].southWall);
        cell.westWall(grid[cell.y()][cell.x()].westWall);
        cell.eastWall(grid[cell.y()][cell.x()].eastWall);
        endCell = cell;
    }

    @Getter @Setter
    static class Cell {
        private boolean partOfPath = false;
        private boolean northWall = true;
        private boolean southWall = true;
        private boolean westWall = true;
        private boolean eastWall = true;

        int type;
        int y;
        int x;

        Cell(int type, int y, int x) {
            this.type = type;
            this.y = y;
            this.x = x;
        }

        @SuppressWarnings("EqualsHashCode")
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Cell cell = (Cell) obj;
            return x == cell.x && y == cell.y;
        }
    }
}
