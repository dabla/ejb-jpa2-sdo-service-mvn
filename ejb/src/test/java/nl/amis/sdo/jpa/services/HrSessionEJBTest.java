package nl.amis.sdo.jpa.services;

import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.TypeHelper;
import commonj.sdo.helper.XSDHelper;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import nl.amis.sdo.jpa.entities.DepartmentsSDO;
import nl.amis.sdo.jpa.entities.EmployeesSDO;
import oracle.jbo.common.service.types.FindControl;
import oracle.jbo.common.service.types.FindCriteria;
import oracle.jbo.common.service.types.ViewCriteria;
import oracle.jbo.common.service.types.ViewCriteriaItem;
import oracle.jbo.common.service.types.ViewCriteriaRow;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

public class HrSessionEJBTest {

    private static final class HrSessionEJBBeanLocal extends HrSessionEJBBean {

        @Override
        protected EntityManager getEntityManager() {
            final EntityManager entityManager = Persistence.createEntityManagerFactory("ModelSDOLocal").createEntityManager();
            entityManager.getTransaction().begin();
            return entityManager;
        }
    }

    @BeforeClass
    public static void setUp() throws Exception {
        final ClassLoader loader =
                Thread.currentThread().getContextClassLoader();
        XSDHelper.INSTANCE.define(loader.getResourceAsStream("META-INF/wsdl/ServiceException.xsd"),
                "META-INF/wsdl/");
        XSDHelper.INSTANCE.define(loader.getResourceAsStream("META-INF/wsdl/BC4JService.xsd"),
                "META-INF/wsdl/");
        XSDHelper.INSTANCE.define(loader.getResourceAsStream("META-INF/wsdl/BC4JServiceCS.xsd"),
                "META-INF/wsdl/");
        XSDHelper.INSTANCE.define(loader.getResourceAsStream("nl/amis/sdo/jpa/entities/EmployeesSDO.xsd"),
                "nl/amis/sdo/jpa/entities/");
        XSDHelper.INSTANCE.define(loader.getResourceAsStream("nl/amis/sdo/jpa/entities/DepartmentsSDO.xsd"),
                "nl/amis/sdo/jpa/entities/");
        XSDHelper.INSTANCE.define(loader.getResourceAsStream("nl/amis/sdo/jpa/services/HrSessionEJBBeanWS.xsd"),
                "nl/amis/sdo/jpa/services/");
    }

    @Test
    public void testCountDepartments() {
        final HrSessionEJB hrSessionEJB = new HrSessionEJBBeanLocal();
        final FindCriteria findCriteria = mockFindCriteria();
        final FindControl findControl = mockFindControl();

        final Long result = hrSessionEJB.count(DepartmentsSDO.class, findCriteria, findControl);

        assertTrue(result > 0);
    }

    @Test
    public void testCountEmployees() {
        final HrSessionEJB hrSessionEJB = new HrSessionEJBBeanLocal();
        final FindCriteria findCriteria = mockFindCriteria();
        final FindControl findControl = mockFindControl();

        final Long result = hrSessionEJB.count(EmployeesSDO.class, findCriteria, findControl);

        assertTrue(result > 0);
    }

    @Test
    public void testFindDepartments() {
        final HrSessionEJB hrSessionEJB = new HrSessionEJBBeanLocal();
        final FindCriteria findCriteria = mockFindCriteria();
        final FindControl findControl = mockFindControl();

        final List<DepartmentsSDO> result = hrSessionEJB.find(DepartmentsSDO.class, findCriteria, findControl);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testFindEmployees() {
        final HrSessionEJB hrSessionEJB = new HrSessionEJBBeanLocal();
        final FindCriteria findCriteria = mockFindCriteria();
        final FindControl findControl = mockFindControl();

        final List<EmployeesSDO> result = hrSessionEJB.find(EmployeesSDO.class, findCriteria, findControl);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    public void testFindEmployeesBetweenHireDates() {
        final HrSessionEJB hrSessionEJB = new HrSessionEJBBeanLocal();
        final FindCriteria findCriteria = mockFindCriteria();
        final FindControl findControl = mockFindControl();

        final List values = new ArrayList();
        values.add("2008-04-21T00:00:00.0Z");
        values.add("2012-08-22T00:00:00.0Z");

        final List<Object> item = new ArrayList<Object>();
        final ViewCriteriaItem viewCriteriaItem =
                (ViewCriteriaItem) DataFactory.INSTANCE.create(TypeHelper.INSTANCE.getType(ViewCriteriaItem.class));
        viewCriteriaItem.setConjunction("And");
        viewCriteriaItem.setUpperCaseCompare(true);
        viewCriteriaItem.setAttribute("hireDate");
        viewCriteriaItem.setOperator("between");
        viewCriteriaItem.setValue(values);
        item.add(viewCriteriaItem);

        findCriteria.setFilter((ViewCriteria) DataFactory.INSTANCE.create(TypeHelper.INSTANCE.getType(ViewCriteria.class)));
        findCriteria.getFilter().setGroup(new ArrayList(1));
        final ViewCriteriaRow viewCriteriaRow =
                (ViewCriteriaRow) DataFactory.INSTANCE.create(TypeHelper.INSTANCE.getType(ViewCriteriaRow.class));
        viewCriteriaRow.setConjunction("And");
        viewCriteriaRow.setUpperCaseCompare(true);
        viewCriteriaRow.setItem(item);
        findCriteria.getFilter().getGroup().add(viewCriteriaRow);
        
        final List<EmployeesSDO> result = hrSessionEJB.findEmployeesSDO(findCriteria, findControl);
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
    }

    protected FindCriteria mockFindCriteria() {
        final FindCriteria findCriteria = (FindCriteria) DataFactory.INSTANCE.create(TypeHelper.INSTANCE.getType(FindCriteria.class));
        findCriteria.setFetchStart(0);
        findCriteria.setFetchSize(-1);
        findCriteria.setExcludeAttribute(false);
        return findCriteria;
    }

    protected FindControl mockFindControl() {
        final FindControl findControl = (FindControl) DataFactory.INSTANCE.create(TypeHelper.INSTANCE.getType(FindControl.class));
        findControl.setRetrieveAllTranslations(false);
        return findControl;
    }
}