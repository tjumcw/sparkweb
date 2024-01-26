package com.mcw.sparkweb.task.po;

public class DocumentMetaDataPO extends BasePO {

    private String id;
    private String sofa;
    private String begin;
    private String end;
    private String documentId;
    private String documentUri;
    private String documentBaseUri;
    private String isLastSegment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSofa() {
        return sofa;
    }

    public void setSofa(String sofa) {
        this.sofa = sofa;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentUri() {
        return documentUri;
    }

    public void setDocumentUri(String documentUri) {
        this.documentUri = documentUri;
    }

    public String getDocumentBaseUri() {
        return documentBaseUri;
    }

    public void setDocumentBaseUri(String documentBaseUri) {
        this.documentBaseUri = documentBaseUri;
    }

    public String getIsLastSegment() {
        return isLastSegment;
    }

    public void setIsLastSegment(String isLastSegment) {
        this.isLastSegment = isLastSegment;
    }
}
