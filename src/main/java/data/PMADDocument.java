package data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuany on 2017.3.14.
 */
public class PMADDocument {

    public enum PropertyType {

        CATAGORICAL, NUMERIC
    }


    private String id;

    /*
     * 文档中的词。需要事先将词映射为一个整数值
     */
    public String[] words;

    public List<Object> properties = null;

    public PMADDocument(String id, int length, List<PropertyType> types) {
        this.id = id;
        this.words = new String[length];
        if (types != null && types.size() != 0) {
            properties = new ArrayList<>();
            for (PropertyType type : types) {
                Object props = null;
                if (type == PropertyType.CATAGORICAL) {
                    props = new int[length];
                }
                if (type == PropertyType.NUMERIC) {
                    props = new double[length];
                }
                properties.add(props);
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    private List<String> ID = new ArrayList<String>();
    private static List<String> DiseaseList = new ArrayList<String>();
    private static List<String> MedicineList = new ArrayList<String>();

    //DiseaseList.add(0, words[0]);
    public List<String> Words2ID(String flag) {
        boolean execute;
        if (flag.equals("D")) {
            for (int i = 0; i < words.length; i++) {
                execute = true;
                for (int k = 0; k < DiseaseList.size(); k++) {   // the first cycle don`t entry
                    if (DiseaseList.get(k).equals(words[i])) {
                        ID.add(i, flag + k);
                        execute = false;
                        break;
                    } else {
                        continue;
                    }
                }
                if (execute) {
                    ID.add(i, flag + DiseaseList.size());
                    DiseaseList.add(DiseaseList.size(), words[i]);
                }
            }
            return ID;
        } else {
            for (int i = 0; i < words.length; i++) {
                execute = true;
                for (int k = 0; k < MedicineList.size(); k++) {   // the first cycle don`t entry
                    if (MedicineList.get(k).equals(words[i])) {
                        ID.add(i, flag + k);
                        execute = false;
                        break;
                    } else {
                        continue;
                    }
                }
                if (execute) {
                    ID.add(i, flag + MedicineList.size());
                    MedicineList.add(MedicineList.size(), words[i]);
                }
            }
            return ID;
        }


    }


}
