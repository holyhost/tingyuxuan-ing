package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/6.
 */

public class BookBean  implements Serializable {

    int number;
    String type;
    float price;
    String public_com;
    int count;
    String name;
    String cover;
    String introduce;
    int look_number;
    int brow_number;
    int star_number;

    public BookBean() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPublic_com() {
        return public_com;
    }

    public void setPublic_com(String public_com) {
        this.public_com = public_com;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getLook_number() {
        return look_number;
    }

    public void setLook_number(int look_number) {
        this.look_number = look_number;
    }

    public int getBrow_number() {
        return brow_number;
    }

    public void setBrow_number(int brow_number) {
        this.brow_number = brow_number;
    }
    public int getStar_number() {
        return star_number;
    }

    public void setStar_number(int star_number) {
        this.star_number = star_number;
    }
}
