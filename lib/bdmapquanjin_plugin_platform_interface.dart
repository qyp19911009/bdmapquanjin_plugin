import 'dart:ffi';

import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'bdmapquanjin_plugin_method_channel.dart';
//
// abstract class BdmapquanjinPluginPlatform extends PlatformInterface {
//   /// Constructs a BdmapquanjinPluginPlatform.
//   BdmapquanjinPluginPlatform() : super(token: _token);
//
//   static final Object _token = Object();
//
//   static BdmapquanjinPluginPlatform _instance = MethodChannelBdmapquanjinPlugin();
//
//   /// The default instance of [BdmapquanjinPluginPlatform] to use.
//   ///
//   /// Defaults to [MethodChannelBdmapquanjinPlugin].
//   static BdmapquanjinPluginPlatform get instance => _instance;
//
//   /// Platform-specific implementations should set this with their own
//   /// platform-specific class that extends [BdmapquanjinPluginPlatform] when
//   /// they register themselves.
//   static set instance(BdmapquanjinPluginPlatform instance) {
//     PlatformInterface.verifyToken(instance, _token);
//     _instance = instance;
//   }
//
//   Future<String?> getPlatformVersion() {
//     throw UnimplementedError('platformVersion() has not been implemented.');
//   }
//
//   Future<String?> didShowqjMap() {
//     throw UnimplementedError('platformVersion() has not been implemented.');
//   }
//
//
//   void initMap(String mapKey){
//     throw UnimplementedError('initMap() has not been implemented.');
//   }
//
//   void config(String value) {
//     throw UnimplementedError('initMap() has not been implemented.');
//   }
//
//
// }
