# Salary Calculator (Android)

A small Android app that shows the salary calculator inside a WebView. The calculator lives in app/src/main/assets/index.html.

## Get the APK, option A: Android Studio
1. Open this folder in Android Studio and let Gradle sync.
2. Build > Build APK(s). The file is at app/build/outputs/apk/debug/app-debug.apk.

## Get the APK, option B: GitHub (no install needed)
1. Create a new GitHub repository and upload everything in this folder, including the .github folder.
2. Open the Actions tab, run "Build APK" (it also runs on every push).
3. Download the SalaryCalc-apk artifact from the finished run and unzip it.

## Install on your phone
Send the APK to your phone, open it, and allow installs from that source when asked.

## Play Store build (AAB)
The workflow also builds app/build/outputs/bundle/release/app-release.aab. To sign it, add these GitHub repository secrets:
KEYSTORE_B64 (base64 of your upload.jks), KEYSTORE_PASSWORD, KEY_ALIAS, KEY_PASSWORD.
Create the keystore once with:
keytool -genkeypair -v -keystore upload.jks -alias upload -keyalg RSA -keysize 2048 -validity 10000
Keep upload.jks and its passwords safe and never commit them.
