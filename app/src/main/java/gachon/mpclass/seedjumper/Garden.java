package gachon.mpclass.seedjumper;

public class Garden {

    private String badge;
    private String gardenInfo;

    public Garden(String flower, String gardenInfo){
        this.badge = badge;
        this.gardenInfo = gardenInfo;
    }

    public String getBadge(){
        return badge;
    }

    public String getGardenInfo(){
        return gardenInfo;
    }

    public void setBadge(String badge){
        this.badge = badge;
    }

    public void setGardenInfo(String gardenInfo){
        this.gardenInfo = gardenInfo;
    }


}
