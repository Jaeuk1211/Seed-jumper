package gachon.mpclass.seedjumper;

//운동 기록: 횟수, 칼로리, 시간

/*
몸무게에 따른 초당 칼로리 소모량
45~48(kg): 0.13 (kcal)
49~54 : 0.15
55~59 : 0.16
60~65 : 0.18
66~71 : 0.2
72~77 : 0.22
78~82 : 0.23
83~88 : 0.25
89~94 : 0.27
95~99 : 0.28
100~ : 0.3
 */

public class Record {

    private int count;
    private double calorie; //55kg(기본) 기준 1분에 10kcal 소모
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

    public void setCalorie(int time, int weight){
        if(weight<49) {
            this.calorie = time * 0.13;
        } else if (weight < 55) {
            this.calorie = time * 0.15;
        } else if (weight < 60) {
            this.calorie = time * 0.16;
        } else if (weight < 66) {
            this.calorie = time * 0.18;
        } else if (weight < 72) {
            this.calorie = time * 0.2;
        } else if (weight < 78) {
            this.calorie = time * 0.22;
        } else if (weight < 83) {
            this.calorie = time * 0.23;
        } else if (weight < 89) {
            this.calorie = time * 0.25;
        } else if (weight < 95) {
            this.calorie = time * 0.27;
        } else if (weight < 100) {
            this.calorie = time * 0.28;
        } else
            this.calorie = time * 0.3;
    }

    public void setTime(int time){
        this.time = time;
    }

}
