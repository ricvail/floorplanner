import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by RICVA on 08/12/2017.
 */
public class SolveAll {
    public static void main(String[] args) {
        final File folder = new File("./problems");
        ArrayList<File> files = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            Problem p = Main.parseFile(fileEntry);
            System.out.print("Solving problem "+p.problemID+ "...");
            Solution s = Solver.solve(p);
            System.out.println(" Done!");
            Main.writeSolution(s);
        }
    }
}
