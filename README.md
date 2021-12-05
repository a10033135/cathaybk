## 環境說明
* Android Studio / tools.build:gradle ：4.1.3
* gradlew：gradle-6.5-bin.zip
* target version: 30 , mini version: 24

## MVP 架構
* 單一 Activity 搭載多 Fragment 
* Library: 
  * Net: Retrofit, OkHttp,
  * UI: FragNav,
  * 其他: RxJava, Klog(多功能Log), Glide(圖片切割,讀取網路圖片)
  
## 功能說明
* 完成 - User List 頁面
* 完成 - User Detail 頁面
* 完成 - UserList 分頁功能,限定最多顯示 100 位使用者
 
 ## 備註
 * 由於 api 在 user.site_admin 皆回傳 false，因此暫時改為每三個 user 即顯示 badge
 * 各個 library 版本整理於 dependency.gradle 檔案
