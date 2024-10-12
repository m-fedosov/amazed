package backend.academy.amazed;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MazeGenerator {
    int width;
    int height;
    Point startPoint;
    Point endPoint;
    private final Random random = new Random();
    MazeGrid grid;
    Console console = new Console();

    @SuppressWarnings("MagicNumber")
    int minSize = 10;
    @SuppressWarnings("MagicNumber")
    int maxSize = 500;

    public void setUp() {
        String welcomeText = """
            Добро пожаловать!
            Это консольная программа для генерации лабиринтов и поиска пути в них.
            Программа способна генерировать лабиринты различной сложности и размеров,
            а также предоставляет несколько методов поиска пути
            от заданной точки А (начала) к точке Б (конца).

            Для генерации лабиринта нужно ввести следующие параметры.
            """;
        console.print(welcomeText);

        console.print("Ширина генерируемого лабиринта");
        width = console.input(minSize, maxSize);
        console.print("Высота генерируемого лабиринта");
        height = console.input(minSize, maxSize);

        grid = new MazeGrid(width, height);

        String algorithmChoiceText = """
            Выберите алгоритм генерации лабиринта:

            1. Алгоритм Прима
            2. Алгоритм Краскала
            """;
        console.print(algorithmChoiceText);
        @SuppressWarnings("MagicNumber")
        int algorithmChoice = console.input(1, 3);

        console.print("Начало лабиринта:");
        int x1 = console.input(1, width) - 1;
        int y1 = console.input(1, height) - 1;
        startPoint = new Point(x1, y1);

        console.print("Конец лабиринта:");
        int x2 = console.input(1, width) - 1;
        int y2 = console.input(1, height) - 1;
        endPoint = new Point(x2, y2);

        // Проверка на совпадение начальной и конечной точек
        while (startPoint.equals(endPoint)) {
            console.print(
                "Начальная и конечная точки совпадают. Пожалуйста, выберите другие координаты для конца лабиринта."
            );
            x2 = console.input(1, width) - 1;
            y2 = console.input(1, height) - 1;
            endPoint = new Point(x2, y2);
        }
    }

    public void generate() {
        // Список граничных ячеек
        List<Point> frontierCells = new ArrayList<>();

        // Выбираем случайную начальную точку
        int startX = random.nextInt(width);
        int startY = random.nextInt(height);

        // Делаем эту ячейку внутренней
        makeCellInternal(startX, startY, frontierCells);

        // Пока есть граничные клетки, продолжаем генерацию
        while (!frontierCells.isEmpty()) {
            // Выбираем случайную граничную клетку
            Point frontierCell = frontierCells.remove(random.nextInt(frontierCells.size()));

            // Получаем соседей этой клетки, которые уже внутренние
            List<Point> internalNeighbors = getInternalNeighbors(frontierCell.x, frontierCell.y);

            if (!internalNeighbors.isEmpty()) {
                // Выбираем случайного внутреннего соседа и соединяем клетки
                Point neighbor = internalNeighbors.get(random.nextInt(internalNeighbors.size()));
                connectCells(frontierCell, neighbor);

                // Делаем выбранную граничную клетку внутренней
                makeCellInternal(frontierCell.x, frontierCell.y, frontierCells);
            }
        }
    }

    public void draw() {
        console.print(grid.draw());
    }

    // Превращаем клетку в внутреннюю и добавляем соседей в список граничных
    private void makeCellInternal(int x, int y, List<Point> frontierCells) {
        grid.setInternal(y, x);  // Метка, что клетка стала внутренней

        // Добавляем соседей как граничные
        for (Point neighbor : getExternalNeighbors(x, y)) {
            frontierCells.add(neighbor);
            grid.setFrontier(y, x);  // Метка, что клетка стала граничной
        }
    }

    // Соединяем две клетки (убираем стены между ними)
    private void connectCells(Point current, Point neighbor) {
        if (current.x == neighbor.x) {
            // Вертикальное соединение
            if (current.y > neighbor.y) {
                grid.removeNorthWall(current.y, current.x);
                grid.removeSouthWall(neighbor.y, neighbor.x);
            } else {
                grid.removeSouthWall(current.y, current.x);
                grid.removeNorthWall(neighbor.y, neighbor.x);
            }
        } else if (current.y == neighbor.y) {
            // Горизонтальное соединение
            if (current.x > neighbor.x) {
                grid.removeWestWall(current.y, current.x);
                grid.removeEastWall(neighbor.y, neighbor.x);
            } else {
                grid.removeEastWall(current.y, current.x);
                grid.removeWestWall(neighbor.y, neighbor.x);
            }
        }
    }

    // Возвращает соседей, которые являются внутренними
    private List<Point> getInternalNeighbors(int x, int y) {
        List<Point> neighbors = new ArrayList<>();

        if (x > 0 && grid.isInternal(y, x - 1)) neighbors.add(new Point(x - 1, y));
        if (x < width - 1 && grid.isInternal(y, x + 1)) neighbors.add(new Point(x + 1, y));
        if (y > 0 && grid.isInternal(y - 1, x)) neighbors.add(new Point(x, y - 1));
        if (y < height - 1 && grid.isInternal(y + 1, x)) neighbors.add(new Point(x, y + 1));

        return neighbors;
    }

    // Возвращает соседей, которые являются внешними
    private List<Point> getExternalNeighbors(int x, int y) {
        List<Point> neighbors = new ArrayList<>();

        if (x > 0 && grid.isExternal(y, x - 1)) neighbors.add(new Point(x - 1, y));
        if (x < width - 1 && grid.isExternal(y, x + 1)) neighbors.add(new Point(x + 1, y));
        if (y > 0 && grid.isExternal(y - 1, x)) neighbors.add(new Point(x, y - 1));
        if (y < height - 1 && grid.isExternal(y + 1, x)) neighbors.add(new Point(x, y + 1));

        return neighbors;
    }

    private static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
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
            Point point = (Point) obj;
            return x == point.x && y == point.y;
        }
    }
}
