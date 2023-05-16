

#import <Foundation/Foundation.h>
#include <Flutter/Flutter.h>


@interface BdPanoramaPluginView : NSObject<FlutterPlatformView>
- (id)initWithFrame:(CGRect)frame
             viewId:(int64_t)viewId
               args:(id)args
           messager:(NSObject<FlutterBinaryMessenger>*)messenger;
@end
