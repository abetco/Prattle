package edu.illinois.cs465.prattle;

public class User_data {
    private String Name;
    private String Password;
    private String Birthday;
    private String Email;

    User_data(String name,String password,String birthday,String email){
        this.Name = name;
        this.Password = password;
        this.Email = email;
        this.Birthday = birthday;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
