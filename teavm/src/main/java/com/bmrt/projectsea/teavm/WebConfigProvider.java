package com.bmrt.projectsea.teavm;

import com.bmrt.projectsea.domain.ConfigProvider;
import org.teavm.jso.JSBody;
import org.teavm.platform.Platform;

public class WebConfigProvider implements ConfigProvider {
    @Override
    public String getProperty(String key) {
        return getJsProperty(key);
       // return System.getProperty(key);
    }

    @JSBody(params = {"key"}, script = "return window.APP_CONFIG[key] || null;")
    private static native String getJsProperty(String key);
}
