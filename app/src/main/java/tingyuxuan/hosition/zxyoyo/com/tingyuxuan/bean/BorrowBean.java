package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean;

/**
 * Created by Administrator on 2018/4/7.
 */

public class BorrowBean {

    String time;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    int userid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;
    int duration;
    int state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;

    public BorrowBean(String time, String name, int duration, int state,int id) {
        this.time = time;
        this.name = name;
        this.duration = duration;
        this.state = state;
        this.id = id;
    }

    public BorrowBean(String time, int userid, String name, int duration, int state, int id) {
        this.time = time;
        this.userid = userid;
        this.name = name;
        this.duration = duration;
        this.state = state;
        this.id = id;
    }

    public BorrowBean() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
