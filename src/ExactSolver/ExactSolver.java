package ExactSolver;

import Model.Problem;
import Model.Region;
import Model.Solution;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ExactSolver {

    public static void solve (Problem p){
        int score = 0;
        Solution s = new Solution();
        s.problem= p;
        s.regions=new Region[p.numberOfRegions];
        for (int i=0; i<s.regions.length; i++){
            s.regions[i]=new Region(p.regionDataArray[i], s);
            s.regions[i].firstValidX();
            s.regions[i].setY(0);
            s.regions[i].setW(1);
            s.regions[i].setH(1);
            s.regions[i].active=false;
        }
        int i = 0;
        while (i>=0){
            Region c = s.regions [i];
            c.active=true;
            System.out.println("Activating region " + i);
            c.setH(1);
            do {
                //System.out.println("Region " + i + " X: " + c.getX());
                do {
                    //System.out.println("Region " + i + " X: " + c.getX()+ " Y: " + c.getY());
                    if (s.isOverlapping(c)) {
                        //System.out.println("Overlapping!");
                        continue;
                    }
                    while(c.data.ID==i &&c.nextValidW()){
                        //System.out.println("Region " + i + " X: " + c.getX()+ " Y: " + c.getY()+ " W: " + c.getW());
                        if (s.isOverlapping(c)){
                            //System.out.println("Overlapping!");
                            c.setW(1);
                            break;
                        }
                        if (c.findMinH()){
                            //System.out.println("Region " + i + " X: " + c.getX()+ " Y: " + c.getY()+ " W: " + c.getW()+ " H: " + c.getH());
                            if (i>=s.regions.length-1) {
                                int newScore= s.getScore();
                                System.out.println("New solution, score: " + newScore);
                                System.out.println(s.toText());
                                if (newScore>score){
                                    score=newScore;
                                    System.out.println("New best score");
                                    writeSolution(s);
                                }
                                c.setH(1);
                            }
                            else i++;
                        } else {
                            //System.out.println("Can't find H");
                            c.setH(1);
                            continue;
                        }

                    }
                }while (c.data.ID==i && c.nextValidY());
                /*if (c.data.ID!=i || c.nextValidX()) continue;
                else {
                    c.active=false;
                    i--;
                }*/
            } while (c.data.ID==i && c.nextValidX());
            if (c.data.ID==i) {
                c.active = false;
                i--;
            }
        } //Region
        System.out.println("Final score: " + score);
    }

    public static void writeSolution(Solution s){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./solutions/"+s.problem.problemID+".txt"))) {

            String content = s.toText();
            bw.write(content);

            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

}
