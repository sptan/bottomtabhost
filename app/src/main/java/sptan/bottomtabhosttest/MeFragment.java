package sptan.bottomtabhosttest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by lp on 2016/6/9.
 */
public class MeFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView tvTitle = new TextView(super.getActivity());
        tvTitle.setText("我");
        tvTitle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setTextSize(30);
        return tvTitle;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }
}
