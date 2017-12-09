import Model.Problem;
import Model.Region;
import Model.Solution;

/**
 * Created by RICVA on 08/12/2017.
 */
public class NaiveSolver {
    public static Solution solve (Problem problem){
        Solution solution = new Solution();
        solution.problem = problem;
        solution.regions = new Region[problem.numberOfRegions];

        for (int i = 0; i<problem.numberOfRegions; i++){
            Region p = new Region(problem.regionDataArray[i], solution);
            solution.regions[i] = p;
            p.setY(problem.tileHeight*i);
            p.setH(problem.tileHeight);
            for (int c = 0; c<problem.cols; c++){
                if (problem.validLeft[c]){
                    p.setX(c);
                    break;
                }
            }
            for (int c = problem.cols-1; c>=0; c--){
                if (problem.validRight[c]){
                    p.setX2(c);
                    break;
                }
            }
        }

        return solution;
    }
}
