package com.bmrt.projectsea.teavm;

import com.badlogic.gdx.ApplicationListener;
import com.github.xpenatan.gdx.backends.teavm.TeaApplication;
import com.github.xpenatan.gdx.backends.teavm.TeaApplicationConfiguration;
import com.bmrt.projectsea.ProjectSeaMain;

/**
 * TeaVM launcher for the web client.
 * This class initializes the libGDX application in the browser.
 */
public class TeaVMLauncher {

    public static void main(String[] args) {
        TeaApplicationConfiguration config = new TeaApplicationConfiguration("canvas");

        // Set canvas size (0 = fill browser window)
        config.width = 1280;
        config.height = 720;

        // Enable antialiasing for smoother graphics
        config.antialiasing = true;

        // Initialize WebSocket support for TeaVM
        TeaVMWebSockets.initiate();

        // Create and start the application
        new TeaApplication(createApplicationListener(), config);
    }

    public static ApplicationListener createApplicationListener() {
        return new ProjectSeaMain();
    }
}
