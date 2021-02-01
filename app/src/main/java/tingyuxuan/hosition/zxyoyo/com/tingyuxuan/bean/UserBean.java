package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/2.
 */

public class UserBean implements Serializable {
    int id;
    String name;
    String sex;
    String major;
    int classnum;
    int type;
    int age;
    String cover;
    int phone;
    String email;
    String jointime;
    String password;
    public UserBean(){

    }

    public UserBean(int id, String name, String sex, String major, int classnum, int type, int age, String cover, int phone, String email, String jointime, String password) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.major = major;
        this.classnum = classnum;
        this.type = type;
        this.age = age;
        this.cover = cover;
        this.phone = phone;
        this.email = email;
        this.jointime = jointime;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getClassnum() {
        return classnum;
    }

    public void setClassnum(int classnum) {
        this.classnum = classnum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJointime() {
        return jointime;
    }

    public void setJointime(String jointime) {
        this.jointime = jointime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
