package data;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuany on 2017.3.15.
 */
public class PMADModelInput {


    private int T = 20;
    private double alpha = 50 / T;
    private double beta = 0.01;

    /*
     * 对应于LDA模型中的多视图数据，相当于数据库中的多个表
     * e.g., 多语言lda
     * 一个LDAView对应一个表中的一个id加word生成的一组文档
     */
    private List<PMADView> views = new ArrayList<>();


    public int getT() {
        return T;
    }

    public void setT(int t) {
        T = t;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public void addLDAView(PMADView view) {
        views.add(view);
    }

    public List<PMADView> getViews() {
        return views;
    }
}

