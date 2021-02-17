package com.perry.devtool.bo;

import java.util.List;
import java.util.Map;

/**
 * @author Perry
 * @date 2021/2/9
 */
public class ComSetData {
    private String dataKey;
    private String v;
    private Map<Object,Object> hashFV;
    private List listV;
    private double zsetScore;

    public ComSetData(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public Map<Object, Object> getHashFV() {
        return hashFV;
    }

    public void setHashFV(Map<Object, Object> hashFV) {
        this.hashFV = hashFV;
    }

    public List getListV() {
        return listV;
    }

    public void setListV(List listV) {
        this.listV = listV;
    }

    public double getZsetScore() {
        return zsetScore;
    }

    public void setZsetScore(double zsetScore) {
        this.zsetScore = zsetScore;
    }
}
