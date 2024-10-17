package backend.academy.amazed;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MazeGrid {

    private static final String WALL  = "⬜️";
    private static final String SPACE = "⬛️";
    private static final String START = "🟩";
    private static final String END   = "🟥";
    private static final String PATH  = "🟨";

    private Cell startCell;
    private Cell endCell;
    private final int width;
    private final int height;
    private final Cell[][] grid;

    public MazeGrid(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = new Cell[height][width];
        initializeGrid();
    }

    /** Инициализация сетки лабиринта */
    private void initializeGrid() {
        int cnt = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Cell(cnt++, i, j);
            }
        }
    }

    /** Метод для отображения лабиринта */
    public String draw() {
        StringBuilder builder = new StringBuilder();

        for (int y = 0; y < height; y++) {
            builder.append(drawNorthWalls(y));
            builder.append(drawCellContent(y));
        }

        builder.append(drawBottomWall());
        return builder.toString();
    }

    /** Отображение северных стен клеток */
    private String drawNorthWalls(int y) {
        StringBuilder builder = new StringBuilder();

        for (int x = 0; x < width; x++) {
            builder.append(WALL);
            builder.append(grid[y][x].northWall() ? WALL : getCellSymbol(grid[y - 1][x], grid[y][x], Direction.NORTH));
        }
        builder.append(WALL).append("\n");
        return builder.toString();
    }

    /** Отображение содержимого клеток */
    private String drawCellContent(int y) {
        StringBuilder builder = new StringBuilder();

        for (int x = 0; x < width; x++) {
            builder.append(grid[y][x].westWall() ? WALL : getCellSymbol(grid[y][x - 1], grid[y][x], Direction.WEST));
            builder.append(getCellDisplay(grid[y][x]));
        }
        builder.append(WALL).append("\n");
        return builder.toString();
    }

    /** Отображение нижней стены */
    private String drawBottomWall() {
        StringBuilder builder = new StringBuilder();
        for (int x = 0; x < width; x++) {
            builder.append(WALL).append(WALL);
        }
        builder.append(WALL).append("\n");
        return builder.toString();
    }

    /** Возвращает символ для отображения клетки */
    private String getCellDisplay(Cell cell) {
        if (cell.equals(startCell)) return START;
        if (cell.equals(endCell)) return END;
        if (cell.partOfPath()) return PATH;
        return SPACE;
    }

    /** Определяет символ для отображения связи между клетками */
    private String getCellSymbol(Cell prevCell, Cell currentCell, Direction direction) {
        if (prevCell != null && prevCell.partOfPath() && currentCell.partOfPath()) {
            return PATH;
        }
        return SPACE;
    }

    Cell getCell(int y, int x) {
        if (y < 0 || y >= height || x < 0 || x >= width) {
            return null;
        }
        return grid[y][x];
    }

    void setCell(Cell cell) {
        grid[cell.y()][cell.x()] = cell;
    }

    void startCell(Cell cell) {
        copyWalls(grid[cell.y()][cell.x()], cell);
        startCell = cell;
    }

    void endCell(Cell cell) {
        copyWalls(grid[cell.y()][cell.x()], cell);
        endCell = cell;
    }

    /** Копирование стен из одной клетки в другую */
    private void copyWalls(Cell source, Cell target) {
        target.northWall(source.northWall());
        target.southWall(source.southWall());
        target.westWall(source.westWall());
        target.eastWall(source.eastWall());
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

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Cell cell = (Cell) obj;
            return x == cell.x && y == cell.y;
        }
    }

    /** Перечисление для направлений */
    private enum Direction {
        NORTH, WEST
    }
}
