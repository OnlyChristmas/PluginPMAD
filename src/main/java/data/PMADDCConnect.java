package data;


/**
 * Created by xuany on 2017.3.14.
 */
public class PMADDCConnect {
    private String JDBC_DRIVER;
    private String DB_URL;
    private String USER;
    private String PASS;
    private String TABLE_NAME;
    private String JDBC_DRIVER2;
    private String DB_URL2;
    private String USER2;
    private String PASS2;

    PMADDCConnect() {
        JDBC_DRIVER = "com.mysql.jdbc.Driver";
        DB_URL = "jdbc:mysql://192.168.4.181/puphdata";
        USER = "root";
        PASS = "woxnsk";
        TABLE_NAME = "pluginpmad_trainm";
    }

    //调试用
    PMADDCConnect(String driver, String url, String user, String password, String tablename) {
        JDBC_DRIVER = driver;
        DB_URL = url;
        USER = user;
        PASS = password;
        TABLE_NAME = tablename;
    }


    PMADDCConnect(String driver, String url, String user, String password) {
        JDBC_DRIVER = driver;
        DB_URL = url;
        USER = user;
        PASS = password;
    }

//    PMADDCConnect(String driver, String url, String user, String password,
//                  String driver2, String url2, String user2, String password2) {
//        JDBC_DRIVER = driver;
//        DB_URL = url;
//        USER = user;
//        PASS = password;
//        JDBC_DRIVER2 = driver2;
//        DB_URL2 = url2;
//        USER2 = user2;
//        PASS2 = password2;
//    }

    public String getJDBC_DRIVER() {
        return JDBC_DRIVER;
    }

    public String getDB_URL() {
        return DB_URL;
    }

    public String getUSER() {
        return USER;
    }

    public String getPASS() {
        return PASS;
    }

    public String getTABLE_NAME() {
        return TABLE_NAME;
    }
}
