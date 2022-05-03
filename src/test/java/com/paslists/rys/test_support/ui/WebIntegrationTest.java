package com.paslists.rys.test_support.ui;

import com.paslists.rys.RentYourStuffApplication;
import com.paslists.rys.RentYourStuffProperties;
import io.jmix.ui.Screens;
import io.jmix.ui.testassist.UiTestAssistConfiguration;
import io.jmix.ui.testassist.junit.UiTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;

@UiTest(authenticatedUser = "admin", mainScreenId = "rys_MainScreen", screenBasePackages = "com.paslists.rys")
@ContextConfiguration(classes = {RentYourStuffApplication.class, UiTestAssistConfiguration.class, RentYourStuffProperties.class})
//@ExtendWith(TenantUserEnvironment.class)
@AutoConfigureTestDatabase
public class WebIntegrationTest {

    @BeforeEach
    void removeAllScreens(Screens screens) {
        screens.removeAll();
    }
}
