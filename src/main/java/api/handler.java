package api;

import core.PMAD;
import data.PMADDCImporter;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xuany on 2017.4.7.
 */
public class handler extends HttpServlet {

    int topicNum = 20;
    int iteration = 1000;    //前端给用户默认值1000
    String SimiMethod = "DOT";
    String corpName;
    List<String> tableNames_m = new ArrayList<>();
    List<String> tableNames_d = new ArrayList<>();

    List<String> wordNames_m = new ArrayList<>();
    List<String> wordNames_d = new ArrayList<>();

    List<String> idNames_m = new ArrayList<>();
    List<String> idNames_d = new ArrayList<>();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        corpName = req.getParameter("taskName");
        tableNames_m.add(req.getParameter("formone"));
        tableNames_d.add(req.getParameter("formtwo"));

        wordNames_m.add("name");
        wordNames_d.add("disease_name");

        idNames_m.add("pid");
        idNames_d.add("patient_sn");

        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);


        File file = new File(".\\data" + "PMADcorpus-" + corpName + "M");
        if (!file.exists()) {
            try {
                System.out.println("训练语料不存在，需从数据库获取，速度较慢");
//            PMADDCImporter.dbConnect("com.mysql.jdbc.Driver", "jdbc:mysql:fabric://192.168.4.181/puphdata", "root", "woxnsk");
                PMADDCImporter.generate(idNames_m, wordNames_m, tableNames_m, corpName);
                PMADDCImporter.generate(idNames_d, wordNames_d, tableNames_d, corpName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        String fa = ".\\data" + "PMADcorpus-" + corpName + "M";
        String fb = ".\\data" + "PMADcorpus-" + corpName + "D";
        PMAD pmad = new PMAD(topicNum);
        try {
            Map<String,Double> result = pmad.getAbInfor(fa, fb, PMAD.NUM_ITER, SimiMethod);

//            List<Double> anomalyScores = pmad.getAbScore(fa, fb, PMAD.NUM_ITER, SimiMethod);
//            System.out.println(Mids.size());
//            result = PMADSort.PMADSort(anomalyScores, Mids);


            JSONObject obj = new JSONObject(result);
            System.out.println(obj.toString());

            resp.getWriter().println(obj.toString());


            System.out.println("返回异常排序");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}

