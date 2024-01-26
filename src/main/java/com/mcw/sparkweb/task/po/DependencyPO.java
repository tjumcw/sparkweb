package com.mcw.sparkweb.task.po;

/**
 * @author miaochangwei1
 * @Package : com.mcw.sparkweb.task.po
 * @Description : TODO
 * @Create on : 2024/1/26 20:24
 **/
public class DependencyPO extends BasePO {

    private String id;

    private String sofa;

    private String begin;

    private String end;

    private String governor;

    private String dependent;

    private String dependencyType;

    private String flavor;

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

    public String getGovernor() {
        return governor;
    }

    public void setGovernor(String governor) {
        this.governor = governor;
    }

    public String getDependent() {
        return dependent;
    }

    public void setDependent(String dependent) {
        this.dependent = dependent;
    }

    public String getDependencyType() {
        return dependencyType;
    }

    public void setDependencyType(String dependencyType) {
        this.dependencyType = dependencyType;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }
}
