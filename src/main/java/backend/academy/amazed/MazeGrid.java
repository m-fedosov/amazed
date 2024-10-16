package backend.academy.amazed;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MazeGrid {

    private final String WALL  = "⬜️";
    private final String SPACE = "⬛️";
    private final String START = "🟩";
    private final String END   = "🟥";

    @Setter
    private Cell startCell;
    @Setter
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
            for (int x = 0; x < width(); x++) {
                builder.append(WALL);
                if (grid[y][x].northWall())  {
                    builder.append(WALL);
                } else {
                    builder.append(SPACE);
                }
            }
            builder.append(WALL);
            builder.append("\n");
            for (int x = 0; x < width(); x++) {
                if (grid[y][x].westWall())  {
                    builder.append(WALL);
                } else {
                    builder.append(SPACE);
                }
                if (grid[y][x].equals(startCell)) {
                    builder.append(START);
                } else if (grid[y][x].equals(endCell)) {
                    builder.append(END);
                } else {
                    builder.append(SPACE);
                }
            }
            if (grid[y][width() - 1].eastWall())  {
                builder.append(WALL);
            } else {
                builder.append(SPACE);
            }
            builder.append("\n");
        }

        for (int x = 0; x < width(); x++) {
            builder.append(WALL);
            if (grid[height() - 1][x].southWall())  {
                builder.append(WALL);
            } else {
                builder.append(SPACE);
            }
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

    @Getter @Setter
    static class Cell {
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
