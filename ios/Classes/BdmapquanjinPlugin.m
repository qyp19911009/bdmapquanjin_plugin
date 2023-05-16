#import "BdmapquanjinPlugin.h"
#import "BdMapPanoramaPluginViewFactory.h"
static  FlutterMethodChannel* mChannel;

@implementation BdmapquanjinPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
     BdMapPanoramaPluginViewFactory *testViewFactory = [[BdMapPanoramaPluginViewFactory alloc] initWithMessenger:registrar.messenger];
     //这里填写的id 一定要和dart里面的viewType 这个参数传的id一致
     [registrar registerViewFactory:testViewFactory withId:@"panorama_view"];
  }


@end
