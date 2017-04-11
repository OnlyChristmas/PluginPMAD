package cn.edu.pku.sei.tsr.dbinsight.commons.topics.resource.data;

import java.util.List;

/**
 * Created by 唐爽 on 2017/3/2.
 */
public interface TopicsDocument {
    String getDocumentId();

    TopicsDocument setDocumentId(String documentId);

    List<String> getWords();

    TopicsDocument setWords(List<String> words);
}
