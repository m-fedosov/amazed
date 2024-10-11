package backend.academy.amazed;

public class MazeGenerator {
    int width, height;
    Point startPoint, endPoint;

    int MIN_SIZE = 10;
    int MAX_SIZE = 500;

    public void setUp() {
        Console console = new Console();
        String text = """
            Добро пожаловать!
            Это консольная программа для генерации лабиринтов и поиска пути в них.
            Программа способна генерировать лабиринты различной сложности и размеров,
            а также предоставляет несколько методов поиска пути
            от заданной точки А (начала) к точке Б (конца).

            Для генерации лабиринта нужно ввести следующие параметры.
            """;
        console.print(text);

        console.print("Ширина генерируемого лабиринта");
        width = console.input(MIN_SIZE, MAX_SIZE);
        console.print("Высота генерируемого лабиринта");
        height = console.input(MIN_SIZE, MAX_SIZE);

        // TODO: выбор алгоритма генерации лабиринта

        console.print("Начало лабиринта:");
        int x1 = console.input(1, width) - 1;
        int y1 = console.input(1, height) - 1;
        startPoint = new Point(x1, y1);

        console.print("Конец лабиринта:");
        int x2 = console.input(1, width) - 1;
        int y2 = console.input(1, height) - 1;
        endPoint = new Point(x2, y2);
    }

    private static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
