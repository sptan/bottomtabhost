package sptan.bottomtabhosttest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import sptan.bottomtabhost.BottomTabHostIndicator;


public class MainActivity extends AppCompatActivity {

    private Class[] mFragmentClasses = {
            InformationFragment.class,
            TopicFragment.class,
            DiscoverFragment.class,
            MeFragment.class
    };

    private int[] mTitles = {
            R.string.information_title,
            R.string.topic_title,
            R.string.discover_title,
            R.string.me_title
    };

    private int[] mNormalDrawables ={
            R.mipmap.info_nor,
            R.mipmap.topic_nor,
            R.mipmap.project_nor,
            R.mipmap.my_nor
    };
    private int[] mSelectedDrawables = {
            R.mipmap.info_sel,
            R.mipmap.topic_sel,
            R.mipmap.project_sel,
            R.mipmap.my_sel

    };
    private int mContainerId = R.id.fragment_container;

    private int mItemLayoutId = R.layout.bottom_tab_item;
    private int mItemImageId = R.id.bottom_tab_item_image;
    private int mItemTextViewId = R.id.bottom_tab_item_text;
    private int mSelectedColor = R.color.theme_color;
    private int mNormalColor = R.color.category_tab_text;

    private BottomTabHostIndicator mBottomTabHostIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomTabHostIndicator = (BottomTabHostIndicator) findViewById(R.id.main_tab_host);
        mBottomTabHostIndicator.initIndicator(this,
                mContainerId,
                mItemLayoutId,
                mItemImageId,
                mItemTextViewId,
                mSelectedColor,
                mNormalColor,
                mTitles,
                mNormalDrawables,
                mSelectedDrawables,
                mFragmentClasses);
        mBottomTabHostIndicator.setSelectedTab(1);
    }
}
