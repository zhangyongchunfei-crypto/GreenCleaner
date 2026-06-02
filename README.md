# 绿源一键清除

一个功能强大的 Android 数据清除应用，帮助用户彻底清除设备上的所有痕迹数据。

## 功能特性

- ✅ 应用存储和数据清除
- ✅ 浏览器历史记录、cookies、缓存清除
- ✅ 搜索历史清除
- ✅ 最近使用的文件清除
- ✅ 剪贴板历史清除
- ✅ 临时文件清除
- ✅ 调用记录、短信、照片元数据清除
- ✅ 设备日志清除
- ✅ 一键快速清除
- ✅ 定时自动清除
- ✅ 清除前数据预览和大小统计
- ✅ 清除历史记录

## 技术栈

- **语言**：Java
- **UI框架**：XML 布局
- **最低 API**：Android 6.0 (API 23)
- **权限模式**：标准权限（无需 Root）

## 快速开始

### 前置要求
- Android Studio
- JDK 11+
- Android SDK 23+

### 安装

1. 克隆仓库
```bash
git clone https://github.com/zhangyongchunfei-crypto/GreenCleaner.git
cd GreenCleaner
```

2. 在 Android Studio 中打开项目

3. 编译和运行
```bash
./gradlew build
./gradlew installDebug
```

## 权限申明

应用需要以下权限：
- `android.permission.READ_EXTERNAL_STORAGE` - 读取外部存储
- `android.permission.WRITE_EXTERNAL_STORAGE` - 写入外部存储
- `android.permission.ACCESS_NETWORK_STATE` - 访问网络状态
- `android.permission.CLEAR_APP_CACHE` - 清除应用缓存
- `android.permission.READ_LOGS` - 读取设备日志
- `android.permission.READ_CALL_LOG` - 读取通话记录
- `android.permission.READ_SMS` - 读取短信
- `android.permission.READ_CLIPBOARD` - 读取剪贴板

## 使用指南

1. 打开应用
2. 选择要清除的数据类型
3. 点击"扫描"查看要清除的数据大小
4. 点击"一键清除"开始清除
5. 清除完成后查看清除历史记录

## 项目结构

```
app/
├── src/main/
│   ├── java/com/green/cleaner/
│   │   ├── MainActivity.java
│   │   ├── services/
│   │   │   ├── DataCleanerService.java
│   │   │   ├── AppCacheCleanerService.java
│   │   │   ├── BrowserCleanerService.java
│   │   │   ├── SearchHistoryCleanerService.java
│   │   │   ├── RecentFilesCleanerService.java
│   │   │   ├── ClipboardCleanerService.java
│   │   │   ├── TempFileCleanerService.java
│   │   │   ├── CallLogCleanerService.java
│   │   │   ├── SmsCleanerService.java
│   │   │   └── LogsCleanerService.java
│   │   ├── utils/
│   │   │   ├── CleanerUtils.java
│   │   │   ├── PermissionUtils.java
│   │   │   ├── StorageUtils.java
│   │   │   ├── FileUtils.java
│   │   │   └── DatabaseUtils.java
│   │   ├── models/
│   │   │   ├── CleanData.java
│   │   │   └── CleanHistory.java
│   │   └── receivers/
│   │       └── BootReceiver.java
│   ├── res/
│   │   ├── layout/
│   │   ├── values/
│   │   ├── drawable/
│   │   └── menu/
│   └── AndroidManifest.xml
└── build.gradle
```

## 许可证

MIT License

## 贡献

欢迎提交 Issue 和 Pull Request！
