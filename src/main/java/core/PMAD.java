package core;

import algorithm.PMADSimMeasure;
import cc.mallet.topics.PolylingualTopicModelMultiReadouts;
import cc.mallet.topics.TopicInferencer;
import cc.mallet.types.InstanceList;
import data.PMADDCImporter;
import data.PMADDocument;
import data.PMADView;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuany on 2017.3.13.
 */
public class PMAD {

    public static final String FA_TRAIN = ".\\data\\name-d";
    public static final String FB_TRAIN = ".\\data\\name-m";
    public static final String FA_TEST = "data\\test-d";
    public static final String FB_TEST = "data\\test-m";
    public static final int NUM_ITER = 1000;
    public int numTopic;
    private InstanceList[] train = null;
    public static PolylingualTopicModelMultiReadouts model = null;
    private PMAD.PmadSimilarityMeasures simMeasure = PMAD.PmadSimilarityMeasures.DOT;
    private String string;
    private String fb;

    public PMAD(int numTopic) {
        this.numTopic = numTopic;
        train = new InstanceList[2];
    }

    public List<Double> getAnoScore(String faTest, String fbTest, int numIter) throws FileNotFoundException {
        List<Double> rs = new ArrayList<>();
        InstanceList insesAtest = PMADDCImporter.readInstances(faTest);
        InstanceList insesBtest = PMADDCImporter.readInstances(fbTest);
        TopicInferencer infA = model.getInferencer(0);
        TopicInferencer infB = model.getInferencer(1);

        for (int i = 0; i < insesAtest.size(); i++) {
            double[] distA = infA.getSampledDistribution(insesAtest.get(i), numIter, 10, 200);
            double[] distB = infB.getSampledDistribution(insesBtest.get(i), numIter, 10, 200);
            double as = this.getScore(distA, distB);
            rs.add(as);
        }
        return rs;
    }


    public enum PmadSimilarityMeasures {
        DOT, KL, EUC, COS, PS
    }


    private double getScore(double[] distA, double[] distB) {
        switch (this.simMeasure) {
            case DOT:
                return -PMADSimMeasure.innerProduct(distA, distB);
            case EUC:
                return new EuclideanDistance().compute(distA, distB);
            case COS:
                return -PMADSimMeasure.cosineSimilarity(distA, distB);
            case PS:
                double rs = new PearsonsCorrelation().correlation(distA, distB);
                if (Double.isNaN(rs)) {
                    rs = 0;
                }
                return -rs;
            case KL:
                return PMADSimMeasure.klDivergence(distA, distB);
            default:
                return -PMADSimMeasure.innerProduct(distA, distB);
        }
    }


    private void setSimMeasure(PMAD.PmadSimilarityMeasures m) {
        this.simMeasure = m;

    }

    public List<Double> getAbScore(String faTest, String fbTest, int numIter, String Method) throws FileNotFoundException {
        List<Double> rs = new ArrayList<>();
        InstanceList insesAtest = PMADDCImporter.readInstances(faTest);
        InstanceList insesBtest = PMADDCImporter.readInstances(fbTest);
        TopicInferencer infA = model.getInferencer(0);
        TopicInferencer infB = model.getInferencer(1);
        for (int i = 0; i < insesAtest.size(); i++) {
            double[] distA = infA.getSampledDistribution(insesAtest.get(i), numIter, 10, 200);
            double[] distB = infB.getSampledDistribution(insesBtest.get(i), numIter, 10, 200);
            double as = this.getAbScore(distA, distB, Method);
            rs.add(as);
        }
        return rs;
    }

