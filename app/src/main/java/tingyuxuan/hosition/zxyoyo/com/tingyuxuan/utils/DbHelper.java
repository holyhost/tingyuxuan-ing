package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BookBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BorrowBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.UserBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.frgment.BorrowFragment;

/**
 * Created by Administrator on 2018/4/6.
 */

public class DbHelper extends SQLiteOpenHelper {


    private static final String SQL_BOOK= "create table if not exists "+Constants.TABLE_NAME_BOOK+"(" +
            "number INTEGER NOT NULL primary key autoincrement," +
            "type varchar(50)  NOT NULL," +
            "price float NOT NULL," +
            "public_com varchar(50)  DEFAULT '听雨喧出版社'," +
            "count int(11) NOT NULL," +
            "name varchar(50) NOT NULL," +
            "cover varchar(50) DEFAULT NULL," +
            "introduce varchar(128)  DEFAULT NULL," +
            "look_number int(11) default 0," +
            "star_number int(11) default 0," +
            "brow_number int(11) default 0" +
            ")";
    private static final String SQL_USER= "create table if not exists "+Constants.TABLE_NAME_USER+"(" +
            "id INTEGER NOT NULL primary key autoincrement," +
            "type int(11) DEFAULT 1," +
            "sex varchar(5) DEFAULT NULL," +
            "major varchar(100) DEFAULT NULL," +
            "classnum int(11) DEFAULT NULL," +
            "name varchar(50) NOT NULL unique," +
            "cover varchar(128)  DEFAULT NULL," +
            "age int(11) DEFAULT 0," +
            "phone int(11) DEFAULT 0," +
            "jointime datetime DEFAULT NULL," +
            "email int(11) DEFAULT 0," +
            "password varchar(50) not null"+
            ")";

    private static final String SQL_OUT_BOOK= "create table if not exists "+Constants.TABLE_NAME_OUT_BOOK+"(" +
            "id INTEGER  primary key autoincrement," +
            "outtime varchar(100) not null," +
            "userid int(11) not null," +
            "bookname varchar(51) not null," +
            "duration int(11) default 30," +
            "state int(11) default 0" +
            ")";

    private static final String SQL_FAVIOR_BOOK= "create table if not exists "+Constants.TABLE_NAME_FAVOR+"(" +
            "id INTEGER  primary key autoincrement," +
            "time varchar(100) not null," +
            "userid int(11) not null," +
            "bookname varchar(51) not null," +
            "state int(11) default 1" +
            ")";

    private static final String SQL_OPERATION = "create table if not exists "+Constants.TABLE_NAME_RECORD_OPERATION+"(" +
            "id INTEGER  primary key autoincrement," +
            "operation varchar(128) not null," +
            "username varchar(51) not null," +
            "time varchar(100) not null" +
            ")";

