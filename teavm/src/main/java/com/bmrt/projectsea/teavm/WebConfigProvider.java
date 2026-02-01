package com.bmrt.projectsea.teavm;

import com.bmrt.projectsea.domain.ConfigProvider;
import org.teavm.jso.JSBody;

public class WebConfigProvider implements ConfigProvider {
    @JSBody(params = {"key"}, script = "return window.APP_CONFIG[key] || null;")
    private static native String getJsProperty(String key);

    @Override
    public String getProperty(String key) {
        return getJsProperty(key);
    }
}
