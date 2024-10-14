package backend.academy.amazed;

import lombok.Getter;
import lombok.Setter;

public class MazeGrid {
    @Getter
    private final int width;
    @Getter
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
                grid[i][j] = new Cell(cnt++);
            }
        }
    }

    // Метод для построения строки с отображением лабиринта
    public String draw() {
        StringBuilder builder = new StringBuilder();

        // Проходим по каждой клетке
        for (int y = 0; y < height() - 1; y++) {
            for (int x = 0; x < width() - 1; x++) {
                builder.append("██");
                if (grid[y][x].northWall())  {
                    builder.append("██");
                } else {
                    builder.append("  ");
                }
            }
            builder.append("██");
            builder.append("\n");
            for (int x = 0; x < width() - 1; x++) {
                if (grid[y][x].westWall())  {
                    builder.append("██");
                } else {
                    builder.append("  ");
                }
                builder.append("  ");
            }
            if (grid[y][width() - 1].eastWall())  {
                builder.append("██");
            } else {
                builder.append("  ");
            }
            builder.append("\n");
        }

        for (int x = 0; x < width() - 1; x++) {
            builder.append("██");
            if (grid[height() - 1][x].southWall())  {
                builder.append("██");
            } else {
                builder.append("  ");
            }
        }
        builder.append("██");
        builder.append("\n");

        return builder.toString();
    }

    @Getter @Setter
    static class Cell {
        private boolean northWall = true;
        private boolean southWall = true;
        private boolean westWall = true;
        private boolean eastWall = true;

        int type;

        Cell(int type) {
            this.type = type;
        }
    }
}
