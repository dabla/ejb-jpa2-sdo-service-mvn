package nl.amis.sdo.jpa.services;


import java.util.List;

import javax.ejb.Remote;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;

import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import nl.amis.sdo.jpa.entities.DepartmentsSDO;
import nl.amis.sdo.jpa.entities.EmployeesSDO;

import oracle.jbo.common.service.types.FindControl;
import oracle.jbo.common.service.types.FindCriteria;
import oracle.jbo.common.service.types.ProcessControl;
import oracle.jbo.common.service.types.ProcessData;
import oracle.jbo.service.errors.ServiceException;

import oracle.webservices.annotations.PortableWebService;
import oracle.webservices.annotations.SDODatabinding;


@Remote
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.WRAPPED, style=SOAPBinding.Style.DOCUMENT)
@PortableWebService(targetNamespace="/nl.amis.sdo.jpa.services/", name="HrSessionEJBBeanWS",
    wsdlLocation="nl/amis/sdo/jpa/services/HrSessionEJBBeanWS.wsdl")
@SDODatabinding(schemaLocation="nl/amis/sdo/jpa/services/HrSessionEJBBeanWS.xsd")
public interface HrSessionEJB extends Service {
  @WebMethod(action="/nl.amis.sdo.jpa.services/getDepartmentsSDO",
      operationName="getDepartmentsSDO")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="getDepartmentsSDO")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="getDepartmentsSDOResponse")
  @WebResult(name="result")
  public DepartmentsSDO getDepartmentsSDO(@WebParam(mode = WebParam.Mode.IN, name="employeeId")
      Long employeeId) throws ServiceException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/createDepartmentsSDO",
      operationName="createDepartmentsSDO")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="createDepartmentsSDO")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="createDepartmentsSDOResponse")
  @WebResult(name="result")
  public DepartmentsSDO createDepartmentsSDO(@WebParam(mode = WebParam.Mode.IN, name="departments")
      DepartmentsSDO departments) throws ServiceException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/updateDepartmentsSDO",
      operationName="updateDepartmentsSDO")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="updateDepartmentsSDO")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="updateDepartmentsSDOResponse")
  @WebResult(name="result")
  public DepartmentsSDO updateDepartmentsSDO(@WebParam(mode = WebParam.Mode.IN, name="departments")
      DepartmentsSDO departments) throws ServiceException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/deleteDepartmentsSDO",
      operationName="deleteDepartmentsSDO")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="deleteDepartmentsSDO")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="deleteDepartmentsSDOResponse")
  public void deleteDepartmentsSDO(@WebParam(mode = WebParam.Mode.IN, name="departments")
      DepartmentsSDO departments) throws ServiceException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/mergeDepartmentsSDO",
      operationName="mergeDepartmentsSDO")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="mergeDepartmentsSDO")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="mergeDepartmentsSDOResponse")
  @WebResult(name="result")
  public DepartmentsSDO mergeDepartmentsSDO(@WebParam(mode = WebParam.Mode.IN, name="departments")
      DepartmentsSDO departments) throws ServiceException;
  
  @WebMethod(action="/nl.amis.sdo.jpa.services/countDepartmentsSDO",
      operationName="countDepartmentsSDO")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="countDepartmentsSDO")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="countDepartmentsSDOResponse")
  @WebResult(name="result")
  public Long countDepartmentsSDO(@WebParam(mode = WebParam.Mode.IN,
          name="findCriteria")
      FindCriteria findCriteria, @WebParam(mode = WebParam.Mode.IN, name="findControl")
      FindControl findControl) throws ServiceException;
  
  @WebMethod(action="/nl.amis.sdo.jpa.services/findDepartmentsSDO",
      operationName="findDepartmentsSDO")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="findDepartmentsSDO")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="findDepartmentsSDOResponse")
  @WebResult(name="result")
  public List<DepartmentsSDO> findDepartmentsSDO(@WebParam(mode = WebParam.Mode.IN,
          name="findCriteria")
      FindCriteria findCriteria, @WebParam(mode = WebParam.Mode.IN, name="findControl")
      FindControl findControl) throws ServiceException;
  
  @WebMethod(action="/nl.amis.sdo.jpa.services/processDepartmentsSDO",
      operationName="processDepartmentsSDO")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="processDepartmentsSDO")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="processDepartmentsSDOResponse")
  @WebResult(name="result")
  public List<DepartmentsSDO> processDepartmentsSDO(@WebParam(mode = WebParam.Mode.IN, name="changeOperation")
      String changeOperation, @WebParam(mode = WebParam.Mode.IN, name="departments")
      List<DepartmentsSDO> departments, @WebParam(mode = WebParam.Mode.IN, name="processControl")
      ProcessControl processControl) throws ServiceException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/processCSDepartmentsSDO",
      operationName="processCSDepartmentsSDO")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="processCSDepartmentsSDO")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="processCSDepartmentsSDOResponse")
  @WebResult(name="result")
  public ProcessData processCSDepartmentsSDO(@WebParam(mode = WebParam.Mode.IN, name="processData")
      ProcessData processData, @WebParam(mode = WebParam.Mode.IN, name="processControl")
      ProcessControl processControl) throws ServiceException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/getEmployeesSDO",
      operationName="getEmployeesSDO")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="getEmployeesSDO")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="getEmployeesSDOResponse")
  @WebResult(name="result")
  public EmployeesSDO getEmployeesSDO(@WebParam(mode = WebParam.Mode.IN, name="employeeId")
      Long employeeId) throws ServiceException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/createEmployeesSDO",
      operationName="createEmployeesSDO")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="createEmployeesSDO")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="createEmployeesSDOResponse")
  @WebResult(name="result")
  public EmployeesSDO createEmployeesSDO(@WebParam(mode = WebParam.Mode.IN, name="employees")
      EmployeesSDO employees) throws ServiceException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/updateEmployeesSDO",
      operationName="updateEmployeesSDO")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="updateEmployeesSDO")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="updateEmployeesSDOResponse")
  @WebResult(name="result")
  public EmployeesSDO updateEmployeesSDO(@WebParam(mode = WebParam.Mode.IN, name="employees")
      EmployeesSDO employees) throws ServiceException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/deleteEmployeesSDO",
      operationName="deleteEmployeesSDO")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="deleteEmployeesSDO")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="deleteEmployeesSDOResponse")
  public void deleteEmployeesSDO(@WebParam(mode = WebParam.Mode.IN, name="employees")
      EmployeesSDO employees) throws ServiceException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/mergeEmployeesSDO",
      operationName="mergeEmployeesSDO")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="mergeEmployeesSDO")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="mergeEmployeesSDOResponse")
  @WebResult(name="result")
  public EmployeesSDO mergeEmployeesSDO(@WebParam(mode = WebParam.Mode.IN, name="employees")
      EmployeesSDO employees) throws ServiceException;
  
  @WebMethod(action="/nl.amis.sdo.jpa.services/countEmployeesSDO",
      operationName="countEmployeesSDO")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="countEmployeesSDO")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="countEmployeesSDOResponse")
  @WebResult(name="result")
  public Long countEmployeesSDO(@WebParam(mode = WebParam.Mode.IN,
          name="findCriteria")
      FindCriteria findCriteria, @WebParam(mode = WebParam.Mode.IN, name="findControl")
      FindControl findControl) throws ServiceException;
  
  @WebMethod(action="/nl.amis.sdo.jpa.services/findEmployeesSDO",
      operationName="findEmployeesSDO")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="findEmployeesSDO")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="findEmployeesSDOResponse")
  @WebResult(name="result")
  public List<EmployeesSDO> findEmployeesSDO(@WebParam(mode = WebParam.Mode.IN,
          name="findCriteria")
      FindCriteria findCriteria, @WebParam(mode = WebParam.Mode.IN, name="findControl")
      FindControl findControl) throws ServiceException;
  
  @WebMethod(action="/nl.amis.sdo.jpa.services/processEmployeesSDO",
      operationName="processEmployeesSDO")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="processEmployeesSDO")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="processEmployeesSDOResponse")
  @WebResult(name="result")
  public List<EmployeesSDO> processEmployeesSDO(@WebParam(mode = WebParam.Mode.IN, name="changeOperation")
      String changeOperation, @WebParam(mode = WebParam.Mode.IN, name="employees")
      List<EmployeesSDO> employees, @WebParam(mode = WebParam.Mode.IN, name="processControl")
      ProcessControl processControl) throws ServiceException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/processCSEmployeesSDO",
      operationName="processCSEmployeesSDO")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="processCSEmployeesSDO")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="processCSEmployeesSDOResponse")
  @WebResult(name="result")
  public ProcessData processCSEmployeesSDO(@WebParam(mode = WebParam.Mode.IN, name="processData")
      ProcessData processData, @WebParam(mode = WebParam.Mode.IN, name="processControl")
      ProcessControl processControl) throws ServiceException;
}