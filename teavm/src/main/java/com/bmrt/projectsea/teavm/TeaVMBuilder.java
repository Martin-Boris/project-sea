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

        // Main application class
        config.setApplicationClass(TeaVMLauncher.class);

        // Asset path - use AssetFileHandle
        config.assetsPath.add(new AssetFileHandle("../assets"));

        // Output directory
        config.setWebAppPath(new File("build/dist/webapp"));

        // Add reflection classes if needed (e.g., for tiled maps)
        config.additionalReflectionClasses.add("com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile");

        // Build configuration options
        config.setObfuscate(true);

        // Check if running in dev mode
        boolean runServer = false;
        for (String arg : args) {
            if ("--run".equals(arg)) {
                runServer = true;
                break;
            }
        }

        if (runServer) {
            // Run development server
            new TeaBuilder(config).build().run();
        } else {
            // Build for production
            new TeaBuilder(config).build();
        }
    }
}
