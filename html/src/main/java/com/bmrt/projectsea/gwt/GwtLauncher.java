package com.bmrt.projectsea.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.bmrt.projectsea.ProjectSeaMain;
import com.github.czyzby.websocket.GwtWebSockets;

/**
 * Launches the GWT application.
 */
public class GwtLauncher extends GwtApplication {
    @Override
    public GwtApplicationConfiguration getConfig() {
        // Resizable application, uses available space in browser with no padding:

        //GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(true);
        //cfg.padVertical = 0;
        //cfg.padHorizontal = 0;
        //return cfg;

        // If you want a fixed size application, comment out the above resizable section,
        // and uncomment below:
        GwtWebSockets.initiate();
        return new GwtApplicationConfiguration(1280, 720);
    }

    @Override
    public ApplicationListener createApplicationListener() {
        return new ProjectSeaMain();
    }
}
