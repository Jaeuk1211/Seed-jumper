package gachon.mpclass.seedjumper;

//운동 기록: 횟수, 칼로리, 시간
public class Record {

    private int count;
    private double calorie; //55kg 기준 1분에 10kcal 소모
    private int time;

    public Record(){}

    public Record(int count, int time){
        this.count = count;
        this.calorie = time * 0.16;
        this.time = time;
    }

    public int getCount(){
        return count;
    }

    public double getCalorie(){
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
