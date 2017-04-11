package algorithm;

import java.util.List;

/**
 * Created by xuany on 2017.3.25.
 */
public class PMADSimMeasure {

    public static double innerProduct(double[] distA, double[] distB) {
        double r = 0;
        for (int i = 0; i < distA.length; i++) {
            r += distA[i] * distB[i];
        }
        return r;
    }


    public static double cosineSimilarity(double[] vectorA, double[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    public static double klDivergence(double[] p1, double[] p2) {
        double klDiv = 0.0;
        for (int i = 0; i < p1.length; ++i) {
            if (p1[i] == 0) {
                continue;
            }
            if (p2[i] == 0.0) {
                continue;
            }
            klDiv += p1[i] * Math.log(p1[i] / p2[i]);
        }
        return klDiv; // moved this division out of the loop -DM
    }



    public static double sum(List<Double> ensScores) {
        double sum = 0;
        for (Double double1 : ensScores) {
            sum += double1;
        }
        return sum;
    }

}

