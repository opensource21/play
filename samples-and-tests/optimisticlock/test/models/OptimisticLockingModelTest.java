package models;
/**
 * 
 */

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import models.OptimisticLockingModel.OptimisticLockingCheck;

import org.junit.Test;

import play.data.validation.CheckWithCheck;

/**
 * @author niels
 *
 */
public class OptimisticLockingModelTest {

    /**
     * Test method for {@link models.OptimisticLockingModel.VersionedModel#setVersion(java.lang.Long)}.
     */
    @Test
    public void testSetVersion() {
        final TestModel testModel = new TestModel();
        final OptimisticLockingCheck check = new OptimisticLockingCheckWithoutMessage();
        
        //You must disable setMessage in the check for this test:-/
        testModel.setVersion(Long.valueOf(2));        
        assertTrue(check.isSatisfied(testModel, ""));
        testModel.setVersion(Long.valueOf(2));
        assertTrue(check.isSatisfied(testModel, ""));
        testModel.setVersion(Long.valueOf(3));
        assertTrue(check.isSatisfied(testModel, ""));
        testModel.setVersion(Long.valueOf(1));
        assertFalse(check.isSatisfied(testModel, ""));
    }
    
    public static class TestModel extends OptimisticLockingModel {
        public String text;
    }

    private static final class OptimisticLockingCheckWithoutMessage extends OptimisticLockingCheck {
        
        {
            checkWithCheck= new CheckWithCheck();
            checkWithCheck.setMessage("optimisticLocking.modelHasChanged");
        }

        @Override
        public void setMessage(String message, String... vars) {
        }
        
        
    }
}
