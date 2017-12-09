import ExactSolver.ExactSolver;
import Model.Problem;
import Model.Resource;
import Model.ResourceCount;
import Model.Solution;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by RICVA on 07/12/2017.
 */
public class Main {
    public static void main(String[] args) {
        Problem p = parseFile(selectProblemFile());
        ExactSolver.solve(p);
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

    public static Problem parseFile (File file){
        Problem problem = new Problem ();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            //PROBLEM ID
            problem.problemID = br.readLine();
            //MAX POINTS, AREA COST, WIRE COST
            String  [] line = br.readLine().split(" ");
            problem.maxPoints = Integer.parseInt(line [0]);
            problem.areaCost = Integer.parseInt(line [1]);
            problem.wireCost = Integer.parseInt(line [2]);
            //RESOURCES COST
            line = br.readLine().split(" ");
            problem.cCost = Integer.parseInt(line [0]);
            problem.bCost = Integer.parseInt(line [1]);
            problem.dCost = Integer.parseInt(line [2]);
            //FPGA SIZE
            line = br.readLine().split(" ");
            problem.rows = Integer.parseInt(line [0]);
            problem.cols = Integer.parseInt(line [1]);
            problem.FPGA = new Resource[problem.rows][problem.cols];
            problem.validLeft = new boolean[problem.cols];
            problem.validRight = new boolean[problem.cols];
            //TILE HEIGHT
            problem.tileHeight = Integer.parseInt(br.readLine());
            //FPGA MATRIX
            for (int r = 0; r < problem.rows; r++){
                line = br.readLine().split(" ");
                for (int c = 0; c < problem.cols; c++){
                    if (line[c].equalsIgnoreCase("-")){
                        problem.FPGA[r][c] = Resource.N;
                    } else if (line[c].equalsIgnoreCase("C")){
                        problem.FPGA[r][c] = Resource.C;
                    } else if (line[c].equalsIgnoreCase("B")){
                        problem.FPGA[r][c] = Resource.B;
                    } else if (line[c].equalsIgnoreCase("D")){
                        problem.FPGA[r][c] = Resource.D;
                    } else if (line[c].equalsIgnoreCase("F")){
                        problem.FPGA[r][c] = Resource.F;
                    }
                }
            }
            //VALID LEFT
            line = br.readLine().split(" ");
            for (int c = 0; c < problem.cols; c++){
                if (line[c].equalsIgnoreCase("0")){
                    problem.validLeft[c] = false;
                } else if (line[c].equalsIgnoreCase("1")){
                    problem.validLeft[c] = true;
                }
            }
            //VALID RIGHT
            line = br.readLine().split(" ");
            for (int c = 0; c < problem.cols; c++){
                if (line[c].equalsIgnoreCase("0")){
                    problem.validRight[c] = false;
                } else if (line[c].equalsIgnoreCase("1")){
                    problem.validRight[c] = true;
                }
            }
            //REGIONS
            problem.numberOfRegions = Integer.parseInt(br.readLine());
            problem.regionDataArray = new Problem.RegionData[problem.numberOfRegions];
            for (int r =0; r<problem.numberOfRegions; r++){
                Problem.RegionData rd = new Problem.RegionData();
                problem.regionDataArray[r] =rd;
                line = br.readLine().split(" ");
                rd.isStatic = !line[0].equalsIgnoreCase("P");
                rd.ID = r;
                rd.requiredResources=new ResourceCount();
                rd.requiredResources.set(Resource.C,Integer.parseInt(line [1]));
                rd.requiredResources.set(Resource.B,Integer.parseInt(line [2]));
                rd.requiredResources.set(Resource.D,Integer.parseInt(line [3]));
                rd.IOs = Integer.parseInt(line [4]);
                //IO WIRES
                rd.IOWires = new Problem.IOWire[rd.IOs];
                for (int w = 0; w<rd.IOs; w++){
                    rd.IOWires[w] = new Problem.IOWire();
                    line = br.readLine().split(" ");
                    rd.IOWires[w].destX = Integer.parseInt(line [0])-1;
                    rd.IOWires[w].destY = Integer.parseInt(line [1])-1;
                    rd.IOWires[w].value = Integer.parseInt(line [2]);
                }
            }
            //INTERCONNECTIONS
            problem.interconnections = new int[problem.numberOfRegions][problem.numberOfRegions];
            for (int r1 =0; r1<problem.numberOfRegions; r1++){
                line = br.readLine().split(" ");
                for (int r2 =0; r2<problem.numberOfRegions; r2++) {
                    problem.interconnections[r1][r2]=Integer.parseInt(line[r2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return problem;
    }

    public static File selectProblemFile() {
        System.out.println("List of problem files:");
        final File folder = new File("./problems");
        ArrayList<File> files = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            System.out.println(files.size()+") "+fileEntry.getName());
            files.add(fileEntry);
        }
        System.out.print("Please choose a number:");
        Scanner sc = new Scanner(System.in);
        int i = sc.nextInt();
        return (files.get(i));
    }


}
