package com.chaungying.common.bean;

import com.google.gson.annotations.Expose;

/**
 * @author 王晓赛
 */
public class Contact implements Comparable<Contact> {


    // 名字
    @Expose
    public String userName;

    // 拼音 用于排序
    @Expose
    public String pinyin;

    // 拼音首字母
    public char firstChar;


    /**
     * 存储拼音和拼音首字母
     *
     * @param pinyin
     */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin.toLowerCase();
        String first = pinyin.substring(0, 1);
        if (first.matches("[A-Za-z]")) {
            firstChar = first.toUpperCase().charAt(0);
        } else {
            firstChar = '#';
        }
    }

    public char getFirstChar() {
        return firstChar;
    }

    public String getPinyin() {
        return pinyin;
    }

    public String getUserName() {
        return userName;
    }


    @Override
    public int compareTo(Contact contact) {
        return this.firstChar - contact.firstChar;
    }
}
