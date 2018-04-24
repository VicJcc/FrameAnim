package jcc.example.com.frameanim;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mIvFrame;
    private TextView mTvStart;

    private JSurfaceView mSurFrame;
    private TextView mTvSurStart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIvFrame = findViewById(R.id.iv_frame);
        mTvStart = findViewById(R.id.tv_start_frame);

        mSurFrame = findViewById(R.id.suv_frame);
        mTvSurStart = findViewById(R.id.tv_start_sur_frame);

        mTvStart.setOnClickListener(this);
        mTvSurStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_start_frame:
                mIvFrame.setBackgroundResource(R.drawable.fram);
                AnimationDrawable animationDrawable = (AnimationDrawable) mIvFrame.getBackground();
                animationDrawable.start();
                break;
            case R.id.tv_start_sur_frame:
                mSurFrame.startAnim(JAnimRes.frams_f);
                break;
        }
    }
}
