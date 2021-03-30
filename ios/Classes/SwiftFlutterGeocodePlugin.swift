import Flutter
import UIKit
import GoogleMaps

public class SwiftFlutterGeocodePlugin: NSObject, FlutterPlugin {
    public static func register(with registrar: FlutterPluginRegistrar) {
        let channel = FlutterMethodChannel(name: "flutter_geocode", binaryMessenger: registrar.messenger())
        let instance = SwiftFlutterGeocodePlugin()
        registrar.addMethodCallDelegate(instance, channel: channel)
    }

    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        if (call.method == "apikey") {
            if let args = call.arguments as? String  {
                GMSServices.provideAPIKey(args)
                result(args)
            } else {
                result(FlutterError.init(code: "bad args", message: nil, details: nil))
            }
        } else if (call.method == "latlng") {
            if let args = call.arguments as? Dictionary<String, Any>,
               let lat = args["lat"] as? Double ,let lng = args["lng"] as? Double  {
                var str = "";
                let geocoder = GMSGeocoder()
                geocoder.reverseGeocodeCoordinate(CLLocationCoordinate2D(latitude:lat,longitude: lng)) { response, error in
                    guard let address = response?.firstResult(), let lines = address.lines else {
                        return
                    }
                    str = lines.joined(separator: "\n");
                    result(str)
                }
            } else {
                result(FlutterError.init(code: "bad args", message: nil, details: nil))
            }
        }
    }
}
