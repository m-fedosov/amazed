package backend.academy.amazed;

import lombok.Getter;
import lombok.Setter;

public class MazeGrid {
    private final int width;
    private final int height;
    private final Cell[][] grid;

    // Конструктор, создающий сетку лабиринта заданного размера
    public MazeGrid(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Cell[height][width];

        // Инициализация каждой клетки с начальными стенами
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new Cell();
            }
        }
    }

    // Метод для построения строки с отображением лабиринта
    public String draw() {
        StringBuilder builder = new StringBuilder();

        // Проходим по каждой строке клеток
        for (int y = 0; y < height; y++) {
            // Верхняя часть клеток
            for (int x = 0; x < width; x++) {
                builder.append("█").append(grid[y][x].northWall() ? "█" : " ").append("█");
            }
            builder.append("\n");

            // Средняя часть клеток (проход и сама клетка)
            for (int x = 0; x < width; x++) {
                builder.append(grid[y][x].westWall() ? "█" : " ");
                builder.append(" ");
                builder.append(grid[y][x].eastWall() ? "█" : " ");
            }
            builder.append("\n");

            // Нижняя часть клеток
            for (int x = 0; x < width; x++) {
                builder.append("█").append(grid[y][x].southWall() ? "█" : " ").append("█");
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    // Класс Cell, представляющий клетку лабиринта
    @Getter @Setter
    private static class Cell {
        private boolean northWall = true;
        private boolean southWall = true;
        private boolean westWall = true;
        private boolean eastWall = true;
    }
}
