# 使用官方的Android Gradle镜像
FROM gradle:8.13-jdk17

# 设置环境变量
ENV ANDROID_HOME=/opt/android-sdk
ENV PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$ANDROID_HOME/cmdline-tools/latest/bin

# 安装必要的依赖包
USER root
RUN apt-get update && apt-get install -y --no-install-recommends \
    wget \
    unzip \
    && rm -rf /var/lib/apt/lists/*

# 下载并安装Android SDK Command Line Tools
RUN mkdir -p $ANDROID_HOME/cmdline-tools \
    && wget -O cmdline-tools.zip https://dl.google.com/android/repository/commandlinetools-linux-8512546_latest.zip \
    && unzip cmdline-tools.zip -d $ANDROID_HOME/cmdline-tools \
    && mv $ANDROID_HOME/cmdline-tools/cmdline-tools $ANDROID_HOME/cmdline-tools/latest \
    && rm cmdline-tools.zip

# 接受Android SDK许可证
RUN yes | sdkmanager --licenses

# 安装Android SDK平台和构建工具
RUN sdkmanager "platforms;android-33" "build-tools;33.0.2" "platform-tools"

# 设置工作目录
WORKDIR /app

# 复制项目文件
COPY . .

# 构建命令
CMD ["gradle", "assembleDebug", "--stacktrace"]