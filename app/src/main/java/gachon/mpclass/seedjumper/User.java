package gachon.mpclass.seedjumper;

import java.io.Serializable;

public class User implements Serializable {

    private String email;
    private String pwd;
    private String name;
    private String id;

    public User() {
    }

    public User(String id, String email, String name, String pwd) {
        this.email = email;
        this.name = name;
        this.pwd = pwd;
        this.id = id;
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

    public String getId() { return id;  }


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

    public void setId(String id) { this.id = id;
    }

}
