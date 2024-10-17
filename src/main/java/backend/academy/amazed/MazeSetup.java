package backend.academy.amazed;

import lombok.Getter;

public class MazeSetup {
    int width;
    int height;
    @Getter
    MazeGrid mazeGrid;

    @SuppressWarnings("MagicNumber")
    int minSize = 4;
    @SuppressWarnings("MagicNumber")
    int maxSize = 40;

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
        mazeGrid = new MazeGrid(height, width);

        String algorithmChoiceText = """
            Выберите алгоритм генерации лабиринта:

            1. Алгоритм Прима
            2. Алгоритм Краскала
            """;
        console.print(algorithmChoiceText);
        @SuppressWarnings("MagicNumber")
        int algorithmChoice = console.input(1, 2);

        if (algorithmChoice == 1) {
            MazeGeneratorPrim mazeGenerator = new MazeGeneratorPrim();
            mazeGrid = mazeGenerator.generate(mazeGrid);
        } else if (algorithmChoice == 2) {
            MazeGeneratorKruskal mazeGenerator = new MazeGeneratorKruskal();
            mazeGrid = mazeGenerator.generate(mazeGrid);
        }

        console.print("Начало лабиринта:");
        int y1 = console.input(0, height - 1);
        int x1 = console.input(0, width - 1);
        MazeGrid.Cell startCell = new MazeGrid.Cell(0, y1, x1);

        console.print("Конец лабиринта:");
        int y2 = console.input(0, height - 1);
        int x2 = console.input(0, width - 1);
        MazeGrid.Cell endCell = new MazeGrid.Cell(0, y2, x2);

        // Проверка на совпадение начальной и конечной точек
        while (startCell.equals(endCell)) {
            console.print(
                "Начальная и конечная точки совпадают. Пожалуйста, выберите другие координаты для конца лабиринта."
            );
            x2 = console.input(0, width - 1);
            y2 = console.input(0, height - 1);
            endCell = new MazeGrid.Cell(0, y2, x2);
        }

        mazeGrid.startCell(startCell);
        mazeGrid.endCell(endCell);

        String pathAlgorithmChoiceText = """
            Выберите алгоритм поиска пути:

            1. BFS
            2. А*
            """;
        console.print(pathAlgorithmChoiceText);
        @SuppressWarnings("MagicNumber")
        int pathAlgorithmChoice = console.input(1, 2);

        if (pathAlgorithmChoice == 1) {
            PathFinderBFS pathFinder = new PathFinderBFS();
            mazeGrid = pathFinder.find(mazeGrid);
        } else if (pathAlgorithmChoice == 2) {
            PathFinderAStar pathFinder = new PathFinderAStar();
            mazeGrid = pathFinder.find(mazeGrid);
        }

        console.print(mazeGrid.draw());
    }
}
