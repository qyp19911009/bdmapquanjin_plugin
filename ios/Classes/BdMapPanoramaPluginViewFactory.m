
#import "BdMapPanoramaPluginViewFactory.h"
#import "BdPanoramaPluginView.h"
@interface BdMapPanoramaPluginViewFactory ()

@property(nonatomic)NSObject<FlutterBinaryMessenger>* messenger;

@end

@implementation BdMapPanoramaPluginViewFactory

- (instancetype)initWithMessenger:(NSObject<FlutterBinaryMessenger>*)messenger {
    self = [super init];
    if (self) {
        self.messenger = messenger;
    }
    return self;
}

#pragma mark -- 实现FlutterPlatformViewFactory 的代理方法
- (NSObject<FlutterMessageCodec>*)createArgsCodec {
    return [FlutterStandardMessageCodec sharedInstance];
}

/// FlutterPlatformViewFactory 代理方法 返回过去一个类来布局 原生视图
/// @param frame frame
/// @param viewId view的id
/// @param args 初始化的参数
- (NSObject<FlutterPlatformView> *)createWithFrame:(CGRect)frame viewIdentifier:(int64_t)viewId arguments:(id)args{
    BdPanoramaPluginView *pluginView = [[BdPanoramaPluginView alloc] initWithFrame:frame viewId:viewId args:args messager:self.messenger];
    return pluginView;
}

@end
