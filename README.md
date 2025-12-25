# phone_manager
设备借/还管理系统

## 项目介绍
手机借还管理系统是一个基于Android平台的应用程序，用于管理手机设备的借出和归还流程。通过扫描二维码快速识别设备，并记录借还信息，实现高效的设备管理。

## 技术栈
- **开发语言**：Java
- **架构模式**：MVC (Model-View-Controller)
- **数据库**：SQLite
- **QR码扫描**：ZXing库
- **构建工具**：Gradle
- **容器化**：Docker

## 环境要求
- Docker（推荐使用最新版本）
- 支持Android 5.0 (API 21)及以上版本的设备

## 功能特性
- ✅ 扫码识别手机设备
- ✅ 记录手机借出信息（借用人、出借人、借出时间）
- ✅ 记录手机归还信息（归还人、归还时间）
- ✅ 查看设备借还历史
- ✅ 本地SQLite数据库存储

## 构建步骤



### 使用Android Studio构建（备选）
1. 安装Android Studio和Android SDK
2. 导入项目到Android Studio
3. 同步Gradle依赖
4. 运行`assembleDebug`任务
5. 在`app/build/outputs/apk/debug/`目录下获取APK文件

## 使用说明

### 安装APK
1. 将构建好的`app-debug.apk`文件传输到Android设备
2. 在设备上打开APK文件，按照提示完成安装
3. 首次运行需要授予相机权限

### 操作流程

#### 借出手机
1. 点击主界面的"借出手机"按钮
2. 使用相机扫描手机设备上的二维码
3. 在借出页面输入借用人和出借人信息
4. 点击"确认借出"完成操作

#### 归还手机
1. 点击主界面的"归还手机"按钮
2. 使用相机扫描手机设备上的二维码
3. 在归还页面输入归还人信息
4. 点击"确认归还"完成操作

## 项目结构

```
phone-manager/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/org/example/phonemanager/
│   │   │   │   ├── model/          # 数据模型层
│   │   │   │   ├── view/           # 视图层（Activity）
│   │   │   │   └── util/           # 工具类
│   │   │   ├── res/               # 资源文件
│   │   │   │   ├── layout/        # 布局文件
│   │   │   │   ├── values/        # 字符串、颜色等
│   │   │   │   └── drawable/      # 图片资源
│   │   │   └── AndroidManifest.xml # 应用配置
│   │   └── build.gradle           # 应用级构建配置
├── build.gradle                   # 项目级构建配置
├── settings.gradle                # 项目设置
├── Dockerfile                     # Docker构建配置
├── docker-compose.yml             # Docker Compose配置
└── README.md                      # 项目说明
```

## 注意事项
1. 确保Android设备已开启"允许安装未知来源应用"的选项
2. 首次运行应用需要授予相机权限
3. 二维码内容应为手机设备的唯一ID
4. 构建过程可能需要较长时间，取决于网络速度

## 故障排除

### 构建失败
- 确保Docker服务正在运行
- 检查网络连接是否正常
- 尝试清理Docker缓存：
  ```bash
  docker system prune
  ```

### 扫描不工作
- 确保已授予相机权限
- 确保二维码清晰可见
- 尝试在光线充足的环境下扫描

## 许可证
本项目采用MIT许可证。
>>>>>>> 4d1ab66 (Initial commit)

### 打包：方法1
执行命令 .\gradlew.bat assembleDebug --stacktrace
直接执行时：Gradle 完全在本地 Windows 环境中运行，使用你本地安装的 JDK
详细说明 1. .\gradlew.bat Gradle Wrapper - Gradle 的封装脚本 自动下载并使用项目指定的 Gradle 版本（7.6.4） 确保团队成员使用一致的 Gradle 版本 .\ 表示当前目录（PowerShell 语法）  2. assembleDebug assemble - 构建任务前缀 Debug - 构建变体（Build Variant） 执行的操作： 编译 Java/Kotlin 代码 合并资源文件 执行代码混淆（如果配置了） 生成 app-debug.apk    3. --stacktrace 当构建失败时，显示完整的错误堆栈跟踪 帮助定位问题发生在哪个文件的哪一行 正常构建时不会显示额外信息
取包路径app/build/outputs/apk/debug/app-debug.apk

### 打包：方法2
dcoker打包
# 进入 Docker 容器后执行
docker exec -it android-container /bin/bash
./gradlew.bat assembleDebug
使用 Docker 作为构建环境 如果你的 Docker 镜像包含了： Android SDK JDK 11 Gradle  那么构建过程是在 Docker 容器内完成的。