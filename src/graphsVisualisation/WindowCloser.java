package graphsVisualisation;

import java.awt.SystemTray;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;

/**
 * Close the window correctly (system tray menu included)
 */
public class WindowCloser implements WindowListener, Serializable {
    private VisualisationJFrame main_frame;

    /**
     * Main Constructor
     * @param f: The Frame that has to be closed
     */
    public WindowCloser(VisualisationJFrame f){
        this.main_frame = f;
    }

    /**
     * Not used
     */
    @Override
    public void windowOpened(WindowEvent e) {

    }
    
    /**
     * Main method to quit the interface
     */
    @Override
    public void windowClosing(WindowEvent e) {
        this.quit();
    }

    /**
     * Not used
     */
    @Override
    public void windowClosed(WindowEvent e) {

    }

    /**
     * Not used
     */
    @Override
    public void windowIconified(WindowEvent e) {

    }

    /**
     * Not used
     */
    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    /**
     * Not used
     */
    @Override
    public void windowActivated(WindowEvent e) {

    }

    /**
     * Not used
     */
    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    /**
     * To quit completely the main frame, remove the icon tray and exiting the program
     */
    protected void quit() {
        if(SystemTray.isSupported()) {
        	main_frame.getSystemTray().remove(main_frame.getMainTrayIcon());
        }
 
        main_frame.getOptionsManager().saveOptions();
        main_frame.dispose();
        System.exit(0);
    }
}
