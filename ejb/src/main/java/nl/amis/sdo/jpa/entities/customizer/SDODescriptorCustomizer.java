package nl.amis.sdo.jpa.entities.customizer;

import commonj.sdo.helper.DataFactory;

import commonj.sdo.helper.TypeHelper;

import commonj.sdo.helper.XSDHelper;

import java.util.logging.Level;
import java.util.logging.Logger;

import nl.amis.sdo.jpa.entities.DepartmentsSDO;

import nl.amis.sdo.jpa.services.AbstractService;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.sdo.SDOType;
import static org.eclipse.persistence.sdo.SDOType.TypeInstantiationPolicy;


public final class SDODescriptorCustomizer implements DescriptorCustomizer {
  private static final Logger logger = Logger.getLogger(SDODescriptorCustomizer.class.getName());
  
  /*static {
    final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    XSDHelper.INSTANCE.define(classLoader.getResourceAsStream("META-INF/wsdl/ServiceException.xsd"),
                            "META-INF/wsdl/");
    XSDHelper.INSTANCE.define(classLoader.getResourceAsStream("META-INF/wsdl/BC4JService.xsd"),
                            "META-INF/wsdl/");
    XSDHelper.INSTANCE.define(classLoader.getResourceAsStream("META-INF/wsdl/BC4JServiceCS.xsd"),
                            "META-INF/wsdl/");
    XSDHelper.INSTANCE.define(classLoader.getResourceAsStream("nl/amis/sdo/jpa/entities/EmployeesSDO.xsd"),
                            "nl/amis/sdo/jpa/entities/");
    XSDHelper.INSTANCE.define(classLoader.getResourceAsStream("nl/amis/sdo/jpa/entities/DepartmentsSDO.xsd"),
                            "nl/amis/sdo/jpa/entities/");
    XSDHelper.INSTANCE.define(classLoader.getResourceAsStream("nl/amis/sdo/jpa/services/HrSessionEJBBeanWS.xsd"),
                            "nl/amis/sdo/jpa/services/");
  }*/
  
  public SDODescriptorCustomizer() {
    super();
  }

  // http://www.eclipse.org/eclipselink/xsds/eclipselink_orm_2_1.xsd
  public void customize(final ClassDescriptor descriptor) throws Exception {
    logger.log(Level.INFO, "customize: {0}", descriptor);
    final Class implementation = findSDOInterface(descriptor);
    
    logger.log(Level.FINEST, "implementation: {0}", implementation);

    if (implementation != null) {
      XSDHelper.INSTANCE.define(Thread.currentThread().getContextClassLoader().getResourceAsStream(implementation.getName().replace(".", "/") + ".xsd"), null);
      final SDOType type = (SDOType)TypeHelper.INSTANCE.getType(implementation);
      
      logger.log(Level.FINEST, "type: {0}", type);
      // http://grepcode.com/file/maven.glassfish.org/content/repositories/eclipselink/org.eclipse.persistence/eclipselink/2.0.0/org/eclipse/persistence/sdo/SDOType.java#SDOType.TypeInstantiationPolicy
      descriptor.useFactoryInstantiationPolicy(new TypeInstantiationPolicy(type), "buildNewInstance");
    }
  }
  
  private Class findSDOInterface(final ClassDescriptor descriptor) {
    if (descriptor.getJavaClass().getInterfaces() != null) {
      for (final Class c : descriptor.getJavaClass().getInterfaces()) {
        if (c.getName().endsWith("SDO")) {
          return c;
        }
      }
    }
    
    return null;
  }
}
