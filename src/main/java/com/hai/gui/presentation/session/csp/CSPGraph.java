package com.hai.gui.presentation.session.csp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.Parent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import netscape.javascript.JSObject;

/**
 * Created by mrsfy on 13-Apr-17.
 */
public class CSPGraph extends Parent {


    private JSObject doc;
    private WebView webView;
    private WebEngine webEngine;
    private boolean ready;

    public CSPGraph() {
        initCSP();
        initCommunication();
        Screen screen = Screen.getPrimary();
        webView.setPrefSize(screen.getBounds().getWidth(),screen.getBounds().getHeight() - 180);
        getChildren().add(webView); // Will be change as JavaFx Elements change
    }


    private void initCSP() {
        ready = false;

        //####################### Initialize Web View #######################
        webView = new WebView();
        webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("/www/csp/index.html").toExternalForm());
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>()
        {

            public void changed(final ObservableValue<? extends Worker.State> observableValue,
                                final Worker.State oldState,
                                final Worker.State newState)
            {
                if (newState == Worker.State.SUCCEEDED)
                {
                    ready = true;
                }
            }
        });
        //####################################################################

    }



    /**
     * Initialize Communication
     * Initialize communication between Java and Javascript
     * Define and send reference of this class
     * */
    private void initCommunication() {
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>()
        {
            public void changed(final ObservableValue<? extends Worker.State> observableValue,
                                final Worker.State oldState,
                                final Worker.State newState)
            {
                if (newState == Worker.State.SUCCEEDED)
                {
                    doc = (JSObject) webEngine.executeScript("window");
                    doc.setMember("app", CSPGraph.this); // Set app variable into Javascript code which is referring to this class
                }
            }
        });
    }

    /**
     * Invoke Javascript
     * Invoke javascript code in the our map view. Eval javascript.
     * @param jsCode javascript code
     * */
    public void invokeJS(final String jsCode) {
        if(ready) {
            // If initialize succeeded
            doc.eval(jsCode); // Add and run the script
        }
        else {
            // Check again, If everything is ok, eval the script
            webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>()
            {
                public void changed(final ObservableValue<? extends Worker.State> observableValue,
                                    final Worker.State oldState,
                                    final Worker.State newState)
                {
                    if (newState == Worker.State.SUCCEEDED)
                    {
                        doc.eval(jsCode); // Add and run the script
                    }
                }
            });
        }
    }
}
