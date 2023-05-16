import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:bdmapquanjin_plugin/bdmapquanjin_plugin_method_channel.dart';

void main() {
  MethodChannelBdmapquanjinPlugin platform = MethodChannelBdmapquanjinPlugin();
  const MethodChannel channel = MethodChannel('bdmapquanjin_plugin');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
