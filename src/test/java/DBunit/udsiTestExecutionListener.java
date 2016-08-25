package DBunit;

import com.bres.siodme.annotation.DataSets;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.util.fileloader.XlsDataFileLoader;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

/**
 * Created by Adam on 2016-08-25.
 */

/**
 * Class defines a listener API that can intercept the events in the various phases of the test case execution
 */
public class udsiTestExecutionListener implements TestExecutionListener {

    private IDatabaseTester iDatabaseTester;

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {

    }

    @Override
    public void prepareTestInstance(TestContext testContext) throws Exception {

    }

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        // Check for existence of @DataSets for the method under testing
        DataSets dataSetsAnnotation = testContext.getTestMethod().getAnnotation(DataSets.class);
        if (dataSetsAnnotation == null) { return; }

        String dataSetName = dataSetsAnnotation.setUpDataSet();

        if (dataSetName.length() > 0) {  // Populate with data
            iDatabaseTester = (IDatabaseTester) testContext.getApplicationContext().getBean("iDatabaseTester");
            XlsDataFileLoader xlsDataFileLoader = (XlsDataFileLoader) testContext.getApplicationContext().getBean("xlsDataFileLoader");
            IDataSet iDataSet = xlsDataFileLoader.load(dataSetName);

            iDatabaseTester.setDataSet(iDataSet);
            iDatabaseTester.onSetup();
        }
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        // clearing up testing data if exists
        if (iDatabaseTester != null) {
            iDatabaseTester.onTearDown();
        }
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {

    }
}
