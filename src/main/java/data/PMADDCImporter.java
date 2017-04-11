package data;

import cc.mallet.pipe.*;
import cc.mallet.pipe.iterator.CsvIterator;
import cc.mallet.types.InstanceList;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;

import static core.PMAD.ldaView2inputFile;

/**
 * Created by xuany on 2017.3.13.
 */
public class PMADDCImporter {


    static boolean DEBUG = true;

    private static String JDBC_DRIVER;
    private static String DB_URL;
    private static String USER;
    private static String PASS;
    private static String TABLE_NAME;
    private static String tem;
    private static int num_corp = 0;


    public static List<String> Mids = new ArrayList<>();

    // 连接到默认数据库，参数从DBConnection中获得
    public static void dbConnect() {
        System.out.println("This function shouldn't be invoked!");
        System.out.println("This function shouldn't be invoked!");
        System.out.println("This function shouldn't be invoked!");
        PMADDCConnect dcConnect = new PMADDCConnect();
        JDBC_DRIVER = dcConnect.getJDBC_DRIVER();
        DB_URL = dcConnect.getDB_URL();
        USER = dcConnect.getUSER();
        PASS = dcConnect.getPASS();
        TABLE_NAME = dcConnect.getTABLE_NAME();
    }

    // 根据得到的信息连接数据库(调试用)
    public static void dbConnect(String driver, String url, String user, String pass, String tablename) {
        PMADDCConnect dcConnect = new PMADDCConnect(driver, url, user, pass, tablename);
        JDBC_DRIVER = dcConnect.getJDBC_DRIVER();
        DB_URL = dcConnect.getDB_URL();
        USER = dcConnect.getUSER();
        PASS = dcConnect.getPASS();
        TABLE_NAME = dcConnect.getTABLE_NAME();
    }

    // 根据得到的信息连接数据库

    public static void dbConnect(String driver, String url, String user, String pass) {
        PMADDCConnect dcConnect = new PMADDCConnect(driver, url, user, pass);
        JDBC_DRIVER = dcConnect.getJDBC_DRIVER();
        DB_URL = dcConnect.getDB_URL();
        USER = dcConnect.getUSER();
        PASS = dcConnect.getPASS();
    }


