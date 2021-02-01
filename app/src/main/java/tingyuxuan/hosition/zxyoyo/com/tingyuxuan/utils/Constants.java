package tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils;

/**
 * Created by Administrator on 2018/4/5.
 */

public  class Constants {
    public  static  final String URL_LOGIN = "";
    public static  final String  USER = "";//数据库用户名
    public static  final String  PWD = "";//数据库密码
    public static  final String  URL_HOST = "";//mysql主机ip
    public static  final String  DB = "";//数据库名称
    public static  final String  URL = "jdbc:mysql://" + URL_HOST + "/"+DB;

    public static  final String TABLE_NAME_RECORD_OPERATION="record_operation";//记录用户所有操作，登录，注册，增删改
    public static  final String TABLE_NAME_OUT_BOOK="outbook";//外借书籍表
    public static  final String TABLE_NAME_USER="user";//外借书籍表
    public static  final String TABLE_NAME_BOOK="book";//
    public static  final String TABLE_NAME_FAVOR="favor";//


    /**
     * 随机获得一个名字
     * @param index
     * @return
     */
    public  static String getBookName(int index){
        String[] names = new String[]{
            "天魁星呼保义宋江",
            "天罡星玉麒麟卢俊义 ",
            "天机星智多星吴用",
            "天闲星入云龙公孙胜 ",
            "天勇星大刀关胜 ",
            "天雄星豹子头林冲 ",
            "天猛星霹雳火秦明 ",
            "天威星双鞭呼延灼 ",
            "天英星小李广花荣 ",
            "天贵星小旋风柴进 ",
            "天富星扑天雕李应",
            "天满星美髯公朱仝",
            "天孤星花和尚鲁智深 ",
            "天伤星行者武松 ",
            "天立星双枪将董平 ",
            "天佑星金枪手徐宁 ",
            "天异星赤发鬼刘唐",
            "天杀星黑旋风李逵",
            "天究星没遮拦穆弘",
            "天寿星混江龙李俊 ",
            "天退星插翅虎雷横",
            "天败星活阎罗阮小七",
            "地奇星圣水将单廷"
        };
        return names[index%names.length];
    }

}
