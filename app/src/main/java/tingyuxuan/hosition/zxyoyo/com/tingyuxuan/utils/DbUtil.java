package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.adapter.AllUserAdapter;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BookBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.BorrowBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.FavorBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.OperationBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.bean.UserBean;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.AllUserActivity;

/**
 * Created by Administrator on 2018/4/5.
 */

public class DbUtil {


    public static int checkLogin(Context context, String sql) {
       DbHelper helper = new DbHelper(context,"tingyuxuan.db",null,1);
        SQLiteDatabase db = helper.getReadableDatabase();
        try{
            Cursor cursor = db.rawQuery(sql, null);
            int count = cursor.getCount();

            Cursor cursor1 = db.rawQuery("select * from user", null);
            while(cursor1.moveToNext()){
                int anInt = cursor1.getInt(0);
            }
            cursor1.close();
            db.close();
            return count;

        }catch (android.database.SQLException e){
            e.printStackTrace();
            db.close();
            return 0;
        }
    }


    public static String getCurrentTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        return time;
    }
    public static boolean toReg(Context context,String name,String pwd,String email,int type) {
        DbHelper helper = new DbHelper(context,"tingyuxuan.db",null,1);
        try{
            helper.addUser(name,pwd,email,type);
            helper.close();
            return true;
        }catch (android.database.SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    public static int getUserLevel(Context context,String name){
        DbHelper helper = new DbHelper(context);
        try{
            int userLevl = helper.getUserLevl(name);
            helper.close();
            return userLevl;
        }catch (android.database.SQLException e){
            e.printStackTrace();
            return 1;
        }
    }

    public static String getUserName(Context context,int id){
        DbHelper helper = new DbHelper(context);
        try{
            String name = helper.getUserName(id);
            helper.close();
            return name;
        }catch (android.database.SQLException e){
            e.printStackTrace();
            return "";
        }
    }


    public static List<OperationBean> getAllOperationRecord(Context context){
        List<OperationBean> mybooks = new ArrayList<>();
        DbHelper  helper = new DbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+Constants.TABLE_NAME_RECORD_OPERATION+" order by id desc", null);
        while(cursor.moveToNext()){
            String outtime = cursor.getString(3);
            String username = cursor.getString(2);
            String operation = cursor.getString(1);
            OperationBean bean = new OperationBean(outtime,username,operation);
            mybooks.add(bean);
        }
        db.close();
        helper.close();
        return mybooks;
    }

    public static List<OperationBean> getAllOperationRecord(Context context,String name){
        List<OperationBean> mybooks = new ArrayList<>();
        DbHelper  helper = new DbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+Constants.TABLE_NAME_RECORD_OPERATION+" where operation like '%read%' and username='"+name+"' order by id desc", null);
        while(cursor.moveToNext()){
            String outtime = cursor.getString(3);
            String username = cursor.getString(2);
            String operation = cursor.getString(1);
            OperationBean bean = new OperationBean(outtime,username,operation);
            mybooks.add(bean);
        }
        db.close();
        helper.close();
        return mybooks;
    }


    public static List<BorrowBean> getAllBorrowBooks(Context context){
        List<BorrowBean> mybooks = new ArrayList<>();
        DbHelper  helper = new DbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from outbook order by id desc", null);
        while(cursor.moveToNext()){
            String outtime = cursor.getString(1);
            String bookName = cursor.getString(3);
            int duration = cursor.getInt(4);
            int state = cursor.getInt(5);
            int id = cursor.getInt(0);
            int userid = cursor.getInt(2);
            BorrowBean bean = new BorrowBean(outtime,userid,bookName,duration,state,id);
            mybooks.add(bean);
        }
        db.close();
        helper.close();
        return mybooks;
    }

    public static  List<BorrowBean> getMyBorrowBooks(Context context){
        List<BorrowBean> mybooks = new ArrayList<>();

        String name = context.getSharedPreferences("user", Context.MODE_PRIVATE).getString("username", "");
        if(TextUtils.isEmpty(name)) return mybooks;
        DbHelper  helper = new DbHelper(context);
        int userId = helper.getUserId(name);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from outbook where userid="+userId+" order by id desc", null);
        while(cursor.moveToNext()){
            String outtime = cursor.getString(1);
            String bookName = cursor.getString(3);
            int duration = cursor.getInt(4);
            int state = cursor.getInt(5);
            int id = cursor.getInt(0);
            BorrowBean bean = new BorrowBean(outtime,bookName,duration,state,id);
            mybooks.add(bean);
        }
        db.close();
        helper.close();
        return mybooks;
    }


    public static  List<FavorBean> getMyFavorBooks(Context context){
        List<FavorBean> mybooks = new ArrayList<>();

        String name = context.getSharedPreferences("user", Context.MODE_PRIVATE).getString("username", "");
        if(TextUtils.isEmpty(name)) return mybooks;
        DbHelper  helper = new DbHelper(context);
        int userId = helper.getUserId(name);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from favor where state =1 and userid="+userId+" order by id desc", null);
        while(cursor.moveToNext()){
            String outtime = cursor.getString(1);
            String bookName = cursor.getString(3);
            int state = cursor.getInt(4);
            int id = cursor.getInt(0);
            int userid = cursor.getInt(2);
            FavorBean bean = new FavorBean(outtime,userid,bookName,state);
            bean.setId(id);
            mybooks.add(bean);
        }
        db.close();
        helper.close();
        return mybooks;
    }

    public static int updateMyBorrow(int id,Context context){


        DbHelper  helper = new DbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("state",1);
        int result = db.update("outbook", values, "id="+id, null);
        db.close();
        values.clear();
        helper.close();

        recordOperation("还书:"+id+(result>0?"成功":"失败"),context);
        return result;
    }

    public static int updateStar(int id, int star_number, Context context){


        DbHelper  helper = new DbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("star_number",star_number);
        int result = db.update("book", values, "number=" + id, null);
        db.close();
        values.clear();
        helper.close();
        return result;
    }

    public static int updateLookNumber(int id, int look_number, Context context){


        DbHelper  helper = new DbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("look_number",look_number);
        int result = db.update("book", values, "number="+id,null);
        db.close();
        values.clear();
        helper.close();

        recordOperation("浏览:"+id+(result>0?"成功":"失败"),context);
        return result;
    }

    public static int updateBookCount(int id, int book_count, Context context){
        DbHelper  helper = new DbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("count",book_count);
        int result = db.update("book", values, "number="+id,null);
        db.close();
        values.clear();
        helper.close();
        return result;
    }

    public static int updateBookCount(String bookName, Context context){
        DbHelper  helper = new DbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        try {
            db.execSQL("update book set count=count+1 where name='"+bookName+"'");

        }catch (android.database.SQLException e){
            e.printStackTrace();
            db.close();
            return 0;
        }
        db.close();
        helper.close();
        return 1;
    }


    public static int deleteBookByNumber(int number,Context context){

        DbHelper  helper = new DbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        int book = db.delete("book", "number=?", new String[] { String.valueOf(number)});
        db.close();
        helper.close();


        recordOperation("删除书籍:"+number+(book>0?"成功":"失败"),context);
        return book;
    }

    public static BookBean getBookById(int id,Context context){
        DbHelper  helper = new DbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from book where number=" + id, null);
        BookBean book = new BookBean();
        while (cursor.moveToNext()){
            String name = cursor.getString(5);
            String pub = cursor.getString(3);
            String intro = cursor.getString(7);
            int looknumber = cursor.getInt(8);
            int count = cursor.getInt(4);
            float price = cursor.getFloat(2);
            int star = cursor.getInt(9);

            book.setIntroduce(intro);
            book.setPublic_com(pub);
            book.setName(name);
            book.setPrice(price);
            book.setLook_number(looknumber);
            book.setStar_number(star);
            book.setCount(count);
        }
        cursor.close();
        db.close();
        helper.close();
        return book;
    }

    public static int addNewBook(BookBean book,Context context){
        recordOperation("添加书籍:"+book.getName(),context);
        DbHelper  helper = new DbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",book.getName());
        values.put("price",book.getPrice());
        values.put("count",book.getCount());
        values.put("introduce","这是一个介绍，有关："+book.getName()+"的介绍");
        values.put("type",book.getType());
        long rsult = db.insert("book", null, values);
        values.clear();
        db.close();
        helper.close();
        return (int) rsult;
    }

    public static int updateBook(BookBean book,Context context){

        recordOperation("修改书籍:"+book.getNumber(),context);

        DbHelper  helper = new DbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",book.getName());
        values.put("price",book.getPrice());
        values.put("count",book.getCount());
        values.put("introduce",book.getIntroduce());
        values.put("type",book.getType());
        values.put("public_com",book.getPublic_com());
        int result = db.update("book", values, "number=?", new String[]{String.valueOf(book.getNumber())});
        values.clear();
        db.close();
        helper.close();
        return  result;
    }

    public static int addNewFavouriteBook(FavorBean book, Context context){
        recordOperation("添加收藏:"+book.getBookname(),context);
        DbHelper  helper = new DbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("bookname",book.getBookname());
        values.put("userid",book.getUserid());
        values.put("state",book.getState());
        values.put("time",book.getTime());
        long rsult = db.insert("favor", null, values);
        values.clear();
        db.close();
        helper.close();
        return (int) rsult;
    }

    public static int updateFavouriteBook(FavorBean book,Context context){

        recordOperation("修改收藏:"+book.getBookname(),context);

        DbHelper  helper = new DbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("state",book.getState());
        int result = db.update("favor", values, "id=?", new String[]{String.valueOf(book.getId())});
        values.clear();
        db.close();
        helper.close();
        return  result;
    }
    public static int updateUser(UserBean user,Context context){

        recordOperation("更新用户信息:"+user.getId(),context);
        DbHelper  helper = new DbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",user.getName());
        values.put("major",user.getMajor());
        values.put("type",user.getType());
        values.put("age",user.getAge());
        values.put("email",user.getEmail());
        values.put("password",user.getPassword());
        int result = db.update("user", values, "id=?", new String[]{String.valueOf(user.getId())});
        values.clear();
        db.close();
        helper.close();
        return  result;
    }


    public static UserBean getUserByName(Context context,String name) {
        DbHelper  helper = new DbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where name='" + name+"'", null);
        UserBean user = new UserBean();
        while (cursor.moveToNext()){
            String sex = cursor.getString(2);
            String major = cursor.getString(3);
            String email = cursor.getString(9);
            String pwd = cursor.getString(10);
            int classnum = cursor.getInt(4);
            int age = cursor.getInt(6);
            int type = cursor.getInt(1);
            int id = cursor.getInt(0);
            user.setId(id);
            user.setType(type);
            user.setEmail(email);
            user.setClassnum(classnum);
            user.setName(name);
            user.setAge(age);
            user.setSex(sex);
            user.setPassword(pwd);
            user.setMajor(major);

        }
        cursor.close();
        db.close();
        helper.close();
        return user;
    }


    public static void recordOperation(String operation,Context context){
        String username = context.getSharedPreferences("user", Context.MODE_PRIVATE).getString("username", "未知用户");
        DbHelper  helper = new DbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("operation",operation);
        values.put("username",username);
        values.put("time",getCurrentTime());
        db.insert(Constants.TABLE_NAME_RECORD_OPERATION, null, values);
        values.clear();
        db.close();
        helper.close();
    }
}
