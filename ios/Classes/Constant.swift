import Foundation

class Constant {
    public static let EXT_INFO = [
        "appVersion": "1.0.1",
    ]
}

enum UserDefaultsKeys: String, CaseIterable {
    case refreshToken = "refreshToken"
    case accessToken = "accessToken"
    case expriedTime = "expriedTime"
}
