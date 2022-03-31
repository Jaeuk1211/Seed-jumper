package gachon.mpclass.seedjumper;

import java.io.Serializable;

public class User implements Serializable {

    private String email;
    private String pwd;
    private String name;
    private int weight;

    public User() {
    }

    public User(String email, String name, String pwd, int weight) {
        this.email = email;
        this.name = name;
        this.pwd = pwd;
        this.weight = weight;
    }

    // getter
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }

    public int getWeight() {return weight;}


    //setter
    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setWeight(int weight) { this.weight = weight; }

}
