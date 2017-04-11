package cn.edu.pku.sei.tsr.dbinsight.data.importer;

import cn.edu.pku.sei.tsr.dbinsight.commons.topics.resource.data.TopicsDataChunk;
import cn.edu.pku.sei.tsr.dbinsight.commons.topics.resource.data.TopicsDocument;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 帮助构建TopicsDataChunk
 * Created by Shawn on 2017/3/3.
 */
@Service
@Scope("prototype")
public class TopicsDataChunkHelper {

    private Map<String, List<String>> documentMap;

    private TopicsDataChunk dataChunk;

    @Autowired
    private ResourceService resourceService;

    public TopicsDataChunkHelper() {
        documentMap = new HashMap<>();
    }

    public TopicsDataChunkHelper(TopicsDataChunk dataChunk, ResourceService resourceService) {
        this.dataChunk = dataChunk;
        this.resourceService =resourceService;
        documentMap = new HashMap<>();
        dataChunk.getDocuments().forEach(topicsDocument ->
            documentMap.put(topicsDocument.getDocumentId(), topicsDocument.getWords()));
    }

    public void putWord(String documentId, String word) {
        documentMap.computeIfAbsent(documentId, id -> {
            TopicsDocument document = resourceService.newInstance(TopicsDocument.class)
                    .setDocumentId(documentId)
                    .setWords(new ArrayList<>());
            dataChunk.getDocuments().add(document);
            return document.getWords();
        });
        documentMap.get(documentId).add(word);
    }

    public TopicsDataChunk getDataChunk() {
        return dataChunk;
    }

    public TopicsDataChunkHelper setDataChunk(TopicsDataChunk dataChunk) {
        if (this.dataChunk != dataChunk) {
            documentMap = new HashMap<>();
            dataChunk.getDocuments().forEach(topicsDocument ->
                    documentMap.put(topicsDocument.getDocumentId(), topicsDocument.getWords()));
            this.dataChunk = dataChunk;
        }
        return this;
    }

    public void close() {
        dataChunk = null;
        documentMap = null;
    }
}
