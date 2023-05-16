import 'dart:async';

import 'package:flutter/services.dart';
typedef void TestViewCreatedCallback(BdMapQuanJingPlugin controller);
class BdMapQuanJingPlugin {
  late MethodChannel _channel;
  BdMapQuanJingPlugin.init(int id) {
    _channel = MethodChannel("panoramaview_channel");
    _channel.setMethodCallHandler(platformCallHandler);
  }

  /*
  ///Flutter 调用原生
  ///这里我传了一个 字符串 当然也可以传Map
  Future<Future<List?>> changeNativeTitle(String str) async{
    return _channel.invokeListMethod('test_channel',str);
  }
  * */

  ///实现监听原生方法回调
  Future<dynamic> platformCallHandler(MethodCall call) async {
    print(call.method);
    switch (call.method) {
      case "click_aciton":
        print('收到原生回调 ---- $call.arguments');
        return ;
    }
  }
}