package gachon.mpclass.seedjumper;

public class Record {

    private int count;
    private int calorie;

    public Record(){}

    public Record(int count, int calorie){
        this.count = count;
        this.calorie = calorie;
    }

    public int getCount(){
        return count;
    }

    public int getCalorie(){
        return calorie;
    }

    public void setCount(int count){
        this.count = count;
    }

    public void setCalorie(int calorie){
        this.calorie = calorie;
    }

}
