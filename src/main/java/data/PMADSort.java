package data;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by xuany on 2017.3.26.
 */
public class PMADSort {

    public static Map<Double, String> PMADSort(List<Double> abscore, List<String> testid) {
        Map<Double, String> Result = new TreeMap<>();
        for (int num = 0; num < testid.size(); num++) {
            Result.put(abscore.get(num), testid.get(num));
        }
        return Result;
    }

}
