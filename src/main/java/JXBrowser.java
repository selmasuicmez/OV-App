import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.dom.InputElement;
import com.teamdev.jxbrowser.dom.Node;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.RenderingMode;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import com.teamdev.jxbrowser.frame.Frame;

public class JXBrowser {

    private Engine engine;
    private String htmlFile;
    private Browser browser;
    private BrowserView view;

    JXBrowser() {
        this.engine = Engine.newInstance(
                EngineOptions.newBuilder(RenderingMode.HARDWARE_ACCELERATED)
                        .licenseKey("")
                        .build());

        this.browser = engine.newBrowser();
        this.view = BrowserView.newInstance(browser);
        this.htmlFile = "/home/yawgmoth/Dropbox/HU-ADSD/S1/Periode1/OV-app/src/index.html";
    }

    public BrowserView getBrowserView () {
        return this.view;
    }

    public void closeBrowser() {
        this.browser.close();
    }

    public void loadUrl() {
        browser.navigation().loadUrl(htmlFile);
        System.out.println("HTML file loaded");
    }

    public void reloadUrl() {
        this.browser.navigation().reload();
    }


    public void drawMap(String searchLocationA, String searchLocationB) {

        System.out.println(searchLocationA +" from/to "+searchLocationB);

        this.browser.mainFrame().flatMap(Frame::document).ifPresent(document -> {
            String baseUri = document.baseUri();

            document.findElementById("departure").ifPresent(element -> ((InputElement) element).value(searchLocationA));
            document.findElementById("destination").ifPresent(element -> ((InputElement) element).value(searchLocationB));
            document.findElementById("submit").ifPresent(Node::click);
        });
    }
}