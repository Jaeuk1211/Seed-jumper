package gachon.mpclass.seedjumper;

public class User {

    private String email;
    private String pwd;
    private String name;

    public User() {
    }

    public User(String email, String name, String pwd) {
        this.email = email;
        this.name = name;
        this.pwd = pwd;
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

}