    private double getAbScore(double[] distA, double[] distB, String method) {

        switch (method) {
            case "DOT":
                return -PMADSimMeasure.innerProduct(distA, distB);
            case "EUC":
                return new EuclideanDistance().compute(distA, distB);
            case "COS":
                return -PMADSimMeasure.cosineSimilarity(distA, distB);
            case "PS":
                double rs = new PearsonsCorrelation().correlation(distA, distB);
                if (Double.isNaN(rs)) {
                    rs = 0;
                }
                return -rs;
            case "KL":
                return PMADSimMeasure.klDivergence(distA, distB);
            default:
                return -PMADSimMeasure.innerProduct(distA, distB);
        }
    }


//    public static ParallelTopicModel trainModel(PMADModelInput pmadModelInput) throws Exception {
//        PMADView ldaView = pmadModelInput.getViews().get(0);
//        ldaView2inputFile(ldaView,"D");
//
//        ArrayList<Pipe> pipeList = new ArrayList<Pipe>();
//        pipeList.add(new CharSequence2TokenSequence(Pattern.compile("\\S+")));
//        pipeList.add(new TokenSequence2FeatureSequence());
//
//        InstanceList instances = new InstanceList(new SerialPipes(pipeList));
//
//        Reader fileReader = new InputStreamReader(new FileInputStream(new File(ldaView.getName() + ".txt")));
//        instances.addThruPipe(new CsvIterator(fileReader, Pattern.compile("^(\\S*)[\\s,]*(\\S*)[\\s,]*(.*)$"), 3, 2, 1));
//
//        ParallelTopicModel model = new ParallelTopicModel(pmadModelInput.getT(), pmadModelInput.getAlpha(), pmadModelInput.getBeta());
//        model.addInstances(instances);
//        model.setNumThreads(8);
//        model.setNumIterations(200);
//        model.estimate();
//
//        return model;


//        // Show the words and topics in the first instance
//
//        // The data alphabet maps word IDs to strings
//        Alphabet dataAlphabet = instances.getDataAlphabet();
//
//        FeatureSequence tokens = (FeatureSequence) model.getData().get(0).instance.getData();
//        LabelSequence topics = model.getData().get(0).topicSequence;
//
//        Formatter out = new Formatter(new StringBuilder(), Locale.US);
//        for (int position = 0; position < tokens.getLength(); position++) {
//            out.format("%s-%d ", dataAlphabet.lookupObject(tokens.getIndexAtPosition(position)), topics.getIndexAtPosition(position));
//        }
//        System.out.println(out);
//
//        // Estimate the topic distribution of the first instance,
//        //  given the current Gibbs state.
//        double[] topicDistribution = model.getTopicProbabilities(0);
//
//        // Get an array of sorted sets of word ID/count pairs
//        ArrayList<TreeSet<IDSorter>> topicSortedWords = model.getSortedWords();
//
//        System.out.println(tokens);
//        System.out.println(topics);
//
//        // Show top 5 words in topics with proportions for the first document
//        for (int topic = 0; topic < modelInput.getT(); topic++) {
//            Iterator<IDSorter> iterator = topicSortedWords.get(topic).iterator();
//
//            out = new Formatter(new StringBuilder(), Locale.US);
//            out.format("%d\t%.3f\t", topic, topicDistribution[topic]);
//            int rank = 0;
//            while (iterator.hasNext() && rank < 5) {
//                IDSorter idCountPair = iterator.next();
//                out.format("%s (%.0f) ", dataAlphabet.lookupObject(idCountPair.getID()), idCountPair.getWeight());
//                rank++;
//            }
//            System.out.println(out);
//        }
//
//        // Create a new instance with high probability of topic 0
//        StringBuilder topicZeroText = new StringBuilder();
//        Iterator<IDSorter> iterator = topicSortedWords.get(0).iterator();
//
//        int rank = 0;
//        while (iterator.hasNext() && rank < 5) {
//            IDSorter idCountPair = iterator.next();
//            topicZeroText.append(dataAlphabet.lookupObject(idCountPair.getID()) + " ");
//            rank++;
//        }
//
//        // Create a new instance named "test instance" with empty target and source fields.
//        InstanceList testing = new InstanceList(instances.getPipe());
//        testing.addThruPipe(new Instance(topicZeroText.toString(), null, "test instance", null));
//
//        TopicInferencer inferencer = model.getInferencer();
//        double[] testProbabilities = inferencer.getSampledDistribution(testing.get(0), 10, 1, 5);
//        System.out.println("0\t" + testProbabilities[0]);
//    }


