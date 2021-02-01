package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean;

/**
 * Created by Administrator on 2018/4/10.
 */

public class OperationBean {


    String time;
    String username;
    String operation;

    public OperationBean(String time, String username, String operation) {
        this.time = time;
        this.username = username;
        this.operation = operation;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
