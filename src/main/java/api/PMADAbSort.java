package api;

import com.simpulife.framework.Action;
import com.simpulife.framework.annotation.ActionMapping;
import com.simpulife.framework.annotation.Parameter;
import core.PMAD;
import data.PMADSort;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static data.PMADDCImporter.Mids;

/**
 * Created by xuany on 2017.3.26.
 */
@ActionMapping("PMADAbSort")
public class PMADAbSort extends Action {

    @Parameter
    int topicNum;   //前端给默认值20

    @Parameter
    String SimiMethod;  //前端默认DOT

    @Parameter
    String corpName;

    @Override
    public void doAction() {
        PMAD pmad = new PMAD(topicNum);
        String fa = ".\\data" + "PMADcorpus-" + corpName + "M";
        String fb = ".\\data" + "PMADcorpus-" + corpName + "D";

        try {
            List<Double> anomalyScores = pmad.getAbScore(fa, fb, PMAD.NUM_ITER, SimiMethod);
            Map<Double,String> result = new TreeMap<>();
            result = PMADSort.PMADSort(anomalyScores, Mids);
            success(result);
            System.out.println("返回异常排序");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        success();
    }
}
