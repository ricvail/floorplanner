package Model;

/**
 * Created by RICVA on 08/12/2017.
 */
public class Solution {
    public Problem problem;
    public Region[] regions;

    public int getInterconnectionCost(){
        float cost = 0;
        for (int i = 0; i <regions.length; i++){
            if ( regions[i]== null || ! regions[i].active) continue;
            for (int j = i+1; j <regions.length; j++){
                if (regions[i]== null ||  ! regions[j].active) continue;
                cost+= problem.getInterconnectionValue(regions[i].data.ID, regions[j].data.ID) * problem.wireCost*
                       (Math.abs(regions[i].getXC() - regions[j].getXC()) +
                        Math.abs(regions[i].getYC() - regions[j].getYC()));
            }
        }
        return (int) cost;
    }

    public boolean isOverlapping(Region r){
        for (int i = 0; i <regions.length; i++) {
            if (regions[i] == null || !regions[i].active || regions[i].data.ID==r.data.ID) continue;
            Region r2 = regions[i];
            if (r.data.isStatic && r2.data.isStatic){
                if (r.getX() <= r2.getX2() && r.getX2() >= r2.getX() &&
                        r.getY() <= r2.getY2() && r.getY2() >= r2.getY()) return true;
            } else {
                if (r.getX() <= r2.getX2() && r.getX2() >= r2.getX() &&
                        r.getRY() <= r2.getRY2() && r.getRY2() >= r2.getRY()) return true;
            }
        }
        return false;
    }

    public int getScore (){
        int score = problem.maxPoints;
        for (int i = 0; i < regions.length; i++){
            if (regions[i]== null ||  ! regions[i].active) continue;
            int aCost = regions[i].getAreaCost();
            int wCost = regions[i].getIOCost();
            score -= aCost;
            score -= wCost;
        }
        int inter = getInterconnectionCost();
        score -= inter;
        return score;
    }

    public String toText(){
        String content = problem.problemID+"\n";
        for(int i = 0; i< regions.length; i++){
            Region p = regions[i];
            content+= (p.getX() +1) +" " + (p.getY() + 1) + " " + p.getW() + " " + p.getH()+ "\n";
        }
        return content;
    }
}
