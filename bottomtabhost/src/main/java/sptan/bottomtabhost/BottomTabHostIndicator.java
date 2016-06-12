package sptan.bottomtabhost;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lp on 2016/6/8.
 */
public class BottomTabHostIndicator extends FragmentTabHost {

    private Class[] mFragmentClasses;

    private int[] mTitles;
    private int[] mNormalDrawables;
    private int[] mSelectedDrawables;
    private int mContainerId;

    private int mItemLayoutId;
    private int mItemImageId;
    private int mItemTextViewId;
    private int mSelectColor;
    private int mNormalColor;

    private FragmentActivity mContext;

    private LayoutInflater mLayoutInflater;
    private Fragment[] mFragments;

    public BottomTabHostIndicator(Context context) {
        super(context);
        if (!(context instanceof FragmentActivity)) {
            throw new RuntimeException("The context must be FragmentActivity!");
        }
        mContext = (FragmentActivity) context;

    }

    public BottomTabHostIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!(context instanceof FragmentActivity)) {
            throw new RuntimeException("The context must be FragmentActivity!");
        }
        mContext = (FragmentActivity) context;
    }

    public BottomTabHostIndicator(Context context,
                                  int containerId,
                                  int itemLayoutId,
                                  int itemImageId,
                                  int itemTextViewId,
                                  int selectColor,
                                  int normalColor,
                                  int[] titles,
                                  int[] normalDrawables,
                                  int[] selectedDrawables,
                                  Class[] fragmentClasses) {
        super(context);
        if (!(context instanceof FragmentActivity)) {
            throw new RuntimeException("The context must be FragmentActivity!");
        }
        mContext = (FragmentActivity) context;

        this.mItemLayoutId = itemLayoutId;
        this.mItemImageId = itemImageId;
        this.mItemTextViewId = itemTextViewId;
        this.mContainerId = containerId;
        this.mSelectColor = selectColor;
        this.mNormalColor = normalColor;

        this.mFragmentClasses = fragmentClasses;
        this.mTitles = titles;
        this.mNormalDrawables = normalDrawables;
        this.mSelectedDrawables = selectedDrawables;
    }

    public BottomTabHostIndicator(Context context,
                                  AttributeSet attrs,
                                  int containerId,
                                  int itemLayoutId,
                                  int itemImageId,
                                  int itemTextViewId,
                                  int selectColor,
                                  int normalColor,
                                  int[] titles,
                                  int[] normalDrawables,
                                  int[] selectedDrawables,
                                  Class[] fragmentClasses) {
        super(context, attrs);
        if (!(context instanceof FragmentActivity)) {
            throw new RuntimeException("The context must be FragmentActivity!");
        }
        mContext = (FragmentActivity) context;
        this.mContainerId = containerId;

        this.mItemLayoutId = itemLayoutId;
        this.mItemImageId = itemImageId;
        this.mItemTextViewId = itemTextViewId;
        this.mSelectColor = selectColor;
        this.mNormalColor = normalColor;

        this.mFragmentClasses = fragmentClasses;
        this.mTitles = titles;
        this.mNormalDrawables = normalDrawables;
        this.mSelectedDrawables = selectedDrawables;
    }

    public void initIndicator(FragmentActivity context,
                              int containerId,
                              int itemLayoutId,
                              int itemImageId,
                              int itemTextViewId,
                              int selectColor,
                              int normalColor,
                              int[] titles,
                              int[] normalDrawables,
                              int[] selectedDrawables,
                              Class[] fragmentClasses) {
        this.mContainerId = containerId;

        this.mItemLayoutId = itemLayoutId;
        this.mItemImageId = itemImageId;
        this.mItemTextViewId = itemTextViewId;
        this.mSelectColor = selectColor;
        this.mNormalColor = normalColor;

        this.mFragmentClasses = fragmentClasses;
        this.mTitles = titles;
        this.mNormalDrawables = normalDrawables;
        this.mSelectedDrawables = selectedDrawables;

        this.mLayoutInflater = context.getLayoutInflater();
        setup(context, context.getSupportFragmentManager(), mContainerId);

        int count = mTitles.length;
        for (int i = 0; i < count; i++) {
            TabSpec tabSpec;
            if (i == 0) {
                tabSpec = this.newTabSpec(context.getString(mTitles[i])).setIndicator(
                        getTabItemView(mSelectedDrawables[i], mTitles[i]));
            } else {
                tabSpec = this.newTabSpec(context.getString(mTitles[i])).setIndicator(
                        getTabItemView(mNormalDrawables[i], mTitles[i]));

            }

            this.getTabWidget().setDividerDrawable(null);
            addTab(tabSpec, mFragmentClasses[i], null);
            getTabWidget().getChildTabViewAt(i).setOnClickListener(new TabOnClickListener(this, i));

        }
        mFragments = new Fragment[mFragmentClasses.length];
        for (int i = 0; i < mFragmentClasses.length; i++) {
            try {
                mFragments[i] = (Fragment) mFragmentClasses[i].newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        setSelectedTab(0);
    }

    public void setSelectedTab(int tabIndex) {
        for (int i = 0; i < this.getTabWidget().getTabCount(); i++) {
            View view = this.getTabWidget().getChildAt(i);
            ImageView imageView = (ImageView) view.findViewById(mItemImageId);
            TextView textView = (TextView) view.findViewById(mItemTextViewId);
            if (i == tabIndex) {
                imageView.setImageResource(mSelectedDrawables[i]);
                textView.setTextColor(getResources().getColor(mSelectColor));
            } else {
                imageView.setImageResource(mNormalDrawables[i]);
                textView.setTextColor(getResources().getColor(mNormalColor));
            }
            this.setCurrentTab(tabIndex);
        }
    }

    /**
     * set text and image
     */
    public View getTabItemView(int imageResId, int stringResId) {
        View view = mLayoutInflater.inflate(mItemLayoutId, null);
        ImageView imageView = (ImageView) view.findViewById(mItemImageId);
        TextView text = (TextView) view.findViewById(mItemTextViewId);
        imageView.setImageResource(imageResId);
        text.setText(mContext.getString(stringResId));
        return view;
    }


    /**
     * set FragmentTabHost click event
     */
    class TabOnClickListener implements OnClickListener {

        private FragmentTabHost fragmentTabHost;
        private int index;

        public TabOnClickListener(FragmentTabHost fragmentTabHost, int index) {
            this.fragmentTabHost = fragmentTabHost;
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            for (int i = 0; i < fragmentTabHost.getTabWidget().getTabCount(); i++) {
                View view = fragmentTabHost.getTabWidget().getChildAt(i);
                ImageView imageView = (ImageView) view.findViewById(mItemImageId);
                TextView textView = (TextView) view.findViewById(mItemTextViewId);
                if (i == index) {
                    imageView.setImageResource(mSelectedDrawables[i]);
                    textView.setTextColor(getResources().getColor(mSelectColor));
                } else {
                    imageView.setImageResource(mNormalDrawables[i]);
                    textView.setTextColor(getResources().getColor(mNormalColor));
                }
                fragmentTabHost.setCurrentTab(index);
            }
        }
    }


    public Class[] getFragmentClasses() {
        return mFragmentClasses;
    }

    public void setFragmentClasses(Class[] mFragmentClasses) {
        this.mFragmentClasses = mFragmentClasses;
    }

    public int[] getTitles() {
        return mTitles;
    }

    public void setTitles(int[] mTitles) {
        this.mTitles = mTitles;
    }

    public int[] getNormalDrawables() {
        return mNormalDrawables;
    }

    public void setNormalDrawables(int[] mNormalDrawables) {
        this.mNormalDrawables = mNormalDrawables;
    }

    public int[] getSelectedDrawables() {
        return mSelectedDrawables;
    }

    public void setSelectedDrawables(int[] mSelectedDrawables) {
        this.mSelectedDrawables = mSelectedDrawables;
    }

    public int getContainerId() {
        return mContainerId;
    }

    public void setContainerId(int mContainerId) {
        this.mContainerId = mContainerId;
    }

    public int getItemLayoutId() {
        return mItemLayoutId;
    }

    public void setItemLayoutId(int mItemLayoutId) {
        this.mItemLayoutId = mItemLayoutId;
    }

    public int getItemImageId() {
        return mItemImageId;
    }

    public void setItemImageId(int mItemImageId) {
        this.mItemImageId = mItemImageId;
    }

    public int getItemTextViewId() {
        return mItemTextViewId;
    }

    public void setItemTextViewId(int mItemTextViewId) {
        this.mItemTextViewId = mItemTextViewId;
    }


}
