import 'dart:io';

import 'package:bdmapquanjin_plugin/bdmapquanjin_plugin.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';


void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  ///定义一个测试类的属性 用来调用原生方法 和原生交互
  var testPluginDemo;
  @override
  void initState() {
    super.initState();
  }
  @override
  Widget build(BuildContext context) {
    ///初始化 测试视图的类
    TestView testView = new TestView(
      onCreated: onTestViewCreated,
    );

    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('flutter原生调用'),
        ),
        body: Column(
          children: <Widget>[
            Container(
              height: 400,
              width: 400,
              child: testView,///使用原生视图
            ),
            FloatingActionButton(
              child: Icon(Icons.add),
              onPressed: onNativeMethon,
            )
          ],
        ),
      ),
    );
  }
  void onTestViewCreated(TestPluginDemo){
    this.testPluginDemo = TestPluginDemo;
  }
  void onNativeMethon(){
    this.testPluginDemo.changeNativeTitle('调用原生成功了');
  }
}


///我是使用的 StatefulWidget 使用StatelessWidget 也是一样
class TestView extends StatefulWidget {
  ///根据自己的需求创建初始化参数
  TestViewCreatedCallback? onCreated; ///是上面创建的回调
  String? titleStr;

  TestView({
    this.onCreated,
    this.titleStr,
  });

  @override
  _TestViewState createState() => _TestViewState();
}

class _TestViewState extends State<TestView> {
  @override
  Widget build(BuildContext context) {
    return Container(
      child: _loadNativeView(),
    );
  }
  ///加载原生视图
  Widget _loadNativeView(){
    ///根据不同的平台显示相应的视图
    if(Platform.isAndroid){ ///加载安卓原生视图
      return AndroidView(
        viewType: 'panorama_view',///视图标识符 要和原生 保持一致 要不然加载不到视图
        onPlatformViewCreated:onPlatformViewCreated,///原生视图创建成功的回调
        creationParams: <String, dynamic>{ ///给原生传递初始化参数 就是上面定义的初始化参数
          'ak': 'E72Sgtzx22KV9HDh0PxkDOK1Vl19Lxkc',
          'titleStr':widget.titleStr ?? "",
          'lon': "114.03989963925856",
          'lat': "22.6485583224421"
        },
        /// 用来编码 creationParams 的形式，可选 [StandardMessageCodec], [JSONMessageCodec], [StringCodec], or [BinaryCodec]
        /// 如果存在 creationParams，则该值不能为null
        creationParamsCodec: const StandardMessageCodec(),
      );
    }else if(Platform.isIOS){///加载iOS原生视图
      return UiKitView(
        viewType: 'panorama_view',///视图标识符 要和原生 保持一致 要不然加载不到视图
        onPlatformViewCreated:onPlatformViewCreated,///原生视图创建成功的回调
        creationParams: <String, dynamic>{ ///给原生传递初始化参数 就是上面定义的初始化参数
          'ak': 'E72Sgtzx22KV9HDh0PxkDOK1Vl19Lxkc',
          'titleStr':widget.titleStr ?? "",
          'lon': "114.03989963925856",
          'lat': "22.6485583224421"
        },// @""
        /// 用来编码 creationParams 的形式，可选 [StandardMessageCodec], [JSONMessageCodec], [StringCodec], or [BinaryCodec]
        /// 如果存在 creationParams，则该值不能为null
        creationParamsCodec: const StandardMessageCodec(),
      );
    }else{
      return Text('这个平台不支持');
    }
  }
  ///这个基本上是固定写法
  Future<void> onPlatformViewCreated(id) async {
    if (widget.onCreated == null) {
      return;
    }
    widget.onCreated!(BdMapQuanJingPlugin.init(id));
  }
}