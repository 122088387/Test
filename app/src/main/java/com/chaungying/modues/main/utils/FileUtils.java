package com.chaungying.modues.main.utils;

import java.io.File;

/**
 * @author 王晓赛 or 2016/9/2
 *         <p/>
 *         对文件操作的工具类
 */
public class FileUtils {

    /**
     * 删除空目录
     *
     * @param dir 将要删除的目录路径
     */
    public static void doDeleteEmptyDir(String dir) {
        File file = new File(dir);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            if (children != null && children.length > 0) {
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
