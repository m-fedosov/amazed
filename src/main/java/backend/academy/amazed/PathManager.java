package backend.academy.amazed;

public class PathManager {
    MazeGrid findPath(int pathAlgorithmChoice, MazeGrid mazeGrid) throws Exception {
        PathFinder pathFinder = switch (pathAlgorithmChoice) {
            case 1 -> new PathFinderBFS();
            case 2 -> new PathFinderAStar();
            default -> throw new Exception("Неверный алгоритм поиска пути");
        };
        return pathFinder.find(mazeGrid);
    }
}
