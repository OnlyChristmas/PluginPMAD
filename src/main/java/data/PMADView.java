package data;

import org.hibernate.validator.internal.xml.PropertyType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xuany on 2017.3.14.
 */
public class PMADView {



    private String name;
    /*
     * 扩展LDA中词的属性
     * 原始LDA中该项为空LIST
     */
    private List<PropertyType> types = new ArrayList<>();
    /*
     * 文档列表
     */
    private List<PMADDocument> docs = new ArrayList<>();

    /*
     * 词表，从词到整数的映射，需要事先进行预处理
     */
    public Map<Integer, String> Idx2Word = new HashMap<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PropertyType> getTypes() {
        return types;
    }

    public void setTypes(List<PropertyType> types) {
        this.types = types;
    }

    public List<PMADDocument> getDocs() {
        return docs;
    }

    public void setDocs(List<PMADDocument> docs) {
        this.docs = docs;
    }



}
