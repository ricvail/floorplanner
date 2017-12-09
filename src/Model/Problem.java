package Model;

/**
 * Created by RICVA on 07/12/2017.
 */
public class Problem {
    public String problemID;
    public int maxPoints, areaCost, wireCost, cCost, bCost, dCost, tileHeight, rows, cols, numberOfRegions;
    public Resource [][] FPGA;
    public boolean [] validRight, validLeft;
    public RegionData[] regionDataArray;
    public int [][] interconnections;

    public static class RegionData {
        public boolean isStatic;
        public int ID, IOs;
        public ResourceCount requiredResources;
        public IOWire [] IOWires;
    }

    public static class IOWire{
        public float destX, destY, value;
    }

    public int getInterconnectionValue(int r1, int r2){
        if (r1 == r2) return 0;
        return interconnections[r1][r2]+interconnections[r2][r1];
    }

}
