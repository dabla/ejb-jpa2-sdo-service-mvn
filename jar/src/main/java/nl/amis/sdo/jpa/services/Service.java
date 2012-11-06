package nl.amis.sdo.jpa.services;

import java.util.List;

import javax.ejb.Remote;

import javax.jws.WebMethod;

import oracle.jbo.common.service.types.FindControl;
import oracle.jbo.common.service.types.FindCriteria;


@Remote
public interface Service {
  public static enum Operator {
      AND("And"),
      OR("Or"),
      EQUALS("Equals","="),
      NOT_EQUALS("Not Equals","<>"),
      LESS_THAN("Less Than","<"),
      GREATER_THAN("Greater Than",">"),
      LESS_THAN_EQUALS("Less Than Equals","<="),
      GREATER_THAN_EQUALS("Greater Than Equals",">="),
      LIKE("Like","LIKE"),
      STARTS_WITH("Starts With","STARTSWITH"),
      ENDS_WITH("Ends With","ENDSWITH"),
      BETWEEN("Between","BETWEEN"),
      NOT_BETWEEN("Not Between","NOTBETWEEN"),
      CONTAINS("Contains","CONTAINS"),
      DOES_NOT_CONTAIN("Does Not Contain","DOESNOTCONTAIN"),
      IN("In","IN"),
      NOT_IN("In","NOTIN");
    
      private final String label;
      private final String value;
      
      Operator(final String value) {
        this(value, value);
      }
              
      Operator(final String label, final String value) {
        this.label = label;
        this.value = value;
      }
      
      public String getLabel() {
        return label;
      }
      
      public String getValue() {
        return value;
      }
  }
  
  @WebMethod(exclude=true)
  public <S> S get(final Class<S> implementation, final Object id);
  @WebMethod(exclude=true)
  public <S> S create(final S dataObject);
  @WebMethod(exclude=true)
  public <S> S update(final S dataObject);
  @WebMethod(exclude=true)
  public <S> void delete(final S dataObject);
  @WebMethod(exclude=true)
  public <S> Long count(final Class<S> implementation, final FindCriteria findCriteria /* JSR-227 API - BC4J Service Runtime */, final FindControl findControl /* BC4J Service Runtime */);
  @WebMethod(exclude=true)
  public <S> List<S> find(final Class<S> implementation, final FindCriteria findCriteria /* JSR-227 API - BC4J Service Runtime */, final FindControl findControl /* BC4J Service Runtime */);  
}