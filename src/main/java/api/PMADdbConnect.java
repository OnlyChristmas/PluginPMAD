package api;

import cc.mallet.topics.PolylingualTopicModelMultiReadouts;
import core.PMAD;
import data.PMADDCImporter;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static core.PMAD.FA_TRAIN;
import static core.PMAD.FB_TRAIN;

/**
 * Created by xuany on 2017.4.4.
 */
public class PMADdbConnect extends HttpServlet {
    public String url;
    public String user;
    public String passw;
    int topicNum = 20;    //前端给用户默认值20
    int iteration = 1000;    //前端给用户默认值1000


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //String arg = req.getParameter("arg");
        url = req.getParameter("mysqlUrl");
        user = req.getParameter("Username");
        passw = req.getParameter("Password");


        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);


//        if (user != null) {
//            resp.getWriter().println("地址：" + user);
//        }

        PMADDCImporter.dbConnect("com.mysql.jdbc.Driver", url, user, passw);
        try {
            Connection conn = PMADDCImporter.getConnection();
            List tableNames = PMADDCImporter.getAllTableName(conn);

            Map<String, Map> res = new LinkedHashMap<>();

            tableNames.forEach(tableName -> {
                res.put((String) tableName, PMADDCImporter.getPreviewData(20, (String) tableName));
            });
            //System.out.println(res.size());
            //success(res);

            JSONObject obj = new JSONObject(res);

            System.out.println(obj.toString());

            resp.getWriter().println(obj.toString());
            System.out.println("目标数据库连接成功");

        } catch (SQLException e) {
            e.printStackTrace();
        }


        int[] tokensinfor = null;
        PMAD pmad = new PMAD(topicNum);
        try {
            File f = new File(".\\datamodel");
            pmad.model = PolylingualTopicModelMultiReadouts.read(f);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (pmad.model == null) {
            try {
                System.out.println("新训练模型，时间较长");
                pmad.readTrainingData(FA_TRAIN, FB_TRAIN);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            tokensinfor = pmad.train(iteration);
            System.out.println(tokensinfor[0]);
            System.out.println(tokensinfor[1]);
        } else {
            System.out.println("读取已有模型");
            System.out.println(tokensinfor[0]);
            System.out.println(tokensinfor[1]);
        }


    }


}
