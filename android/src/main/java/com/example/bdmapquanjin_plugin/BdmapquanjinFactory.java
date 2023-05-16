package com.example.bdmapquanjin_plugin;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class BdmapquanjinFactory extends PlatformViewFactory {
    private static final String TAG = "BdmapquanjinFactory";
    private final BinaryMessenger mMessenger;
    private final LifecycleProxy mLifecycleProxy;


    public BdmapquanjinFactory(BinaryMessenger messenger, LifecycleProxy lifecycleProxy) {
        super(StandardMessageCodec.INSTANCE);
        Log.d(TAG, "BdmapquanjinFactory");
        mMessenger = messenger;
        mLifecycleProxy = lifecycleProxy;
    }
    @NonNull
    @Override
    public PlatformView create(@Nullable Context context, int viewId, @Nullable Object args) {
        Log.d(TAG, "BdmapquanjinFactory create");
        return new QuanjinView(context,(Map<String,String>)args, mLifecycleProxy);
    }
}
