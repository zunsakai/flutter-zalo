import 'package:flutter/material.dart';
import 'package:flutter_zalo/flutter_zalo.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter Zalo',
      home: MyHomePage(title: 'Flutter Zalo'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final FlutterZalo flutterZalo = FlutterZalo();

  @override
  void initState() {
    super.initState();
    flutterZalo.init();
  }

  showMessage(String msg) {
    ScaffoldMessenger.of(context).clearSnackBars();
    ScaffoldMessenger.of(context).showSnackBar(SnackBar(
      content: Text(msg),
    ));
  }

  void logIn() async {
    bool? result = await flutterZalo.logIn();
    showMessage(result! ? "Logged in" : "Failed to log in");
  }

  void isAccessTokenValid() async {
    bool? isValid = await flutterZalo.isAccessTokenValid();
    showMessage("Is access token valid:\n$isValid");
  }

  void getAccessToken() async {
    String? accessToken = await flutterZalo.getAccessToken();
    showMessage("Access Token:\n$accessToken");
  }

  void refreshAccessToken() async {
    bool? isRefreshed = await flutterZalo.refreshAccessToken();
    showMessage("Refreshed access token:\n$isRefreshed");
  }

  void getProfile() async {
    Map<String, dynamic>? profile = await flutterZalo.getProfile();
    showMessage("Profile:\n$profile");
  }

  void logOut() async {
    bool? isLoggedOut = await flutterZalo.logOut();
    showMessage(isLoggedOut! ? "Logged out" : "Failed to log out");
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title, style: const TextStyle(color: Colors.white)),
        backgroundColor: Colors.blue,
      ),
      body: SingleChildScrollView(
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              const VerticalDivider(),
              TextButton(
                onPressed: logIn,
                child: const Text(
                  "logIn",
                  style: TextStyle(color: Colors.red),
                ),
                // color: Theme.of(context).accentColor,
              ),
              const VerticalDivider(),
              TextButton(
                onPressed: isAccessTokenValid,
                child: const Text(
                  "isAccessTokenValid",
                  style: TextStyle(color: Colors.red),
                ),
                // color: Theme.of(context).accentColor,
              ),
              const VerticalDivider(),
              TextButton(
                onPressed: getAccessToken,
                child: const Text(
                  "getAccessToken",
                  style: TextStyle(color: Colors.red),
                ),
                // color: Theme.of(context).accentColor,
              ),
              const VerticalDivider(),
              TextButton(
                onPressed: refreshAccessToken,
                child: const Text(
                  "refreshAccessToken",
                  style: TextStyle(color: Colors.red),
                ),
                // color: Theme.of(context).accentColor,
              ),
              const VerticalDivider(),
              TextButton(
                onPressed: getProfile,
                child: const Text(
                  "getProfile",
                  style: TextStyle(color: Colors.red),
                ),
                // color: Theme.of(context).accentColor,
              ),
              const VerticalDivider(),
              TextButton(
                onPressed: logOut,
                child: const Text(
                  "logOut",
                  style: TextStyle(color: Colors.red),
                ),
                // color: Theme.of(context).accentColor,
              ),
            ],
          ),
        ),
      ),
    );
  }
}
