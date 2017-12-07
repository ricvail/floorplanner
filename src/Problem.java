import java.util.ArrayList;

/**
 * Created by RICVA on 07/12/2017.
 */
public class Problem {
    public String problemID;
    public int maxPoints, areaCost, wireCost, cCost, bCost, dCost, tileHeight, rows, cols, numberOfRegions;
    public Element [][] FPGA;
    public boolean [] validRight, validLeft;
    public Region [] regions;
    public int [][] interconnections;

    public static class Region {
        public boolean isStatic;
        public int CLBs, BRAMs, DSPs, IOs;
        public IOWire [] IOWires;
    }

    public static class IOWire{
        int destX, destY, value;
    }

    public enum Element {
        N, C, B, D, F;
    }
}