    public static void ldaView2inputFile(PMADView ldaView, String sign) {
        File file = new File(".\\data" + "PMADcorpus-" + sign);
        try {
            file.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (PMADDocument doc : ldaView.getDocs()) {
                out.write(doc.getId());
                out.write("\t");
                out.write(sign);
                out.write("\t");
                for (String s : doc.Words2ID(sign)) {
                    out.write(s);
                    out.write(" ");
                }
                out.newLine();
            }
            out.flush();
            out.close();
            System.out.println("写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ldaView2inputFile(PMADView ldaView, String sign, String name) {
        File file = new File(".\\data" + "PMADcorpus-" + name + sign);
        try {
            int num = 0;
            file.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (PMADDocument doc : ldaView.getDocs()) {

                if (num != 0) {
                    out.newLine();
                }
                out.write(doc.getId());
                out.write("\t");
                out.write(sign);
                out.write("\t");
                for (String s : doc.Words2ID(sign)) {
                    out.write(s);
                    out.write(" ");
                }
                num++;

            }
            out.flush();
            out.close();
            System.out.println("写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int[] train(int numIter) throws IOException {
        model = new PolylingualTopicModelMultiReadouts(numTopic, 50);
        model.addInstances(train);
        model.setNumIterations(numIter);
        model.showTopicsInterval = 1000;
        model.setOptimizeInterval(0);
        model.setBurninPeriod(200);
        model.estimate();
        int[] tokens = {0, 0};
        tokens[0] = model.maxTokens;
        tokens[1] = model.totalTokens;


//        model.write(new File(".\\data" + "model"));

        System.out.println("模型已保存");
        return tokens;
    }

    public void readTrainingData(String fa, String fb) throws FileNotFoundException, SQLException {

//        PMADDCImporter.dbConnect();
//        PMADView medicine = PMADDCImporter.generateASimpleView("pid", "name", "pluginpmad_trainm");
//        PMADModelInput medicineInput = new PMADModelInput();
////        pmadModelInput.setT(5);
////        pmadModelInput.setAlpha(8.5);
////        pmadModelInput.setBeta(0.01);
//        medicineInput.addLDAView(medicine);
//        System.out.println("开始写入");
//        PMADView medicineView = medicineInput.getViews().get(0);
//        ldaView2inputFile(medicineView,"M");
//
//        PMADDCImporter.dbConnect("com.mysql.jdbc.Driver","jdbc:mysql://192.168.4.181/puphdata",
//                "root", "woxnsk","diagnosis_part");
//        System.out.println("连接成功");
//        PMADView diagnose = PMADDCImporter.generateASimpleView("patient_sn", "disease_name", "pluginpmad_traindd");
//        PMADModelInput diagnoseInput = new PMADModelInput();
//        diagnoseInput.addLDAView(diagnose);
//        System.out.println("开始写入");
//        PMADView diagnoseView = diagnoseInput.getViews().get(0);
//        ldaView2inputFile(diagnoseView,"D");

//        -----------------------------
//        try {
//            trainModel(pmadModelInput);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        train[0] = PMADDCImporter.readInstances(fa);
        //PMADDCImporter.ClearFile(fb);
        train[1] = PMADDCImporter.readInstances(fb);


    }

    public void readTrainFile(String corp) throws FileNotFoundException {
        String fa = ".\\data" + "PMADcorpus-" + corp + "M";
        String fb = ".\\data" + "PMADcorpus-" + corp + "D";
        train[0] = PMADDCImporter.readInstances(fa);
        train[1] = PMADDCImporter.readInstances(fb);
    }


    public static void main(String[] args) throws IOException, SQLException {

        PMAD pmad = new PMAD(20);

        //read training data from two files
        pmad.readTrainingData(FA_TRAIN, FB_TRAIN);

        //train the model
        System.out.println("开始训练");
        int[] test = pmad.train(NUM_ITER);


        //choose a similarity measure
        pmad.setSimMeasure(PmadSimilarityMeasures.DOT);

        //read and calculate the anomaly score for test data
        System.out.println("开始检测");
        List<Double> anomalyScores = pmad.getAnoScore(FA_TEST, FB_TEST, NUM_ITER);

        System.out.println(anomalyScores);
    }
}
