package backend.academy.amazed;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        MazeSetup mazeSetup = new MazeSetup();
        mazeSetup.setUp();
    }
}
