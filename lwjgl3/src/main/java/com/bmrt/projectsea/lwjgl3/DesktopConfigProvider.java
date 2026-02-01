package com.bmrt.projectsea.lwjgl3;

import com.bmrt.projectsea.domain.ConfigProvider;

public class DesktopConfigProvider implements ConfigProvider {
    @Override
    public String getProperty(String key) {
        return System.getProperty(key);
    }
}
