package com.example.bdmapquanjin_plugin;


import android.content.Context;

import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;


import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;
import com.baidu.lbsapi.panoramaview.PanoramaView;
import com.baidu.lbsapi.panoramaview.PanoramaViewListener;
import com.baidu.lbsapi.tools.Point;

import java.util.Map;

import io.flutter.plugin.platform.PlatformView;

public class QuanjinView implements PlatformView, DefaultLifecycleObserver {

    private static final String TAG = "QuanjinView";
    private Context mContext;
    private LifecycleProxy mLifecycleProxy;
    private boolean mIsDisposed = false;
    private PanoramaView panoramaView;
    private BMapManager bMapManager;
    private RelativeLayout relativeLayout;
    private double lat;
    private double lon;
    private double titleHeight = 78; // Android 11 以上使用

    public QuanjinView(Context context, Map<String, String> map, LifecycleProxy lifecycleProxy){
        Log.e("map==",map.toString());
        this.mContext = context;
        this.mLifecycleProxy = lifecycleProxy;
        Lifecycle lifecycle = mLifecycleProxy.getLifecycle();
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
        if(bMapManager == null) {
            bMapManager = new BMapManager(mContext.getApplicationContext());
            //设置用户是否同意隐私政策，自v2.9.2版本起增加了隐私合规接口，请务必确保用户同意隐私政策后调用setAgreePrivacy接口
            //true，表示用户同意隐私合规政策
            //false，表示用户不同意隐私合规政策
            bMapManager.setAgreePrivacy(mContext.getApplicationContext(), true);
            bMapManager.init(new MyGeneralListener());
        }
        if(panoramaView == null) {
            panoramaView = new PanoramaView(mContext);
        }

        String latValue = map.get("lat");
        if(latValue!= null) {
            lat = Double.parseDouble(latValue);
        }
        String lonValue = map.get("lon");
        if(lonValue != null) {
            lon = Double.parseDouble(lonValue);
        }
        String heightValue = map.get("titleHeight");
        if(heightValue!= null) {
            titleHeight = Double.parseDouble(heightValue);
        }

         //创建父容器
        relativeLayout = new RelativeLayout(context);

//         初始化成功 设置加载全景
//        LinearLayout linearLayout1 = new LinearLayout(context);
//        linearLayout1.setOrientation(LinearLayout.VERTICAL);
//        linearLayout1.setGravity(Gravity.CENTER_VERTICAL);
//        LinearLayout.LayoutParams linearParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(60,context));
//        linearParams.setMargins(dp2px(15,context),0, dp2px(15,context),0);
//        linearLayout1.setId(0x2022);
//        linearLayout1.setBackgroundColor(Color.parseColor("#000000"));
//        TextView tv1 = new TextView(context);
//        tv1.setTextSize(14);
//        tv1.setText("当前位置无街景");
//        tv1.setColor(Color.parseColor("#000000"));
//        TextView tv2 = new TextView(context);
//        tv2.setText("地址：为什么会往上移动的呢");
//        tv2.setTextSize(14);
//        linearLayout1.addView(tv1);
//        linearLayout1.addView(tv2);
//        relativeLayout.addView(linearLayout1,linearParams);

         RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (Build.VERSION.SDK_INT > 29) { // Android 11 以上,特殊处理
            relativeParams.setMargins(0, dp2px(titleHeight,context),0,0);
        }
//
//        if(panoramaView==null){
//            relativeLayout.addView(Text("当前位置无街景"));
//            return;
//        }
//        LinearLayout.LayoutParams linearParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(60,context));
//        linearParams.setMargins(dp2px(15,context),0, dp2px(15,context),0);
//        TextView tv1 = new TextView(mContext);
//        tv1.setText("当前位置无街景");
//        tv1.setTextSize(14);
//        relativeLayout.addView(tv1,linearParams);
//        textView.setText("当前位置无街景");
//        Toast.makeText(mContext.getApplicationContext(), "当前位置无街景" , Toast.LENGTH_LONG).show();
//        if (hasPanorama(lon,lat)){
//            relativeLayout.addView(panoramaView,relativeParams);
//        }else {
//            Toast.makeText(mContext.getApplicationContext(), "当前位置无街景" , Toast.LENGTH_LONG).show();
//        }
        relativeLayout.addView(panoramaView,relativeParams);
         panoramaView.setShowTopoLink(true);
         panoramaView.setClickable(true);
        // 测试回调函数,需要注意的是回调函数要在setPanorama()之前调用，否则回调函数可能执行异常
         panoramaView.setPanoramaViewListener(new PanoramaViewListener() {

            @Override
            public void onLoadPanoramaBegin() {
            }

            @Override
            public void onLoadPanoramaEnd(String json) {

            }

            @Override
            public void onLoadPanoramaError(String error) {
            }

            @Override
            public void onDescriptionLoadEnd(String json) {
            }

            @Override
            public void onMessage(String msgName, int msgType) {
            }

            @Override
            public void onCustomMarkerClick(String key) {

            }

            @Override
            public void onMoveStart() {

            }

            @Override
            public void onMoveEnd() {

            }
        });
    }

    @Nullable
    @Override
    public View getView() {
        return relativeLayout;
    }

    @Override
    public void dispose() {
        if (mIsDisposed) {
            return;
        }
        mIsDisposed = true;

//        if (mMapController != null) {
//            mMapController.release();
//        }

        if (null != panoramaView) {
            panoramaView.destroy();
            panoramaView = null;
        }
        Lifecycle lifecycle = mLifecycleProxy.getLifecycle();
        if (lifecycle != null) {
            lifecycle.removeObserver(this);
        }
    }

    public PanoramaView getPanoramaView() {
        return panoramaView;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        if (!mIsDisposed && null != panoramaView) {
            Log.e("QuanjinView","onResume");
            panoramaView.onResume();
        }
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        if (!mIsDisposed && null != panoramaView) {
            Log.e("QuanjinView","onPause");
            panoramaView.onPause();
        }
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        if (!mIsDisposed && null != panoramaView) {
            Log.e("QuanjinView","onDestroy");
            panoramaView.destroy();
            panoramaView = null;
        }
    }

    // 常用事件监听，用来处理通常的网络错误，授权验证错误等
    public class MyGeneralListener implements MKGeneralListener {
        @Override
        public void onGetPermissionState(int iError) {
            // 非零值表示key验证未通过
            if (iError != 0) {
                // 授权Key错误：
                Toast.makeText(mContext.getApplicationContext(), "未授权Key,或检查您的网络连接是否正常" , Toast.LENGTH_LONG).show();
            } else {
                Point sourcePoint = new Point(lon, lat);
                panoramaView.setPanorama(sourcePoint.x, sourcePoint.y, PanoramaView.COORDTYPE_GCJ02);
                panoramaView.setPanoramaImageLevel(PanoramaView.ImageDefinition.ImageDefinitionMiddle);

            }
        }
    }

    public static int dp2px(double dpValue, Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
