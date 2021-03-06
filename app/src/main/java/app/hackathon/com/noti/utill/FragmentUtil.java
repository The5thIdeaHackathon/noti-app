package app.hackathon.com.noti.utill;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import app.hackathon.com.noti.R;

public class FragmentUtil {

    private static final String TAG = FragmentUtil.class.getSimpleName();
    private FragmentManager mFragmentManager;
    private int mContainerId;

    public FragmentUtil(FragmentManager fm) {
        this.mFragmentManager = fm;
    }

    public FragmentUtil(FragmentManager fm, int containerId) {
        this.mFragmentManager = fm;
        this.mContainerId = containerId;
    }

    public void add(Fragment fragment, Bundle b) {
        if (mContainerId == 0) {
            Log.e(TAG, "Fragment Container Layout Id is zero.");
            return;
        }

        if (mFragmentManager == null) {
            Log.e(TAG, "FragmentManager fm is null.");
            return;
        }

        if(b != null){
            fragment.setArguments(b);
        }

        if(mFragmentManager.getFragments().get(0) != fragment){
            mFragmentManager.beginTransaction()
                    .replace(mContainerId, fragment)
                    .commitAllowingStateLoss();
        }
    }

    public void addInOut(Fragment fragment, Bundle b) {
        if (mContainerId == 0) {
            Log.e(TAG, "Fragment Container Layout Id is zero.");
            return;
        }

        if (mFragmentManager == null) {
            Log.e(TAG, "FragmentManager fm is null.");
            return;
        }

        if(b != null){
            fragment.setArguments(b);
        }

        if(mFragmentManager.getFragments().size() != 0){
            mFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out, R.anim.fragment_in_backstack, R.anim.fragment_out_backstack)
                    .replace(mContainerId, fragment)
                    .addToBackStack(fragment.toString())
                    .commitAllowingStateLoss();
        }else{
            mFragmentManager.beginTransaction()
                    .add(mContainerId, fragment)
                    .commitAllowingStateLoss();
        }
    }

    public void addFadeInOut(Fragment fragment, Bundle b) {
        if (mContainerId == 0) {
            Log.e(TAG, "Fragment Container Layout Id is zero.");
            return;
        }

        if (mFragmentManager == null) {
            Log.e(TAG, "FragmentManager fm is null.");
            return;
        }

        if(b != null){
            fragment.setArguments(b);
        }

        if(mFragmentManager.getFragments().size() != 0){
            mFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                    .replace(mContainerId, fragment)
                    .addToBackStack(fragment.toString())
                    .commitAllowingStateLoss();
        } else{
            mFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .add(mContainerId, fragment)
                    .commitAllowingStateLoss();
        }
    }

    public void back() {
        if (mFragmentManager == null) {
            Log.e(TAG, "FragmentManager fm is null.");
            return;
        }

        mFragmentManager.popBackStack();
    }

    public void clear() {
        if (mFragmentManager == null) {
            Log.e(TAG, "FragmentManager fm is null.");
            return;
        }

        try {
            mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
        }
    }
//
//    public void showDialog(DialogFragment dialogFragment) {
//        if (mFragmentManager == null) {
//            Log.e(TAG, "FragmentManager fm is null.");
//            return;
//        }
//
//        dialogFragment.show(mFragmentManager, "dialog");
//    }
//
//    public void dismissDialog() {
//        if (mFragmentManager == null) {
//            Log.e(TAG, "FragmentManager fm is null.");
//            return;
//        }
//
//        Fragment fragment = mFragmentManager.findFragmentByTag("dialog");
//        if (fragment != null) {
//            mFragmentManager.beginTransaction().remove(fragment)
//                    .commitAllowingStateLoss();
//        }
//
//    }
}
