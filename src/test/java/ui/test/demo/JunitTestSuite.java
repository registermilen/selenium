package ui.test.demo;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import io.qameta.allure.Epic;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   LeaveRequestTest.class
})

@Epic("Leave requests Tests")
public class JunitTestSuite {   
}