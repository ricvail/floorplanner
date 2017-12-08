/**
 * Created by RICVA on 08/12/2017.
 */
public class Solver {
    public static Solution solve (Problem problem){
        Solution solution = new Solution();
        solution.problemID = problem.problemID;
        solution.placements = new Solution.Placement[problem.numberOfRegions];

        for (int i = 0; i<problem.numberOfRegions; i++){
            Solution.Placement p = new Solution.Placement();
            solution.placements[i] = p;
            p.y=problem.tileHeight*i;
            p.h=problem.tileHeight;
            for (int c = 0; c<problem.cols; c++){
                if (problem.validLeft[c]){
                    p.x=c;
                    break;
                }
            }
            for (int c = problem.cols-1; c>=0; c--){
                if (problem.validRight[c]){
                    p.w= c - p.x + 1;
                    break;
                }
            }
        }

        return solution;
    }
}
