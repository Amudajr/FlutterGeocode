#import "FlutterGeocodePlugin.h"
#if __has_include(<flutter_geocode/flutter_geocode-Swift.h>)
#import <flutter_geocode/flutter_geocode-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "flutter_geocode-Swift.h"
#endif

@implementation FlutterGeocodePlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterGeocodePlugin registerWithRegistrar:registrar];
}
@end
