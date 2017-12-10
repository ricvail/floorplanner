import ExactSolver.ExactSolver;
import Model.Problem;
import Model.Solution;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;

/**
 * Created by RICVA on 08/12/2017.
 */
public class SolveAll {
    public static void main(String[] args) {
        final File folder = new File("./problems");
        ExecutorService executor = Executors.newCachedThreadPool();
        for (final File fileEntry : folder.listFiles()) {
            ProblemRunnable p = new ProblemRunnable();
            p.prob=fileEntry;
            executor.submit(p);
        }
    }

    public static class ProblemRunnable implements Runnable{

        public File prob;

        @Override
        public void run() {
            Problem p = Main.parseFile(prob);
            System.out.println("Problem "+ p.problemID+" has been parsed!");
            ExactSolver.solve(p);
        }
    }
}
