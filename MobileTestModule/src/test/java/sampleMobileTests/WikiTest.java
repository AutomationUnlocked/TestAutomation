package sampleMobileTests;

import org.testng.annotations.Test;
import pages.wiki.HomePage;

public class WikiTest extends HomePage {

    @Test
    public void verifyScrollingAndNavigation() {
        scrollToButtonOnApp();
        openMyLists();
        openHistory();
        openNearBy();
        openExplore();
        scrollToTopOnApp();
    }

    @Test
    public void verifySearch(){

    }

    @Test
    public void verifyToggleSettings(){

    }
}
