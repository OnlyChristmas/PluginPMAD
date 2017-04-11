package api;

import com.simpulife.framework.Action;
import com.simpulife.framework.annotation.ActionMapping;
import com.simpulife.framework.annotation.Parameter;
import com.simpulife.framework.annotation.aop.Async;
import data.PMADDCImporter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuany on 2017.3.25.
 */

@ActionMapping("PMADgencorp")
public class PMADGenTestCorpus extends Action {

    @Parameter
    List<String> idNames_d = new ArrayList<>();
    @Parameter
    List<String> wordNames_d = new ArrayList<>();
    @Parameter
    List<String> tableNames_d = new ArrayList<>();
    @Parameter
    String corpName;
//    @Parameter
//    @AllowEmpty
//    String des_d;

    @Parameter
    List<String> idNames_m = new ArrayList<>();
    @Parameter
    List<String> wordNames_m = new ArrayList<>();
    @Parameter
    List<String> tableNames_m = new ArrayList<>();

//    @Parameter
//    @AllowEmpty
//    String des_m;

    @Override
    public void doAction() {
//        //DBUtils.dbConnect();
//        if (MetaDB.isNameUsed(corpName))
//            fail(2001, corpName);
        try {
            gencorp();
            success("Test Corpus have generated");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Async
    public void gencorp() throws SQLException {
        PMADDCImporter.generate(idNames_m, wordNames_m, tableNames_m, corpName);
        PMADDCImporter.generate(idNames_d, wordNames_d, tableNames_d, corpName);

    }

}
