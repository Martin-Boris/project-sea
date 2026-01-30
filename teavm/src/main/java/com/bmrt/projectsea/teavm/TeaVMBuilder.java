package com.bmrt.projectsea.teavm;

import com.github.xpenatan.gdx.backends.teavm.config.TeaBuildConfiguration;
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuilder;
import com.github.xpenatan.gdx.backends.teavm.config.plugins.TeaReflectionSupplier;
import java.io.File;

/**
 * TeaVM build configuration for the web client.
 * Run this class to compile the libGDX application to JavaScript.
 */
public class TeaVMBuilder {

    public static void main(String[] args) {
        TeaBuildConfiguration config = new TeaBuildConfiguration();

        // Main application class
        config.mainClass = TeaVMLauncher.class.getName();

        // Asset path
        config.assetsPath.add(new File("../assets"));

        // Output directory
        config.webappPath = new File("build/dist/webapp").getAbsolutePath();

        // Add reflection classes if needed (e.g., for tiled maps)
        TeaReflectionSupplier reflectionSupplier = config.getReflectionSupplier();
        reflectionSupplier.addReflectionClass("com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile");

        // Build configuration options
        config.obfuscate = true;
        config.logClasses = false;

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
            String[] serverArgs = {String.valueOf(TeaBuilder.getDefaultServerPort())};
            TeaBuilder.serve(config, serverArgs);
        } else {
            // Build for production
            TeaBuilder.build(config);
        }
    }
}
