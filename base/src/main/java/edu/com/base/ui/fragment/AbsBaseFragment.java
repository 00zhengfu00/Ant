package edu.com.base.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

import edu.com.base.ui.BaseView;
import edu.com.base.util.AppUtils;
import edu.com.base.util.ToastUtils;
import edu.com.mvplibrary.R;

//import butterknife.ButterKnife;

/**
 * Created by Anthony on 2016/2/25.
 * Class Note:
 *
 * Base Fragment for all the Fragment defined in the project
 * 1 extended from {@link AbsBaseFragment} to do
 * some base operation.
 * 2 do operation in initViewAndEvents(){@link #initViewsAndEvents(View rootView)}
 */
public abstract class AbsBaseFragment extends Fragment implements BaseView{
    /**
     * Log tag
     */
    protected static String TAG_LOG = null;
    /**
     * url passed into fragment
     */
    public static String EXTRA_URL = "url";
    private String mUrl;
    /**
     * activity context of fragment
     */
    protected Context mContext;

    public SweetAlertDialog mProgressDialog;
    public SweetAlertDialog mWarningDialog;
    public SweetAlertDialog mErrorDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG_LOG = this.getClass().getSimpleName();
        mContext = getActivity();
        if (getArguments() != null) {
            mUrl = getArguments().getString(EXTRA_URL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getContentViewID() != 0) {
            return inflater.inflate(getContentViewID(), null);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initViewsAndEvents(view);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public String getFragmentUrl() {
        return mUrl;
    }


    /**
     * override this method to do operation in the fragment
     */
    protected abstract void initViewsAndEvents(View rootView);


    /**
     * override this method to return content view id of the fragment
     */
    protected abstract int getContentViewID();

    /**
     * show Message in screen
     */
    protected void showMessageDialog(String msg) {
        if (null != msg && !AppUtils.isEmpty(msg)) {
//            Snackbar.make(this, msg, Snackbar.LENGTH_SHORT).show();
            ToastUtils.getInstance().showToast(msg);
        }
    }

    public void showWarningDialog(String title, String content, SweetAlertDialog.OnSweetClickListener listener) {
        mWarningDialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText("确定")
                .setCancelText("取消")
                .setConfirmClickListener(listener);

        mWarningDialog.show();
    }

    public void showErrorDialog(String title, String content, SweetAlertDialog.OnSweetClickListener listener) {
        mErrorDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                .setConfirmText("确定")
                .setTitleText(title)
                .setContentText(content)
                .setConfirmClickListener(listener);
        mErrorDialog.show();
    }

    public void showProgressDialog(String message) {
        mProgressDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        mProgressDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        mProgressDialog.setTitleText(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

    }


    public void showProgressDialog(String message, int progress) {
        mProgressDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        mProgressDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        mProgressDialog.setTitleText(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.getProgressHelper().setProgress(progress);
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String msg) {
        showMessageDialog(msg);
    }

    @Override
    public void close() {
//        finish();
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


}

