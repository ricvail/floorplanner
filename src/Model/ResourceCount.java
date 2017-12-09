package Model;

/**
 * Created by RICVA on 08/12/2017.
 */
public class ResourceCount {

    private int [] list;

    public ResourceCount(){
        list = new int [5];
    }

    public int get(Resource e){
        return list[getIndex(e)];
    }

    public void reset(){
        for (int i = 0; i<5; i++){
            list[i]=0;
        }
    }

    public void set(Resource e, int i){
        list[getIndex(e)]=i;
    }

    public void increment(Resource e){
        list[getIndex(e)]++;
    }

    public int getIndex(Resource e){
        switch (e){
            case N:
                return 0;
            case C:
                return 1;
            case B:
                return 2;
            case D:
                return 3;
            case F:
                return 4;
        }
        return -1;
    }
}
