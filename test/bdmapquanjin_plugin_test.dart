import 'package:flutter_test/flutter_test.dart';
import 'package:bdmapquanjin_plugin/bdmapquanjin_plugin.dart';
import 'package:bdmapquanjin_plugin/bdmapquanjin_plugin_platform_interface.dart';
import 'package:bdmapquanjin_plugin/bdmapquanjin_plugin_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockBdmapquanjinPluginPlatform 
    with MockPlatformInterfaceMixin
    implements BdmapquanjinPluginPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final BdmapquanjinPluginPlatform initialPlatform = BdmapquanjinPluginPlatform.instance;

  test('$MethodChannelBdmapquanjinPlugin is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelBdmapquanjinPlugin>());
  });

  test('getPlatformVersion', () async {
    BdmapquanjinPlugin bdmapquanjinPlugin = BdmapquanjinPlugin();
    MockBdmapquanjinPluginPlatform fakePlatform = MockBdmapquanjinPluginPlatform();
    BdmapquanjinPluginPlatform.instance = fakePlatform;
  
    expect(await bdmapquanjinPlugin.getPlatformVersion(), '42');
  });
}
