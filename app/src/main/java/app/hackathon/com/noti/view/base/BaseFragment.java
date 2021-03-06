package app.hackathon.com.noti.view.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.hackathon.com.noti.R;

public abstract class BaseFragment extends Fragment {

    protected Bundle args;
    protected Context context;
    private Activity activity;
    private String TAG = BaseFragment.class.getSimpleName();
    private View v;
    private int layoutId;

    public BaseFragment() {
        TAG = getClass().getSimpleName();
    }

    public BaseFragment(@LayoutRes int resId) {
        TAG = getClass().getSimpleName();
        this.layoutId = resId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (layoutId == 0) {
            Log.e(TAG, "layoutId is zero.");
            return null;
        }

        if (v == null) {
            v = inflater.inflate(layoutId, container, false);
        }

        onCreateView(inflater, v);

        if(v.getBackground() == null || v.getBackground().getCurrent() == null)
            v.setBackgroundColor(context.getResources().getColor(R.color.snow));
        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        onFocusFragment();
    }

    public abstract void onCreateView(LayoutInflater inflate, View v);

    public void onFocusFragment() { }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onDestroyView() {
        ViewGroup parentViewGroup = (ViewGroup) v.getParent();

        if (null != parentViewGroup) {
            parentViewGroup.removeView(v);
        }

        super.onDestroyView();
    }
}
