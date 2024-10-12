package backend.academy.amazed;

import lombok.Getter;
import lombok.Setter;

public class MazeGrid {
    private final int width;
    private final int height;
    private Cell[][] cells;

    // Конструктор, создающий сетку лабиринта заданного размера
    MazeGrid(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[height][width];

        // Инициализация каждой клетки с начальными стенами
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[y][x] = new Cell();
            }
        }
    }

    // Метод для построения строки с отображением лабиринта
    String draw() {
        StringBuilder builder = new StringBuilder();

        // Проходим по каждой строке клеток
        for (int y = 0; y < height; y++) {
            // Верхняя часть клеток
            for (int x = 0; x < width; x++) {
                builder.append("██").append(cells[y][x].northWall() ? "██" : "  ").append("██");
            }
            builder.append("\n");

            // Средняя часть клеток (проход и сама клетка)
            for (int x = 0; x < width; x++) {
                builder.append(cells[y][x].westWall() ? "██" : "  ");
                builder.append("  ");
                builder.append(cells[y][x].eastWall() ? "██" : "  ");
            }
            builder.append("\n");

            // Нижняя часть клеток
            for (int x = 0; x < width; x++) {
                builder.append("██").append(cells[y][x].southWall() ? "██" : "  ").append("██");
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    void setInternal(int y, int x) {
        cells[y][x].isInternal(true);
        cells[y][x].isExternal(false);
    }

    boolean isInternal(int y, int x) {
        return cells[y][x].isInternal();
    }

    boolean isExternal(int y, int x) {
        return cells[y][x].isExternal();
    }

    void setFrontier(int y, int x) {
        cells[y][x].isFrontier(true);
    }

    void removeNorthWall(int y, int x) {
        cells[y][x].northWall(false);
    }

    void removeSouthWall(int y, int x) {
        cells[y][x].southWall(false);
    }

    void removeWestWall(int y, int x) {
        cells[y][x].westWall(false);
    }

    void removeEastWall(int y, int x) {
        cells[y][x].eastWall(false);
    }

    // Класс Cell, представляющий клетку лабиринта
    @Getter @Setter
    static class Cell {
        private boolean northWall = true;
        private boolean southWall = true;
        private boolean westWall = true;
        private boolean eastWall = true;

        private boolean isInternal = false;
        private boolean isFrontier = false;
        private boolean isExternal = true;
    }
}
