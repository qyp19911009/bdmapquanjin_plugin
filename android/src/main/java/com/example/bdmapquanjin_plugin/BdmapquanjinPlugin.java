package com.example.bdmapquanjin_plugin;

import java.util.HashMap;
import java.util.Map;





import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.embedding.engine.plugins.lifecycle.FlutterLifecycleAdapter;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * FlutterBmfmapPlugin
 */
public class BdmapquanjinPlugin implements FlutterPlugin, ActivityAware,
        MethodChannel.MethodCallHandler {
    private static final String TAG = BdmapquanjinPlugin.class.getSimpleName();

    private Lifecycle mLifecycle;


    // This static function is optional and equivalent to onAttachedToEngine. It supports the old
    // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
    // plugin registration via this function while apps migrate to use the new Android APIs
    // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
    //
    // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
    // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
    // depending on the user's project. onAttachedToEngine or registerWith must both be defined
    // in the same class.
    public static void registerWith(Registrar registrar) {
        final Activity activity = registrar.activity();
        if (activity == null) {
            return;
        }
        LifecycleProxy lifecycleProxy;
        if (activity instanceof LifecycleOwner) {
            lifecycleProxy = new LifecycleProxy() {
                @Override
                public Lifecycle getLifecycle() {
                    return ((LifecycleOwner) activity).getLifecycle();
                }
            };
        } else {
            lifecycleProxy = new ActivityLifecycleProxy(activity);
        }

        registrar.platformViewRegistry().registerViewFactory("panorama_view",
                new BdmapquanjinFactory(registrar.messenger(), lifecycleProxy));
    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {

        BinaryMessenger mMessenger = flutterPluginBinding.getBinaryMessenger();

        flutterPluginBinding.getPlatformViewRegistry().registerViewFactory("panorama_view",
                new BdmapquanjinFactory(flutterPluginBinding.getBinaryMessenger(), new LifecycleProxy() {
                    @Override
                    public Lifecycle getLifecycle() {
                        return mLifecycle;
                    }
                }));

        // 单独配置获取版本的消息通道,和地图实例的消息通道无关
        MethodChannel methodChannel = new MethodChannel(mMessenger, "bdmapquanjin_plugin");
        methodChannel.setMethodCallHandler(this);

    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {

    }

    @Override
    public void onAttachedToActivity(ActivityPluginBinding binding) {
        mLifecycle = FlutterLifecycleAdapter.getActivityLifecycle(binding);
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        Log.d(TAG, "onDetachedFromActivityForConfigChanges");
        onDetachedFromActivity();
    }

    @Override
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
        Log.d(TAG, "onReattachedToActivityForConfigChanges");
        onAttachedToActivity(binding);
    }

    @Override
    public void onDetachedFromActivity() {
        Log.d(TAG, "onDetachedFromActivity");
        mLifecycle = null;
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
//        if (call.method.equals(Constants.NATIVE_SDK_VERSION)) {
//            Map<String, String> versionMap = new HashMap<>();
//            versionMap.put("version", VersionInfo.getApiVersion());
//            versionMap.put("platform", "Android");
//            result.success(versionMap);
//        }
    }

    private static class ActivityLifecycleProxy implements LifecycleProxy,
            Application.ActivityLifecycleCallbacks, LifecycleOwner {

        private final LifecycleRegistry mLifecycle = new LifecycleRegistry(this);
        private final int mRegistrarActivityHashCode;

        private ActivityLifecycleProxy(Activity activity) {
            this.mRegistrarActivityHashCode = activity.hashCode();
            activity.getApplication().registerActivityLifecycleCallbacks(this);
        }

        @Override
        public Lifecycle getLifecycle() {
            return mLifecycle;
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            if (activity.hashCode() != mRegistrarActivityHashCode) {
                return;
            }
            mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (activity.hashCode() != mRegistrarActivityHashCode) {
                return;
            }
            mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            if (activity.hashCode() != mRegistrarActivityHashCode) {
                return;
            }
            mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        }

        @Override
        public void onActivityPaused(Activity activity) {
            if (activity.hashCode() != mRegistrarActivityHashCode) {
                return;
            }
            mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (activity.hashCode() != mRegistrarActivityHashCode) {
                return;
            }
            mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (activity.hashCode() != mRegistrarActivityHashCode) {
                return;
            }
            mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        }
    }

}
