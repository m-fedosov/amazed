package backend.academy.amazed;

public class MazeProgram {
    private static final int MIN_SIZE = 4;
    private static final int MAX_SIZE = 40;

    private MazeGrid mazeGrid;

    @SuppressWarnings("MultipleStringLiterals")
    public void run() throws Exception {
        Console console = new Console();
        printWelcomeMessage(console);

        MazeInputHandler inputHandler = new MazeInputHandler(console, MIN_SIZE, MAX_SIZE);
        MazeManager mazeManager = new MazeManager();
        PathManager pathManager = new PathManager();

        mazeGrid = new MazeGrid(inputHandler.height(), inputHandler.width());

        int algorithmChoice = inputHandler.promptForAlgorithm(
            "Выберите алгоритм генерации лабиринта",
            "Алгоритм Прима",
            "Алгоритм Краскала"
        );
        mazeGrid = mazeManager.generateMaze(algorithmChoice, mazeGrid);

        MazeGrid.Cell startCell = inputHandler.promptForCell("Начало лабиринта:");
        MazeGrid.Cell endCell = inputHandler.promptForCell("Конец лабиринта:");
        while (startCell.equals(endCell)) {
            console.print("Начальная и конечная точки совпадают. Выберите другие координаты.");
            endCell = inputHandler.promptForCell("Конец лабиринта:");
        }
        mazeGrid.startCell(startCell);
        mazeGrid.endCell(endCell);

        int pathAlgorithmChoice = inputHandler.promptForAlgorithm(
            "Выберите алгоритм поиска пути",
            "BFS",
            "A*"
        );
        mazeGrid = pathManager.findPath(pathAlgorithmChoice, mazeGrid);

        console.print(mazeGrid.draw());
    }

    private void printWelcomeMessage(Console console) {
        String welcomeText = """
            Добро пожаловать!
            Это консольная программа для генерации лабиринтов и поиска пути в них.
            Программа способна генерировать лабиринты различной сложности и размеров,
            а также предоставляет несколько методов поиска пути
            от заданной точки А (начала) к точке Б (конца).

            Для генерации лабиринта нужно ввести следующие параметры.
            """;
        console.print(welcomeText);
    }
}
