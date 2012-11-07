package nl.amis.sdo.jpa.entities;

import commonj.sdo.Type;
import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.TypeHelper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import javax.persistence.Table;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import nl.amis.sdo.jpa.entities.customizer.SDODescriptorCustomizer;
import oracle.eclipselink.coherence.integrated.cache.CoherenceInterceptor;
import org.eclipse.persistence.annotations.CacheInterceptor;

import org.eclipse.persistence.annotations.Customizer;
import org.eclipse.persistence.sdo.SDODataObject;

@Entity
@Table(name="EMPLOYEES")
@SequenceGenerator(name="EMPLOYEES_SEQ",sequenceName="EMPLOYEES_SEQ")
@Customizer(SDODescriptorCustomizer.class)
@CacheInterceptor(CoherenceInterceptor.class)
public class EmployeesSDOImpl extends SDODataObject implements EmployeesSDO {
  @SuppressWarnings("compatibility:3441819862910628280")
  private static final long serialVersionUID = 1L;
   public static final int START_PROPERTY_INDEX = 0;

   public static final int END_PROPERTY_INDEX = START_PROPERTY_INDEX + 9;

   public EmployeesSDOImpl() {
    super();
  }

  @Column(name="COMMISSION_PCT")
   public double getCommissionPct() {
      return getDouble(START_PROPERTY_INDEX + 0);
   }

   public void setCommissionPct(double value) {
      set(START_PROPERTY_INDEX + 0 , value);
   }
  @Column(nullable = false, unique = true, length = 25)
   public java.lang.String getEmail() {
      return getString(START_PROPERTY_INDEX + 1);
   }

   public void setEmail(java.lang.String value) {
      set(START_PROPERTY_INDEX + 1 , value);
   }
   
  @Id
  @GeneratedValue(generator="EMPLOYEES_SEQ",strategy=GenerationType.SEQUENCE)
  @Column(name="EMPLOYEE_ID", nullable = false)
   public long getEmployeeId() {
      return getLong(START_PROPERTY_INDEX + 2);
   }

   public void setEmployeeId(long value) {
      set(START_PROPERTY_INDEX + 2 , value);
   }
  @Column(name="FIRST_NAME", length = 20)
   public java.lang.String getFirstName() {
      return getString(START_PROPERTY_INDEX + 3);
   }

   public void setFirstName(java.lang.String value) {
      set(START_PROPERTY_INDEX + 3 , value);
   }
  @Column(name="HIRE_DATE", nullable = false)
  @Temporal(TemporalType.DATE)
   public java.util.Date getHireDate() {
      return getDate(START_PROPERTY_INDEX + 4);
   }

   public void setHireDate(java.util.Date value) {
      set(START_PROPERTY_INDEX + 4 , value);
   }
  @Column(name="JOB_ID", nullable = false, length = 10)
   public java.lang.String getJobId() {
      return getString(START_PROPERTY_INDEX + 5);
   }

   public void setJobId(java.lang.String value) {
      set(START_PROPERTY_INDEX + 5 , value);
   }
  @Column(name="LAST_NAME", nullable = false, length = 25)
   public java.lang.String getLastName() {
      return getString(START_PROPERTY_INDEX + 6);
   }

   public void setLastName(java.lang.String value) {
      set(START_PROPERTY_INDEX + 6 , value);
   }
  @Column(name = "MANAGER_ID")
   public long getManagerId() {
      return getLong(START_PROPERTY_INDEX + 7);
   }

   public void setManagerId(long value) {
      set(START_PROPERTY_INDEX + 7 , value);
   }
  @Column(name="PHONE_NUMBER", length = 20)
   public java.lang.String getPhoneNumber() {
      return getString(START_PROPERTY_INDEX + 8);
   }

   public void setPhoneNumber(java.lang.String value) {
      set(START_PROPERTY_INDEX + 8 , value);
   }
  @Column(name = "SALARY", nullable = false, length = 10)
   public double getSalary() {
      return getDouble(START_PROPERTY_INDEX + 9);
   }

   public void setSalary(double value) {
      set(START_PROPERTY_INDEX + 9 , value);
   }
   
  public String toString() {
      return new StringBuilder(getClass().getName())
      .append("[departmentId=").append(getCommissionPct())
      .append(",email=").append(getEmail())
      .append(",employeeId=").append(getEmployeeId())
      .append(",firstName=").append(getFirstName())
      .append(",hireDate=").append(getHireDate())
      .append(",jobId=").append(getJobId())
      .append(",lastName=").append(getLastName())
      .append(",managerId=").append(getManagerId())
      .append(",phoneNumber=").append(getPhoneNumber())
      .append(",salary=").append(getSalary()).append("]").toString();
    }
}