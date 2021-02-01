package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean;

public class FavorBean {
    int id;
    String time;
    int userid;
    String bookname;
    int state;


    public FavorBean(String time, int userid, String bookname, int state) {

        this.time = time;
        this.userid = userid;
        this.bookname = bookname;
        this.state = state;
    }

    public FavorBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
