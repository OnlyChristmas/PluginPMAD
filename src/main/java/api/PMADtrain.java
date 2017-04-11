package api;

import com.simpulife.framework.Action;
import core.PMAD;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import static core.PMAD.FA_TRAIN;
import static core.PMAD.FB_TRAIN;


/**
 * Created by xuany on 2017.3.26.
 */

public class PMADtrain extends Action {

    int topicNum=20;    //前端给用户默认值20
    //    @Parameter
//    double alpha;
//    @Parameter
//    double beta;

    String corpName;


    int iteration=1000;    //前端给用户默认值1000


    public void Action(){};

    @Override
    public void doAction() {
        String infor = "Model training success " + corpName;
        try {
            train();
            success(infor);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    public int[] train() throws FileNotFoundException, SQLException {
//        int[] tokensinfor = null;
//        PMAD pmad = new PMAD(topicNum);
//        pmad.readTrainingData(FA_TRAIN, FB_TRAIN);
//        tokensinfor = pmad.train(iteration);
//        return tokensinfor;
//    }

        public void train() throws IOException, SQLException {
        int[] tokensinfor = null;
        PMAD pmad = new PMAD(topicNum);
        pmad.readTrainingData(FA_TRAIN, FB_TRAIN);
        tokensinfor = pmad.train(iteration);
    }
}
