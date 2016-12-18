package com.chaungying.round_malls.interface_;

import com.chaungying.round_malls.view.AddSubtractBtnView;

/**
 * @author 王晓赛 or 2016/9/22
 */
public interface AddSubStractBtnListtener {

    /**
     * 当在ListView中使用时
     * @param view  比对当前按钮的id 和 在当前listview中的位置  为了防止复用的发生
     * @param num   当前加减按钮上的数字
     */
    void getNumListtener(AddSubtractBtnView view, int num);


}