    public DbHelper(Context context){
        super(context,"tingyuxuan.db",null,1);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建表
        sqLiteDatabase.execSQL(SQL_BOOK);
        sqLiteDatabase.execSQL(SQL_USER);
        sqLiteDatabase.execSQL(SQL_OUT_BOOK);
        sqLiteDatabase.execSQL(SQL_OPERATION);
        sqLiteDatabase.execSQL(SQL_FAVIOR_BOOK);
        System.out.println("数据库创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



    public void addUser(String name,String pwd,String email,int type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("password",pwd);
        values.put("email",email);
        values.put("type",type);
        db.insert("user",null,values);
        values.clear();
        db.close();
    }

    public int getUserLevl(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select type from user where name='" + name + "'", null);
        cursor.moveToLast();
        int type=0;
        try{
           type= cursor.getInt(cursor.getColumnIndex("type"));
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            db.close();
            return type;
        }
    }

    public String getUserName(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select name from user where id='" + id + "'", null);
        cursor.moveToLast();
        String name="";
        try{
            name= cursor.getString(cursor.getColumnIndex("name"));
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            db.close();
            return name;
        }
    }


    //获取用户的id
    public int getUserId(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select id from user where name='" + name + "'", null);
        cursor.moveToLast();
        int type=0;
        try{
            type= cursor.getInt(cursor.getColumnIndex("id"));
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            db.close();
            return type;
        }


    }

    public String getBookName(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select name from book where id=" + id , null);
        cursor.moveToLast();
        String nmae="";
        try{
            nmae= cursor.getString(cursor.getColumnIndex("name"));
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            db.close();
            return nmae;
        }


    }


    public List<BookBean> getAllBooks(){
        List<BookBean> books = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        //查询Book表中的所有数据
        Cursor cursor = db.query("book", null, null, null, null, null, "number desc", null);
        //遍历Curosr对象，取出数据并打印
        while (cursor.moveToNext()) {
            BookBean book = new BookBean();
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String introduce = cursor.getString(cursor.getColumnIndex("introduce"));
            String pub = cursor.getString(cursor.getColumnIndex("public_com"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            int count = cursor.getInt(cursor.getColumnIndex("count"));
            int look_number = cursor.getInt(cursor.getColumnIndex("look_number"));
            int star_number = cursor.getInt(cursor.getColumnIndex("star_number"));
            int price = cursor.getInt(cursor.getColumnIndex("price"));
            int id = cursor.getInt(cursor.getColumnIndex("number"));
            book.setNumber(id);
            book.setName(name);
            book.setPublic_com(pub);
            book.setLook_number(look_number);
            book.setStar_number(star_number);
            book.setIntroduce(introduce);
            book.setCount(count);
            book.setPrice(price);
            book.setType(type);
            books.add(book);
        }
        //关闭Cursor
        cursor.close();
        db.close();
        return books;
    }

    public void addBook(BookBean book){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",book.getName());
        values.put("price",book.getPrice());
        values.put("count",book.getCount());
        values.put("introduce","这是有关："+book.getName()+"的介绍");
        values.put("type",book.getType());
        long book1 = db.insert("book", null, values);
        System.out.println("-=-----book1---添加一本-"+book1);
        values.clear();
        db.close();
    }
    public void addBooks(){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        Random random = new Random();
        String bookName = Constants.getBookName(random.nextInt(100));
        values.put("name", bookName);
        values.put("price",random.nextInt(90)/2);
        values.put("count",random.nextInt(40));
        values.put("introduce","这是有关："+bookName+"的介绍");
        values.put("type","文学");
        db.insert("book",null,values);
        values.clear();
        db.close();
    }

    public void addDefaultData(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for(int i=1;i<11;i++){
            Random random = new Random();
            int index = random.nextInt(100);
            String bookName = Constants.getBookName(index);
            values.put("type","文学类");
            values.put("price",i+index);
            values.put("star_number",0);
            values.put("public_com","听雨轩出版社");
            values.put("count",20+i);
            values.put("name",bookName+"传");
            values.put("introduce","介绍："+bookName);
            try {
                long book = db.insert("book", null, values);
            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                values.clear();
            }
        }
        db.close();
    }


    public void addToMyBorreow(String username,String bookName) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        int userId = getUserId(username);
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("userid",userId);
        values.put("bookname",bookName);
        values.put("outtime",time);
        try{
            db.insert("outbook",null,values);
        }catch (SQLException e){
            e.printStackTrace();

        }finally {
            values.clear();
            db.close();
        }


    }

    public  void getUserBorrows(String name){
       String sql = "select j.outtime,b.name,j.duration from outbook j,book b,user u where u.name='"+name
               +"' and u.id=j.userid and b.number=j.bookid ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
//        db.query()
        while(cursor.moveToNext()){
            String outtime = cursor.getString(cursor.getColumnIndex("outtime"));
            String name2 = cursor.getString(cursor.getColumnIndex("name"));
            int duration = cursor.getInt(cursor.getColumnIndex("duration"));
        }

    }

    public void selectAllBorrow(String name){
        List<BorrowBean> books = new ArrayList<>();
        int userId = getUserId(name);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("outbook", null, null, null, null, null, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("userid"));
            if(userId == id){
                BorrowBean  bean = new BorrowBean();
                String outtime = cursor.getString(cursor.getColumnIndex("outtime"));
                String name2 = cursor.getString(cursor.getColumnIndex("name"));
                bean.setTime(outtime);
                bean.setName(name2);
                System.out.println("------------------"+outtime+"---"+name2);
            }
        }
    }

    public  List<UserBean> getAllUsers(){
        List<UserBean> users =  new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.query("user", null, null, null, null, null, null);
        Cursor cursor = db.rawQuery("select * from user order by id desc", null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String sex = cursor.getString(cursor.getColumnIndex("sex"));
            String major = cursor.getString(cursor.getColumnIndex("major"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String pwd = cursor.getString(cursor.getColumnIndex("password"));
            UserBean bean = new UserBean();
            bean.setType(type);
            bean.setPassword(pwd);
            bean.setId(id);
            bean.setMajor(major);
            bean.setSex(sex);
            bean.setEmail(email);
            bean.setName(name);
            users.add(bean);
        }
        cursor.close();
        db.close();
        return users;
    }
}
