package example.prw.gamegeocos;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import example.prw.gamegeocos.api.ApiUtils;
import example.prw.gamegeocos.api.model.SecurityToken;
import example.prw.gamegeocos.api.model.UserCredentials;
import example.prw.gamegeocos.api.service.UserService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class UserProfileFragment extends Fragment {
    public static final String TAG = "user_profile_fragment";
    private Unbinder unbinder;

    @BindView(R.id.user_profile_name)
    TextView mUserNameText;
    @BindView(R.id.user_profile_friends)
    TextView mFriendsText;
    @BindView(R.id.user_profile_boxes)
    TextView mBoxesText;
    @BindView(R.id.user_profile_badges)
    TextView mBadgesText;
    @BindView(R.id.user_profile_exp_progress_bar)
    ProgressBar mExpProgressBar;
    @BindView(R.id.user_profile_current_lvl)
    TextView mUserLvlText;
    @BindView(R.id.user_profile_percent_to_next_lvl)
    TextView mUserPercentToNextLvlText;
    @BindView(R.id.user_profile_progress_bar3)
    ProgressBar mProgress3;
    @BindView(R.id.user_profile_progress_bar2)
    ProgressBar mProgress2;
    @BindView(R.id.user_profile_progress_bar1)
    ProgressBar mProgress1;
    @BindView(R.id.user_profile_image)
    ImageView mUserImage;
    @BindView(R.id.user_profile_pts)
    TextView mUserPtsText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUserNameText.setText("JANUSZ69");
        SpannableString ss1=  new SpannableString("38pts");
        ss1.setSpan(new RelativeSizeSpan(3f), 0,2, 0); // set size
        mUserPtsText.setText(ss1);
        mFriendsText.setText("23");
        mBadgesText.setText("12");
        mBoxesText.setText("4");
        mUserImage.setImageResource(R.drawable.avatar);
        setProgress();

        Toast.makeText(getContext(), "UserProfileResponse created!", Toast.LENGTH_SHORT).show();
    }

    private void setProgress(){
        final int percent = 15;
        final int level = 3;

        mExpProgressBar.setMax(100);
//        mExpProgressBar.setMin(0);
        mExpProgressBar.setProgress(50);
        mUserLvlText.setText("3");

        mUserPercentToNextLvlText.setText(percent + "% to next");
        mUserPercentToNextLvlText.setTypeface(null, Typeface.BOLD);

        mUserLvlText.setText("level " + level);
        mUserLvlText.setTypeface(null, Typeface.BOLD);

        mProgress1.setMax(100);
        mProgress1.setProgress(30);
        mProgress2.setMax(100);
        mProgress2.setProgress(80);
        mProgress3.setMax(100);
        mProgress3.setProgress(60);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
