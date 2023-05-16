import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'bdmapquanjin_plugin_platform_interface.dart';

//
// /// An implementation of [BdmapquanjinPluginPlatform] that uses method channels.
// class MethodChannelBdmapquanjinPlugin extends BdmapquanjinPluginPlatform {
//   /// The method channel used to interact with the native platform.
//   @visibleForTesting
//   final methodChannel = const MethodChannel('bdmapquanjin_plugin');
//
//   @override
//   Future<String?> getPlatformVersion() async {
//     final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
//     return version;
//   }
//
//   @override
//   void initMap(String mapKey) {
//     _addNativeHandlers();
//     methodChannel.invokeMethod<String>('initMap',mapKey);
//   }
//
//   @override
//   Future<String?> didShowqjMap() async {
//     final version = await methodChannel.invokeMethod<String>('didShowqjMap');
//     return version;
//   }
//   void config(String value) {
//     methodChannel.invokeListMethod("config",value);
//   }
//
//   _addNativeHandlers() {
//     methodChannel.setMethodCallHandler((call) async {
//       switch (call.method) {
//         case "configCallback":
//           print("flutter receive menthod call calue" + call.arguments);
//           break;
//         default:
//       }
//     });
//   }
//
// }
