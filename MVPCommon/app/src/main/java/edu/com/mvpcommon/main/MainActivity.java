package edu.com.mvpcommon.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import edu.com.mvpcommon.R;
import edu.com.mvplibrary.ui.activity.AbsBaseActivity;
import edu.com.mvplibrary.ui.widget.CircleImageView;
import edu.com.mvplibrary.ui.widget.imageloader.ImageLoader;
import edu.com.mvplibrary.ui.widget.imageloader.ImageLoaderUtil;
import edu.com.mvplibrary.util.BaseUtil;

/**
 *use {@link NewDrawerMainActivity} instead
 * Created by Anthony on 2016/5/3.
 * Class Note:
 * 1 use{@link DrawerLayout} to
 * acts as a top-level container for window content that allows for
 * interactive "drawer" views to be pulled out from the edge of the window.
 * 2 View in MVP
 * see {@link MainContract}------Manager role of MVP
 * {@link MainPresenter}---------Presenter
 * &{@link MainActivity}---------View
 * &{@link DrawerData}------------Model
 */
public class MainActivity extends AbsBaseActivity implements MainContract.View {
    @Bind(R.id.user_img)
    CircleImageView mUserImg;//user icon
    @Bind(R.id.user_name)
    TextView mUserName; //名字
    @Bind(R.id.user_autograph)
    TextView mUserAutograph; //签名
    @Bind(R.id.toolbar)
    Toolbar mToolBar;//ToolBar
    @Bind(R.id.center_layout)
    FrameLayout mCenterLayout;//中间内容
    @Bind(R.id.home_view)
    FrameLayout mHomeView;//当前的整个view
    @Bind(R.id.left_menu)
    ListView mDrawerMenu;

    private MainPresenter mPresenter;//主页面的Presenter
    private static DrawerLayout mDrawerLayout;//整个抽屉布局
    private int curFragmentPos = -1;//当前的fragment位置(放置fragment的多次创建)

    @Override
    protected int getContentViewID() {
        return R.layout.activity_drawer;
    }

    @Override
    protected void initDagger() {

    }


    @Override
    protected void initViewsAndEvents() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);//static field ,not use ButterKnife


        setToolBar();//set a tool bar before hide it
        //hide toolBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //create and init presenter,
        mPresenter = new MainPresenter(mContext);
        mPresenter.attachView(this);
        //use presenter to load data
        mPresenter.getDrawerData();
        //default select first fragment
        mPresenter.getSelectView(1);//chattingListFragment

        curFragmentPos = -1;
    }

    protected void setToolBar() {
        if (mToolBar != null) {
            setSupportActionBar(mToolBar);
        }
        final ActionBar ab = getSupportActionBar();
        if (ab == null)
            return;
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public static void openDrawer() {
        if (mDrawerLayout == null)
            return;
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer() {
        if (mDrawerLayout == null)
            return;
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    /**
     * close drawer if drawer is opening
     */
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onDrawerListGet(final ArrayList<DrawerData.DrawerItem> mDrawerItems) {
        NavDrawerListAdapter mAdapter = new NavDrawerListAdapter(this,
                mDrawerItems);

        if (mDrawerMenu != null) {
            mDrawerMenu.setAdapter(mAdapter);
            mDrawerMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!BaseUtil.isEmpty(mDrawerItems, i)) {
                        DrawerData.DrawerItem drawerItem = mDrawerItems.get(i);
                        if (drawerItem != null) {
                            mPresenter.getSelectView(i + 1);
                            curFragmentPos = i + 1;
                        }
                    }
                }
            });
        }

    }

    @Override
    public void setDrawerIcon(String url) {

        ImageLoader imageLoader = new ImageLoader.Builder().url(url).imgView(mUserImg).build();
        ImageLoaderUtil.getInstance().loadImage(this, imageLoader);
    }

    @Override
    public void onSelectFragmentGet(Fragment fragment, int position) {
        if (curFragmentPos == position) {
            closeDrawer();
        } else {
            closeDrawer();
            curFragmentPos = position;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.center_layout, fragment).commit();
        }

    }

    @Override
    public void showMessage(String msg) {
        showMessageDialog(msg);
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void showProgress(String message) {
        showProgressDialog(message);
    }

    @Override
    public void showProgress(String message, int progress) {
        showProgressDialog(message, progress);
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void showErrorMessage(String msg, String content) {
        showErrorDialog(msg, content, new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });
    }


    @OnClick(R.id.user_img)
    public void onClick() {
        mPresenter.getSelectView(0);
    }
}
