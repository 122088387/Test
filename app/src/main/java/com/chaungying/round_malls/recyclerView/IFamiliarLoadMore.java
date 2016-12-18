package com.chaungying.round_malls.recyclerView;

import android.view.View;

/**
 * LoadMoreView interface
 * Created by iWgang on 16/04/13.
 * https://github.com/iwgang/FamiliarRecyclerView
 */
public interface IFamiliarLoadMore {

    void showLoading();

    void showNormal();

    boolean isLoading();

    View getView();

}
