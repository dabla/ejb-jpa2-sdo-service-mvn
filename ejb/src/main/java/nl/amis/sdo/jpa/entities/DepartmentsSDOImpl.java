package nl.amis.sdo.jpa.entities;

import commonj.sdo.Type;
import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.TypeHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import javax.persistence.Table;

import nl.amis.sdo.jpa.entities.customizer.SDODescriptorCustomizer;

import oracle.jbo.common.service.types.FindCriteria;

import org.eclipse.persistence.annotations.Customizer;
import org.eclipse.persistence.sdo.SDODataObject;

@Entity
@Table(name="DEPARTMENTS")
@SequenceGenerator(name="DEPARTMENTS_SEQ",sequenceName="DEPARTMENTS_SEQ")
@Customizer(SDODescriptorCustomizer.class)
public class DepartmentsSDOImpl extends SDODataObject implements DepartmentsSDO {
  @SuppressWarnings("compatibility:-6447132931414648339")
  private static final long serialVersionUID = 1L;
   public static final int START_PROPERTY_INDEX = 0;

   public static final int END_PROPERTY_INDEX = START_PROPERTY_INDEX + 4;

   public DepartmentsSDOImpl() {
       super();
     }

  @Id
  @GeneratedValue(generator="DEPARTMENTS_SEQ",strategy=GenerationType.SEQUENCE)
  @Column(name="DEPARTMENT_ID", nullable = false)
   public long getDepartmentId() {
      return getLong(START_PROPERTY_INDEX + 0);
   }

   public void setDepartmentId(long value) {
      set(START_PROPERTY_INDEX + 0 , value);
   }

  @Column(name="DEPARTMENT_NAME", nullable = false, length = 30)
   public java.lang.String getDepartmentName() {
      return getString(START_PROPERTY_INDEX + 1);
   }

   public void setDepartmentName(java.lang.String value) {
      set(START_PROPERTY_INDEX + 1 , value);
   }

  @OneToMany(fetch = FetchType.EAGER, targetEntity = nl.amis.sdo.jpa.entities.EmployeesSDOImpl.class)
  @JoinColumn(name = "EMPLOYEE_ID")
   public List<EmployeesSDO> getEmployeesList() {
      return getList(START_PROPERTY_INDEX + 2);
   }

   public void setEmployeesList(List<EmployeesSDO> value) {
      set(START_PROPERTY_INDEX + 2 , value);
   }

  @Column(name="LOCATION_ID")
   public long getLocationId() {
      return getLong(START_PROPERTY_INDEX + 3);
   }

   public void setLocationId(long value) {
      set(START_PROPERTY_INDEX + 3 , value);
   }

  @ManyToOne(targetEntity = nl.amis.sdo.jpa.entities.EmployeesSDOImpl.class)
  @JoinColumn(name = "MANAGER_ID")
   public EmployeesSDO getManager() {
      return (EmployeesSDO)get(START_PROPERTY_INDEX + 4);
   }

   public void setManager(nl.amis.sdo.jpa.entities.EmployeesSDO value) {
      set(START_PROPERTY_INDEX + 4 , value);
   }
   
  public String toString() {
       final StringBuilder result = new StringBuilder(getClass().getName())
       .append("[departmentId=").append(getDepartmentId())
       .append(",departmentName=").append(getDepartmentName());
       if (getEmployeesList() != null) {
         result.append(",employeesList=").append(Arrays.toString(getEmployeesList().toArray()));
       }
       return result.append(",locationId=").append(getLocationId())
       .append(",manager=").append(getManager()).append("]").toString();
     }
}