    // 用已经获得的URL、USER和PASS信息去连接数据库
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        return conn;
    }


    // idName对应用作文档ID那那一列的列名(如pid)，wordName对应作文档词的那一列的列名(如disease_name),生成语料
    public static PMADView generateASimpleView(List<String> idName, List<String> wordName, List<String> TABLE_NAME) throws SQLException {
        String idname = idName.get(0).toString();
        String wordname = wordName.get(0).toString();
        String tablename = TABLE_NAME.get(0).toString();


        Connection conn;
        Statement stmt;
        List<String> ids = new ArrayList<>();
        Map<String, List<String>> docs = new TreeMap<>();
        //Set<String> allWords = new HashSet<>();
        try {
            conn = getConnection();
            stmt = conn.createStatement();


//            String getIds = "SELECT DISTINCT " + idName + " FROM " + TABLE_NAME;
//            // 测试的时候仅选几个病人试试看
//            if (DEBUG) {
//                getIds += " LIMIT 1000";
//            }
//            ResultSet idSet = stmt.executeQuery(getIds);
//            while (idSet.next()) {
//                String tp = idSet.getString(1);
//                ids.add(tp);
//            }
//            System.out.println("原方法得到id");
//
//            for (String id : ids) {
//
//                String getWords = "SELECT " + wordName + " FROM " + TABLE_NAME + " WHERE " + idName + " = " + id;
//                ResultSet wordSet = stmt.executeQuery(getWords);
//                List<String> words = new ArrayList<>();
//                for (int num = 0; wordSet.next() == true; num = num + 1) {
//
//                    String tp = wordSet.getString(1);
//                    words.add(num, tp);
//                    //allWords.add(tp);
//                }
//                docs.put(id, words);
//            }
//            System.out.println("读取数据");


            ResultSet DList;
            List<String> dList = new ArrayList<>();

            String Did = "0";
            if (Mids.size() != 0) {
                String getDList = "SELECT DISTINCT " + idname + " FROM " + tablename;
                // 测试的时候仅选几个病人试试看

                System.out.println("获取所有诊断列表病人编号");
                DList = stmt.executeQuery(getDList);
                System.out.println("开始写入");
                for (; DList.next() == true; ) {
                    Did = DList.getString(1);
                    dList.add(Did);
                }
            }

            int dListid = 0;

            System.out.println("读取数据");
            String getInformation = "SELECT " + idname + "," + wordname + " FROM " + tablename;

            // 测试的时候仅选几个病人试试看
            if (DEBUG) {
                getInformation += " LIMIT 10000";
            }

            boolean startread = stmt.execute(getInformation);
            if (startread) {
                boolean exist;
                ResultSet infor = stmt.getResultSet();
                int num = 0;
                String tpid = "0";

                if (Mids.size() != 0) {
                    int Mid = 0;

                    for (; dListid < dList.size(); dListid++) {
                        if (dList.get(dListid).equals(Mids.get(Mid))) {
                            break;
                        } else {
                            continue;
                        }
                    }

//                    do {                                                   //A match is found patients code to proceed
//                        exist = true;
//                        Did = DList.getString(1);
//                        for (; Mid < Mids.size(); Mid = Mid + 1) {
//                            if (Mids.get(Mid).equals(Did)) {
//                                break;
//                            } else {
//                                if (Mid == Mids.size() - 1) {
//                                    exist = false;
//                                }
//                            }
//                        }
//                    } while (exist == false && DList.next());

                    do {
                        infor.next();
                        tpid = infor.getString(1);
                    } while (dList.get(dListid).equals(tpid) == false);
                    System.out.println("匹配第一个");

                    ids.add(tpid);
                    String tpword;

                    boolean intercycle;
                    boolean outloop = true;
                    boolean suplle = false;
                    do {
                        exist = true;
                        List<String> words = new ArrayList<>();

                        do {
                            // outloop = true;
                            intercycle = true;
//                        if (suplle) {
//                            words.add(tem);
//                        }
//                        suplle = false;


                            tpid = infor.getString(1);
                            tpword = infor.getString(2);

                            if (tpid.equals(ids.get(num))) {
                                words.add(tpword);
                            } else {
                                docs.put(ids.get(num), words);
                                dListid++;
                                ++Mid;
                                if (Mids.size() - 1 < Mid) {
                                    outloop = false;
                                }
                                intercycle = false;

//                                for (int i = 0; i < Mids.size(); i = i + 1) {
//                                    if (Mids.get(i).equals(tpid)) {
//                                        intercycle = false;
//                                        break;
//                                    } else {
//                                        if (i == Mids.size() - 1) {
//                                            exist = false;
//                                            break;
//                                        }
//                                    }
//                                }
                            }
                        }
                        while (intercycle && infor.next() == intercycle);

                        if (intercycle == false && outloop) {
                            for (; dListid < dList.size(); dListid++) {
                                if (dList.get(dListid).equals(Mids.get(Mid))) {
                                    if (Mids.size() - 1 < Mid) {
                                        outloop = false;
                                    }
                                    break;
                                } else {
                                    continue;
                                }
                            }
                            if (dListid == dList.size()) {
                                outloop = false;
                            }
                            if (tpid.equals(dList.get(dListid))) {
                                ids.add(tpid);
                                num = num + 1;
                            } else {
                                for (; outloop && tpid.equals(dList.get(dListid)) == false && infor.next(); ) {

                                    tpid = infor.getString(1);
                                }
                                ids.add(tpid);
                                num = num + 1;
                            }
                            //suplle = true;
                            //  tem = tpword;
                        }
//                        if (exist == false) {
//                            docs.put(ids.get(num), words);
//                            outloop = false;
//                        }
                        if (intercycle == true) {
                            outloop = false;
                        }
                    }
                    while (outloop);
                } else {
                    //A match is found patients code to proceed
                    exist = true;
                    infor.next();
                    tpid = infor.getString(1);

                    ids.add(tpid);
                    String tpword;

                    boolean intercycle;
                    boolean outloop = true;
                    boolean suplle = false;
                    do {
                        List<String> words = new ArrayList<>();

                        do {
                            // outloop = true;
                            intercycle = true;
//                        if (suplle) {
//                            words.add(tem);
//                        }
//                        suplle = false;
                            tpid = infor.getString(1);
                            tpword = infor.getString(2);

                            if (tpid.equals(ids.get(num))) {
                                words.add(tpword);
                            } else {
                                docs.put(ids.get(num), words);
                                intercycle = false;
                            }
                        }
                        while (intercycle && infor.next() == intercycle);
                        if (intercycle == false) {
                            ids.add(tpid);
                            num = num + 1;
//                            suplle = true;
//                              tem = tpword;
                        } else {
                            docs.put(ids.get(num), words);
                            outloop = false;
                        }
                    }
                    while (outloop);
                }
            }

            if (wordName.get(0) == "name") {
                Mids = ids;
            }
//            if (wordName.get(num_corp) == "name") {
//                Mids = ids;
//            }
            System.out.println("读取完成");


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
//        Map<Integer, String> dictInt2Str = new HashMap<>();
//        Map<String, Integer> dictStr2Int = new HashMap<>();
//        int iterator = 0;
//        for (String s : allWords) {
//            dictInt2Str.put(iterator++, s);
//        }
//        for (Map.Entry<Integer, String> entry : dictInt2Str.entrySet()) {
//            dictStr2Int.put(entry.getValue(), entry.getKey());
//        }

        List<PMADDocument> ldaDocuments = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : docs.entrySet()) {
            List<String> wordlist = entry.getValue();
            int length = wordlist.size();
            PMADDocument document = new PMADDocument(entry.getKey(), length, null);
            for (int i = 0; i < length; ++i) {
                document.words[i] = wordlist.get(i);
            }
            ldaDocuments.add(document);
        }
//
//        String temp;
//        int size = ids.size();
//        for (int i = 0; i < size - 1; i++) {
//            for (int j = i + 1; j < size; j++) {
//                if (ids.get(i).compareTo(ids.get(j)) > 0) {
//                    temp = ids.get(i);
//                    ids.set(i, ids.get(j));
//                    ids.set(j, temp);
//                }
//            }
//        }


        PMADView ldaViewSimple = new PMADView();
        ldaViewSimple.setName(TABLE_NAME.get(0));
//        ldaViewSimple.setName(TABLE_NAME.get(num_corp));
        ldaViewSimple.setDocs(ldaDocuments);
        return ldaViewSimple;


    }


    // idName对应用作文档ID那那一列的列名(如pid)，wordName对应作文档词的那一列的列名(如disease_name),生成语料
    public static PMADView generateASimpleView(String idName, String wordName, String TABLE_NAME) throws SQLException {

        Connection conn;
        Statement stmt;
        List<String> ids = new ArrayList<>();
        Map<String, List<String>> docs = new TreeMap<>();
        //Set<String> allWords = new HashSet<>();
        try {
            conn = getConnection();
            stmt = conn.createStatement();


//            String getIds = "SELECT DISTINCT " + idName + " FROM " + TABLE_NAME;
//            // 测试的时候仅选几个病人试试看
//            if (DEBUG) {
//                getIds += " LIMIT 1000";
//            }
//            ResultSet idSet = stmt.executeQuery(getIds);
//            while (idSet.next()) {
//                String tp = idSet.getString(1);
//                ids.add(tp);
//            }
//            System.out.println("原方法得到id");
//
//            for (String id : ids) {
//
//                String getWords = "SELECT " + wordName + " FROM " + TABLE_NAME + " WHERE " + idName + " = " + id;
//                ResultSet wordSet = stmt.executeQuery(getWords);
//                List<String> words = new ArrayList<>();
//                for (int num = 0; wordSet.next() == true; num = num + 1) {
//
//                    String tp = wordSet.getString(1);
//                    words.add(num, tp);
//                    //allWords.add(tp);
//                }
//                docs.put(id, words);
//            }
//            System.out.println("读取数据");


            String getDList = "SELECT DISTINCT " + idName + " FROM " + TABLE_NAME;
            // 测试的时候仅选几个病人试试看

            System.out.println("读取列表0");
            ResultSet DList;
            List<String> dList = new ArrayList<>();

            String Did = "0";
            if (Mids.size() != 0) {
                DList = stmt.executeQuery(getDList);
                System.out.println("读取列表1");
                for (; DList.next() == true; ) {
                    Did = DList.getString(1);
                    dList.add(Did);
                }
            }

            int dListid = 0;

            System.out.println("开始读取");
            String getInformation = "SELECT " + idName + "," + wordName + " FROM " + TABLE_NAME;

            // 测试的时候仅选几个病人试试看
            if (DEBUG) {
                getInformation += " LIMIT 200000";
            }

            boolean startread = stmt.execute(getInformation);
            if (startread) {
                boolean exist;
                ResultSet infor = stmt.getResultSet();
                int num = 0;
                String tpid = "0";

                if (Mids.size() != 0) {
                    int Mid = 0;

                    for (; dListid < dList.size(); dListid++) {
                        if (dList.get(dListid).equals(Mids.get(Mid))) {
                            break;
                        } else {
                            continue;
                        }
                    }

//                    do {                                                   //A match is found patients code to proceed
//                        exist = true;
//                        Did = DList.getString(1);
//                        for (; Mid < Mids.size(); Mid = Mid + 1) {
//                            if (Mids.get(Mid).equals(Did)) {
//                                break;
//                            } else {
//                                if (Mid == Mids.size() - 1) {
//                                    exist = false;
//                                }
//                            }
//                        }
//                    } while (exist == false && DList.next());

                    do {
                        infor.next();
                        tpid = infor.getString(1);
                    } while (dList.get(dListid).equals(tpid) == false);
                    System.out.println("匹配第一个");

                    ids.add(tpid);
                    String tpword;

                    boolean intercycle;
                    boolean outloop = true;
                    boolean suplle = false;
                    do {
                        exist = true;
                        List<String> words = new ArrayList<>();

                        do {
                            // outloop = true;
                            intercycle = true;
//                        if (suplle) {
//                            words.add(tem);
//                        }
//                        suplle = false;


                            tpid = infor.getString(1);
                            tpword = infor.getString(2);

                            if (tpid.equals(ids.get(num))) {
                                words.add(tpword);
                            } else {
                                docs.put(ids.get(num), words);
                                dListid++;
                                ++Mid;
                                if (Mids.size() - 1 < Mid) {
                                    outloop = false;
                                }
                                intercycle = false;

//                                for (int i = 0; i < Mids.size(); i = i + 1) {
//                                    if (Mids.get(i).equals(tpid)) {
//                                        intercycle = false;
//                                        break;
//                                    } else {
//                                        if (i == Mids.size() - 1) {
//                                            exist = false;
//                                            break;
//                                        }
//                                    }
//                                }
                            }
                        }
                        while (intercycle && infor.next() == intercycle);

                        if (intercycle == false && outloop) {
                            for (; dListid < dList.size(); dListid++) {
                                if (dList.get(dListid).equals(Mids.get(Mid))) {
                                    if (Mids.size() - 1 < Mid) {
                                        outloop = false;
                                    }
                                    break;
                                } else {
                                    continue;
                                }
                            }
                            if (dListid == dList.size()) {
                                outloop = false;
                            }
                            if (tpid.equals(dList.get(dListid))) {
                                ids.add(tpid);
                                num = num + 1;
                            } else {
                                for (; outloop && tpid.equals(dList.get(dListid)) == false && infor.next(); ) {

                                    tpid = infor.getString(1);
                                }
                                ids.add(tpid);
                                num = num + 1;
                            }
                            //suplle = true;
                            //  tem = tpword;
                        }
//                        if (exist == false) {
//                            docs.put(ids.get(num), words);
//                            outloop = false;
//                        }
                        if (intercycle == true) {
                            outloop = false;
                        }
                    }
                    while (outloop);
                } else {
                    //A match is found patients code to proceed
                    exist = true;
                    infor.next();
                    tpid = infor.getString(1);

                    ids.add(tpid);
                    String tpword;

                    boolean intercycle;
                    boolean outloop = true;
                    boolean suplle = false;
                    do {
                        List<String> words = new ArrayList<>();

                        do {
                            // outloop = true;
                            intercycle = true;
//                        if (suplle) {
//                            words.add(tem);
//                        }
//                        suplle = false;
                            tpid = infor.getString(1);
                            tpword = infor.getString(2);

                            if (tpid.equals(ids.get(num))) {
                                words.add(tpword);
                            } else {
                                docs.put(ids.get(num), words);
                                intercycle = false;
                            }
                        }
                        while (intercycle && infor.next() == intercycle);
                        if (intercycle == false) {
                            ids.add(tpid);
                            num = num + 1;
//                            suplle = true;
//                              tem = tpword;
                        } else {
                            docs.put(ids.get(num), words);
                            outloop = false;
                        }
                    }
                    while (outloop);
                }
            }

            if (wordName == "name") {
                Mids = ids;
            }
            System.out.println("读取完成");


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
//        Map<Integer, String> dictInt2Str = new HashMap<>();
//        Map<String, Integer> dictStr2Int = new HashMap<>();
//        int iterator = 0;
//        for (String s : allWords) {
//            dictInt2Str.put(iterator++, s);
//        }
//        for (Map.Entry<Integer, String> entry : dictInt2Str.entrySet()) {
//            dictStr2Int.put(entry.getValue(), entry.getKey());
//        }

        List<PMADDocument> ldaDocuments = new ArrayList<>();
        for (
                Map.Entry<String, List<String>> entry : docs.entrySet())

        {
            List<String> wordlist = entry.getValue();
            int length = wordlist.size();
            PMADDocument document = new PMADDocument(entry.getKey(), length, null);
            for (int i = 0; i < length; ++i) {
                document.words[i] = wordlist.get(i);
            }
            ldaDocuments.add(document);
        }
//
//        String temp;                            sort id
//        int size = ids.size();
//        for (int i = 0; i < size - 1; i++) {
//            for (int j = i + 1; j < size; j++) {
//                if (ids.get(i).compareTo(ids.get(j)) > 0) {
//                    temp = ids.get(i);
//                    ids.set(i, ids.get(j));
//                    ids.set(j, temp);
//                }
//            }
//        }


        PMADView ldaViewSimple = new PMADView();
        ldaViewSimple.setName(TABLE_NAME);
        ldaViewSimple.setDocs(ldaDocuments);
        return ldaViewSimple;


    }


//    public static void ClearFile(String fileName) throws SQLException {
//        Connection connect;
//        Statement clearfile;
//        connect = getConnection();
//        clearfile = connect.createStatement();
//        String getMedicineList = "SELECT distinct pid FROM pluginpmad_trainm";
//        ResultSet wordSet = clearfile.executeQuery(getMedicineList);
//        List<String> words = new ArrayList<>();
//    }


    public static InstanceList readInstances(String fileName) throws FileNotFoundException {

        List<Pipe> pipes = new ArrayList<>();
        pipes.add(new CharSequenceLowercase());
        pipes.add(new CharSequence2TokenSequence(Pattern.compile("\\S+")));
        pipes.add(new TokenSequence2FeatureSequence());

        InstanceList inses = new InstanceList(new SerialPipes(pipes));
        Reader reader = new InputStreamReader(new FileInputStream(new File(fileName)));
        Iterator iter = new CsvIterator(reader, Pattern.compile("(\\S+)\\s(\\S+)\\s(.*)"),
                3, 2, 1);
        // System.out.println("iter="+iter);
        inses.addThruPipe(iter);

        return inses;
    }

    public static List getAllTableName(Connection cnn) throws SQLException {
        List tables = new ArrayList();

        DatabaseMetaData dbMetaData = cnn.getMetaData();

        //可为:"TABLE",   "VIEW",   "SYSTEM   TABLE",
        //"GLOBAL   TEMPORARY",   "LOCAL   TEMPORARY",   "ALIAS",   "SYNONYM"
        String[] types = {"TABLE"};

        ResultSet tabs = dbMetaData.getTables(null, null, null, types/*只要表就好了*/);
        /*记录集的结构如下:
          TABLE_CAT       String   =>   table   catalog   (may   be   null)
          TABLE_SCHEM   String   =>   table   schema   (may   be   null)
          TABLE_NAME     String   =>   table   name
          TABLE_TYPE     String   =>   table   type.
          REMARKS           String   =>   explanatory   comment   on   the   table
          TYPE_CAT         String   =>   the   types   catalog   (may   be   null)
          TYPE_SCHEM     String   =>   the   types   schema   (may   be   null)
          TYPE_NAME       String   =>   type   name   (may   be   null)
          SELF_REFERENCING_COL_NAME   String   =>   name   of   the   designated   "identifier"   column   of   a   typed   table   (may   be   null)
          REF_GENERATION   String   =>   specifies   how   values   in   SELF_REFERENCING_COL_NAME   are   created.   Values   are   "SYSTEM",   "USER",   "DERIVED".   (may   be   null)
        */
        while (tabs.next()) {
            //只要表名这一列
            tables.add(tabs.getObject("TABLE_NAME"));
        }
        System.out.println(tables);
        return tables;
    }


    // num is the number lines need to be shown
    // 从连接的对应表中去获取数据，供前端预览使用
    public static Map<String, Object> getPreviewData(int num, String tableName) {
        Map<String, List<String>> data = new LinkedHashMap<>();
        List<String> heads = new ArrayList<>();
        Connection conn;
        Statement stmt;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            String getHead = "SELECT DISTINCT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = \'" + tableName + "\'";
            String getData = "SELECT * FROM `" + tableName + "` LIMIT " + num;

            ResultSet tableHeadSet = stmt.executeQuery(getHead);

            while (tableHeadSet.next()) {
                String tp = tableHeadSet.getString("COLUMN_NAME");
                heads.add(tp);
                data.put(tp, new ArrayList<>());
            }

            ResultSet dataSet = stmt.executeQuery(getData);
            while (dataSet.next()) {
                for (String hd : heads) {
                    data.get(hd).add(dataSet.getString(hd));
                }
            }
            if (DEBUG) {
                System.out.println("heads " + heads);
                System.out.println("data " + data);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection Error");
            return null;
        }
        Map<String, Object> res = new HashMap<>();
        res.put("heads", heads);
        res.put("data", data);
        return res;
    }


    public static void generate(List<String> idNames, List<String> wordNames, List<String> tableNames, String corpName) throws SQLException {
        PMADView medicine = PMADDCImporter.generateASimpleView(idNames, wordNames, tableNames);

        PMADModelInput medicineInput = new PMADModelInput();
        medicineInput.addLDAView(medicine);
        PMADView medicineView = medicineInput.getViews().get(0);
//        if (wordNames.get(num_corp).equals("disease_name")) {
//            String sign = corpName + "D";
//            ldaView2inputFile(medicineView, sign);
//        }
        if (wordNames.get(0).equals("disease_name")) {
            String sign = "D";
            ldaView2inputFile(medicineView, sign, corpName);
        } else {
            String sign = "M";
            ldaView2inputFile(medicineView, sign, corpName);
        }

        num_corp++;
    }

}

