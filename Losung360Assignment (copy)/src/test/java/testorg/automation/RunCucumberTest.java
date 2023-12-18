package testorg.automation;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/features/search.feature",
        glue = "testorg.automation",
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        monochrome = true
)

public class RunCucumberTest {
}
