package com.chaungying.site_repairs.bean;

/**
 * @author 王晓赛 or 2016/9/3
 *
 * 上传涂鸦文件时  文件的bean
 */
public class HandDrawSaveBean implements Comparable<HandDrawSaveBean>{

    String path;
    int key;
    String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public int compareTo(HandDrawSaveBean another) {
        return this.key - another.key;
    }
}
