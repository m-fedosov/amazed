package backend.academy.amazed.pathfinder;

import backend.academy.amazed.skeleton.MazeGrid;

public class PathManager {
    public MazeGrid findPath(int pathAlgorithmChoice, MazeGrid mazeGrid) throws Exception {
        PathFinder pathFinder = switch (pathAlgorithmChoice) {
            case 1 -> new PathFinderBFS();
            case 2 -> new PathFinderAStar();
            default -> throw new Exception("Неверный алгоритм поиска пути");
        };
        return pathFinder.find(mazeGrid);
    }
}
