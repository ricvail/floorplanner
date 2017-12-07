import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by RICVA on 07/12/2017.
 */
public class Main {
    public static void main(String[] args) {
        Problem p = parseFile(selectProblemFile());
        System.out.println(p.FPGA);
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
            problem.FPGA = new Problem.Element[problem.rows][problem.cols];
            problem.validLeft = new boolean[problem.cols];
            problem.validRight = new boolean[problem.cols];
            //TILE HEIGHT
            problem.tileHeight = Integer.parseInt(br.readLine());
            //FPGA MATRIX
            for (int r = 0; r < problem.rows; r++){
                line = br.readLine().split(" ");
                for (int c = 0; c < problem.cols; c++){
                    if (line[c].equalsIgnoreCase("-")){
                        problem.FPGA[r][c] = Problem.Element.N;
                    } else if (line[c].equalsIgnoreCase("C")){
                        problem.FPGA[r][c] = Problem.Element.C;
                    } else if (line[c].equalsIgnoreCase("B")){
                        problem.FPGA[r][c] = Problem.Element.B;
                    } else if (line[c].equalsIgnoreCase("D")){
                        problem.FPGA[r][c] = Problem.Element.D;
                    } else if (line[c].equalsIgnoreCase("F")){
                        problem.FPGA[r][c] = Problem.Element.F;
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
            problem.regions = new Problem.Region[problem.numberOfRegions];
            for (int r =0; r<problem.numberOfRegions; r++){
                problem.regions[r] = new Problem.Region();
                line = br.readLine().split(" ");
                problem.regions[r].isStatic = !line[0].equalsIgnoreCase("P");
                problem.regions[r].CLBs = Integer.parseInt(line [1]);
                problem.regions[r].BRAMs = Integer.parseInt(line [2]);
                problem.regions[r].DSPs = Integer.parseInt(line [3]);
                problem.regions[r].IOs = Integer.parseInt(line [4]);
                //IO WIRES
                problem.regions[r].IOWires = new Problem.IOWire[problem.regions[r].IOs];
                for (int w =0; w<problem.regions[r].IOs; w++){
                    problem.regions[r].IOWires[w] = new Problem.IOWire();
                    line = br.readLine().split(" ");
                    problem.regions[r].IOWires[w].destX = Integer.parseInt(line [0]);
                    problem.regions[r].IOWires[w].destY = Integer.parseInt(line [1]);
                    problem.regions[r].IOWires[w].value = Integer.parseInt(line [2]);
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
