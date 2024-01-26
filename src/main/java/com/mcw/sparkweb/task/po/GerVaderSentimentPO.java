package com.mcw.sparkweb.task.po;

public class GerVaderSentimentPO extends BasePO {

    private String id;
    private String sofa;
    private String begin;
    private String end;
    private String sentiment;
    private String subjectivity;
    private String pos;
    private String neu;
    private String neg;

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

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public String getSubjectivity() {
        return subjectivity;
    }

    public void setSubjectivity(String subjectivity) {
        this.subjectivity = subjectivity;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getNeu() {
        return neu;
    }

    public void setNeu(String neu) {
        this.neu = neu;
    }

    public String getNeg() {
        return neg;
    }

    public void setNeg(String neg) {
        this.neg = neg;
    }
}
