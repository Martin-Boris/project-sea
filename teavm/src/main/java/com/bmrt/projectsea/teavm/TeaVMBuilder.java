package com.bmrt.projectsea.teavm;

import com.github.xpenatan.gdx.backends.teavm.config.AssetFileHandle;
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuildConfiguration;
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuilder;
import java.io.File;

/**
 * TeaVM build configuration for the web client.
 * Run this class to compile the libGDX application to JavaScript.
 */
public class TeaVMBuilder {

    public static void main(String[] args) {
        TeaBuildConfiguration config = new TeaBuildConfiguration();

        // Main application class - use public field
        config.mainApplicationClass = TeaVMLauncher.class.getName();

        // Asset path - use AssetFileHandle
        config.assetsPath.add(new AssetFileHandle("../assets"));

        // Output directory - use public field
        config.webappPath = new File("build/dist/webapp").getAbsolutePath();

        // Check if running in dev mode
        boolean runServer = false;
        for (String arg : args) {
            if ("--run".equals(arg)) {
                runServer = true;
                break;
            }
        }

        // Build using static method
        TeaBuilder.build(config);

        if (runServer) {
            // Start local server for development
            System.out.println("Build complete. Run a local HTTP server in build/dist/webapp to test.");
        }
    }
}
