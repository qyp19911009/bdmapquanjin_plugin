
typedef struct loc_panor {
    double latitude;
    double longitude;
}Loca;
const double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

#import "BdPanoramaPluginView.h"
#import <BaiduPanoSDK/BaiduPanoramaView.h>
#import <BaiduPanoSDK/BaiduPanoDataFetcher.h>

@interface BdPanoramaPluginView ()<BaiduPanoramaViewDelegate>
/** channel*/
@property (nonatomic, strong) FlutterMethodChannel  *channel;
@property (nonatomic, strong) UIButton *button;
@property (nonatomic, strong) BaiduPanoramaView *panoramaView;
@property (nonatomic, strong) UILabel *locationInfo;
@property (nonatomic, assign) CLLocationCoordinate2D location;
@end

@implementation BdPanoramaPluginView
{
    CGRect _frame;
    int64_t _viewId;
    id _args;
}

- (id)initWithFrame:(CGRect)frame
             viewId:(int64_t)viewId
               args:(id)args
           messager:(NSObject<FlutterBinaryMessenger>*)messenger{
    if (self = [super init]) {
        _frame = frame;
        _viewId = viewId;
        _args = args;
        ///建立通信通道 用来 监听Flutter 的调用和 调用Fluttter 方法 这里的名称要和Flutter 端("panoramaview_channel"插件dart和原生)保持一致
        _channel = [FlutterMethodChannel methodChannelWithName:@"panoramaview_channel" binaryMessenger:messenger];
        __weak __typeof__(self) weakSelf = self;
        [_channel setMethodCallHandler:^(FlutterMethodCall * _Nonnull call, FlutterResult  _Nonnull result) {
            [weakSelf onMethodCall:call result:result];
        }];
    }
    return self;
}

/// 原生的UIView
- (UIView *)view{
    NSDictionary *dic = (NSDictionary *)_args;
    UIView *nativeView = [[UIView alloc] initWithFrame:_frame];
    nativeView.backgroundColor = [UIColor whiteColor];
    NSString *lat = (NSString *)dic[@"lat"];
    NSString *lon = (NSString *)dic[@"lon"];
    NSString *AK = (NSString *)dic[@"ak"];
    Loca loc = [self bd_encrypt:lat.doubleValue lon:lon.doubleValue];
    
    BaiduLocationPanoData *panoData = [BaiduPanoDataFetcher requestPanoramaInfoWithLon:loc.longitude Lat:loc.latitude];
    
    if(!panoData.hasPanorama){
        UILabel *tip = [[UILabel alloc]initWithFrame:CGRectMake(UIScreen.mainScreen.bounds.size.width/2-80, UIScreen.mainScreen.bounds.size.height/2-40, 160, 40)];
        tip.text = @"当前位置暂无街景";
        tip.textColor = [[UIColor blackColor] colorWithAlphaComponent:0.5];
        tip.font = [UIFont systemFontOfSize:16];
        tip.textAlignment = NSTextAlignmentCenter;
        [nativeView addSubview:tip];
        return nativeView;
        
    }
    
    self.panoramaView = [[BaiduPanoramaView alloc] initWithFrame:CGRectMake(0, 0, UIScreen.mainScreen.bounds.size.width , UIScreen.mainScreen.bounds.size.height) key:AK];
    self.panoramaView.delegate = self;
    [nativeView addSubview:self.panoramaView];
    [self.panoramaView setPanoramaWithLon:loc.longitude lat:loc.latitude];
    return nativeView;
}

- (Loca )bd_encrypt:(double) gg_lat lon:(double) gg_lon
{
    double x = gg_lon, y = gg_lat;
    double z = sqrt(x * x + y * y) + 0.00002 * sin(y * x_pi);
    double theta = atan2(y, x) + 0.000003 * cos(x * x_pi);
    Loca loc;
    loc.longitude = z * cos(theta) + 0.0065;
    loc.latitude = z * sin(theta) + 0.006;
    return loc;
}

#pragma mark -- Flutter 监听
-(void)onMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result{
    NSLog(@"Flutter 监听 result%@",call.arguments);
    //监听Fluter
    /*
    if ([[call method] isEqualToString:@"test_channel"]) {
        [self.button setTitle:call.arguments forState:UIControlStateNormal];
    }
    */
}
/*
//调用Flutter
- (void)flutterMethod{
    [self.channel invokeMethod:@"click_aciton" arguments:@"传递参数"];
}
*/

@end
