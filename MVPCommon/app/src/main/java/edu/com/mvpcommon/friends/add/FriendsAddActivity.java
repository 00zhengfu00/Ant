package edu.com.mvpcommon.friends.add;

import android.view.View;

import edu.com.mvpcommon.R;
import edu.com.mvplibrary.ui.activity.AbsSwipeBackActivity;

/**
 * Created by Anthony on 2016/5/10.
 * Class Note:
 */
public class FriendsAddActivity  extends AbsSwipeBackActivity {


    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_friends_add;
    }
}
