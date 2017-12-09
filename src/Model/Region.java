package Model;

/**
 * Created by RICVA on 08/12/2017.
 */
public class Region {
    private int x;
    private int y;
    private int w;
    private int h;
    public Problem.RegionData data;
    public Problem p;
    public ResourceCount resourceCount;
    public boolean active;
    public Solution s;


    public Region (Problem.RegionData d, Solution s){
        active=true;
        this.s=s;
        this.p=s.problem;
        this.data=d;
        resourceCount=new ResourceCount();
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
        updateResourceCount();
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
        updateResourceCount();
    }
    public int getW() {
        return w;
    }
    public void setW(int w) {
        this.w = w;
        updateResourceCount();
    }
    public int getH() {
        return h;
    }
    public void setH(int h) {
        this.h = h;
        updateResourceCount();
    }
    public int getX2(){
        return x+w-1;
    }
    public void setX2(int x2){
        w = x2-x+1;
        updateResourceCount();
    }
    public int getY2(){
        return y+h-1;
    }
    public void setY2(int y2){
        h = y2-y+1;
        updateResourceCount();
    }
    public float getXC(){
        return (float)w/2+x;
    }
    public float getYC(){
        return (float)h/2+y;
    }
    public int getRY(){
        int a = y/p.tileHeight;
        return a*p.tileHeight;
    }
    public int getRY2(){
        int a = getY2()/p.tileHeight;
        return a*p.tileHeight + p.tileHeight-1;
    }
    public void updateResourceCount(){
        resourceCount.reset();
        //System.out.println("X: " + getX()+ " Y: " + getY()+ " W: " + getW()+ " H: " + getH());
        for (int row = y; row<=getY2(); row++){
            for (int col = x; col<=getX2(); col++){
                resourceCount.increment(p.FPGA[row][col]);
            }
        }
    }
    public boolean checkResourceCoverage(){
        return (resourceCount.get(Resource.C)>=data.requiredResources.get(Resource.C)&&
                resourceCount.get(Resource.B)>=data.requiredResources.get(Resource.B)&&
                resourceCount.get(Resource.D)>=data.requiredResources.get(Resource.D));
    }

    public void firstValidX (){
        setX(0);
        if (data.isStatic) return;
        else for (; x <p.cols; x++){
            if (p.validLeft[x]) {
                setX(x);
                return;
            }
        }
    }
    public boolean nextValidX (){
        x++;
        if (data.isStatic && x<p.cols) {
            setX(x);
            return true;
        }
        for (; x <p.cols; x++){
            if (p.validLeft[x]){
                setX(x);
                return true;
            }
        }
        firstValidX();
        return false;
    }
    public boolean nextValidY (){
        y++;
        if (y<p.rows) {
            setY(y);
            return true;
        }
        setY(0);
        return false;
    }
    public boolean nextValidW (){
        w++;
        if (data.isStatic && getX2()<p.cols) {
            setW(w);
            return true;
        }
        for (; getX2() <p.cols; w++){
            if (p.validRight[getX2()]){
                setW(w);
                return true;
            }
        }
        setW(1);
        return false;
    }

    public boolean findMinH(){
        if (h!=1)
            System.out.println("This might be a problem!");
        while (getY2()<p.rows-1){
            setH(getH()+1);
            if(checkForForbidden()||s.isOverlapping(this)) {
                setH(1); return false;
            }
            if (checkResourceCoverage()){
                return true;
            }
        }
        return false;
    }


    public boolean checkForForbidden(){
        return resourceCount.get(Resource.F)>0;
    }

    public int getAreaCost(){
        int c = resourceCount.get(Resource.C)*p.cCost;
        int b = resourceCount.get(Resource.B)*p.bCost;
        int d = resourceCount.get(Resource.D)*p.dCost;
        return ( c + b + d) *p.areaCost;
    }

    public int getIOCost(){
        float x = getXC(), y = getYC();
        float cost = 0;
        for (int i = 0; i<data.IOs; i++){
            float l = ( Math.abs(data.IOWires[i].destX - x) + Math.abs(data.IOWires[i].destY - y) );
            cost += data.IOWires[i].value * p.wireCost * l;
        }
        return (int) cost;
    }

}
