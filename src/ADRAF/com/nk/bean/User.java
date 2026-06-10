package ADRAF.com.nk.bean;

public class User {
    private String name;
    private String pwd;
    private String okpwd;
    private String allergy;
    private String role;
    private String status;

    public User() {
    }

    public User(String name, String pwd, String okpwd, String allergy) {
        this.name = name;
        this.pwd = pwd;
        this.okpwd = okpwd;
        this.allergy = allergy;
    }


    public User(String name, String pwd, String status) {
        this.name = name;
        this.pwd = pwd;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getOkpwd() {
        return okpwd;
    }

    public void setOkpwd(String okpwd) {
        this.okpwd = okpwd;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
