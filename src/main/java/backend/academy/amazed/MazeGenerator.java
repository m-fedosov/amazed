package backend.academy.amazed;

public class MazeGenerator {
    int width;
    int height;
    Point startPoint;
    Point endPoint;

    @SuppressWarnings("MagicNumber")
    int minSize = 10;
    @SuppressWarnings("MagicNumber")
    int maxSize = 500;

    public void setUp() {
        Console console = new Console();
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

        String algorithmChoiceText = """
            Выберите алгоритм генерации лабиринта:

            1. Алгоритм Прима
            2. Алгоритм Краскала
            """;
        console.print(algorithmChoiceText);
        @SuppressWarnings("MagicNumber")
        int algorithmChoice = console.input(1, 3);

        MazeGrid mazeGrid = new MazeGrid(width, height);
        console.print(mazeGrid.draw());

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
