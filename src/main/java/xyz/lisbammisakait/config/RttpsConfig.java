package xyz.lisbammisakait.config;


import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class RttpsConfig {
    private static final String CONFIG_FILE_NAME = "RtTPS.properties";
    private static final Properties properties = new Properties();

    static {
        // 获取配置文件路径
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), CONFIG_FILE_NAME);

        try {
            if (!configFile.exists()) {
                // 如果配置文件不存在，创建新的配置文件并设置默认值
                createDefaultConfig(configFile);
            }
            // 加载配置文件
            loadConfig(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDefaultConfig(File configFile) throws IOException {
        // 设置默认配置
        properties.setProperty("isBindingMap", "false");

        // 保存配置到文件
        try (FileOutputStream fos = new FileOutputStream(configFile)) {
            properties.store(fos, "Whether to use it with a special map?");
        }
    }

    private static void loadConfig(File configFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(configFile)) {
            properties.load(fis);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}