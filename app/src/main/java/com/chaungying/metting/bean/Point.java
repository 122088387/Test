package com.chaungying.metting.bean;

/**
 * @author 王晓赛 or 2016/7/8
 */
public class Point implements Comparable<Point>{
    public int x;
    public int y;

    @Override
    public int compareTo(Point another) {
        return this.x - another.x;
    }
}
