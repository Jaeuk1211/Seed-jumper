package gachon.mpclass.seedjumper;

//운동 기록: 횟수, 칼로리, 시간
public class Record {

    private int count;
    private int calorie;
    private int time;

    public Record(){}

    public Record(int count, int calorie, int time){
        this.count = count;
        this.calorie = calorie;
        this.time = time;
    }

    public int getCount(){
        return count;
    }

    public int getCalorie(){
        return calorie;
    }

    public int getTime(){
        return time;
    }

    public void setCount(int count){
        this.count = count;
    }

    public void setCalorie(int calorie){
        this.calorie = calorie;
    }

    public void setTime(int time){
        this.time = time;
    }



}
