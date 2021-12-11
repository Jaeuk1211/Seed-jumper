package gachon.mpclass.seedjumper;

//마이페이지 스케줄러에 저장되는 내용
public class Memo {

    private int planCalorie;
    private String content;

    public Memo(){}

    public Memo(int planCalorie, String content){
        this.planCalorie = planCalorie;
        this.content = content;
    }

    public int getPlanCalorie(){
        return planCalorie;
    }

    public String getContent(){
        return content;
    }

    public void setCount(int planCalorie){
        this.planCalorie = planCalorie;
    }

    public void setCalorie(String content){
        this.content = content;
    }


}
