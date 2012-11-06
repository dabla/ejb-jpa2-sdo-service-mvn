package nl.amis.sdo.jpa.services;


import commonj.sdo.ChangeSummary;
import commonj.sdo.DataObject;
import commonj.sdo.helper.TypeHelper;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.xml.bind.DatatypeConverter;
import oracle.jbo.common.service.types.FindControl;
import oracle.jbo.common.service.types.FindCriteria;
import oracle.jbo.common.service.types.ProcessData;
import oracle.jbo.common.service.types.SortAttribute;
import oracle.jbo.common.service.types.ViewCriteriaItem;
import oracle.jbo.common.service.types.ViewCriteriaRow;
import org.eclipse.persistence.sdo.SDOType;


public abstract class AbstractService implements Service {
  protected static final Logger logger = Logger.getLogger(AbstractService.class.getName());
  
  public AbstractService() {
    super();
  }
  
  public <S> S get(final Class<S> implementation, final Object id) {
    logger.log(Level.FINEST, "implementation: {0}", implementation);
    logger.log(Level.FINEST, "id: {0}", id);
    return (S)getEntityManager().find(getEntityClass(implementation), id);
  }
  
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public <S> S create(final S dataObject) {
    logger.log(Level.FINEST, "dataObject: {0}", dataObject);
    if (dataObject != null) {
      logger.log(Level.FINEST, "implementation: {0}", dataObject.getClass().getName());
      getEntityManager().persist(dataObject);
      getEntityManager().flush();  // http://stackoverflow.com/questions/7206891/is-calling-persist-flush-and-refresh-in-one-method-to-persist-an-entity-th
      getEntityManager().refresh(dataObject);
      // now the entity has an id
      System.out.println("dataObject: " + dataObject);
      return dataObject;
    }
    return null;
  }
  
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public <S> S update(final S dataObject) {
    logger.log(Level.FINEST, "dataObject: {0}", dataObject);
    if (dataObject != null) {
      logger.log(Level.FINEST, "implementation: {0}", dataObject.getClass().getName());
      return getEntityManager().merge(dataObject);
    }
    return null;
  }
  
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public <S> void delete(final S dataObject) {
    logger.log(Level.FINEST, "dataObject: {0}", dataObject);
    if (dataObject != null) {
      logger.log(Level.FINEST, "implementation: {0}", dataObject.getClass().getName());
      getEntityManager().remove(getEntityManager().find(dataObject.getClass(), getId(dataObject)));
    }
  }
  
  public <S> Long count(final Class<S> implementation, final FindCriteria findCriteria /* JSR-227 API - BC4J Service Runtime */, final FindControl findControl /* BC4J Service Runtime */) {
    final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
    final Root<S> root = cq.from(getEntityClass(implementation));
    cq.select(cb.count(root));
    appendToQueryBuilder(cb, cq, root, findCriteria);
    return getEntityManager().createQuery(cq).getSingleResult();
  }
  
  public <S> List<S> find(final Class<S> implementation, final FindCriteria findCriteria /* JSR-227 API - BC4J Service Runtime */, final FindControl findControl /* BC4J Service Runtime */) {
    final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    final CriteriaQuery<S> cq = cb.createQuery(implementation);
    final Root<S> root = cq.from(getEntityClass(implementation));
    cq.select(root);
    appendToQueryBuilder(cb, cq, root, findCriteria);
    
    final TypedQuery<S> tq = getEntityManager().createQuery(cq);
    
    logger.log(Level.FINEST, "fetchStart: {0}", findCriteria.getFetchStart());
      
    if (findCriteria.getFetchStart() > -1) {
      tq.setFirstResult(findCriteria.getFetchStart());
    }
    
    logger.log(Level.FINEST, "fetchSize: {0}", findCriteria.getFetchSize());
    
    if (findCriteria.getFetchSize() > 0) {
      tq.setMaxResults(findCriteria.getFetchSize());
    }

    final List<S> resultList = tq.getResultList();
    
    logger.log(Level.FINEST, "resultList: {0}", resultList);
      
    return resultList;
  }
  
  protected abstract EntityManager getEntityManager();
  
  protected <S> ProcessData process(final ProcessData request) {
    final List<S> dataObjects = request.getValue();
    
    for (int index = 0; index < dataObjects.size(); index++) {
      final ChangeSummary changeSummary = request.getChangeSummary();
      final DataObject dataObject = (DataObject)dataObjects.get(index);
      
      if (changeSummary.isCreated(dataObject)) {
        dataObjects.set(index, create(dataObjects.get(index)));
      }
      else if (changeSummary.isModified(dataObject)) {
        dataObjects.set(index, update(dataObjects.get(index)));
      }
      else if (changeSummary.isDeleted(dataObject)) {
        delete(dataObjects.get(index));
      }
    }
    
    return request;
  }
  
   /*protected <S extends BaseDataObject<T>, T extends BaseEntity<S>> List<S> process(final Class<T> implementation, final String operation, final List<S> dataObjects) {
    final String[] operations = new String[dataObjects.size()];
    Arrays.fill(operations, operation);
    return process(implementation, operations, dataObjects);
   }*/
  
  protected <S> List<S> process(final String operation, final List<S> dataObjects) {
  if (dataObjects != null) {
      for (int index = 0; index < dataObjects.size(); index++) {
        logger.log(Level.FINEST, "operations[{0}]: {1}", new Object[]{index,operation});
        
        if ("Create".equals(operation)) {
          dataObjects.set(index, create(dataObjects.get(index)));
        }
        else if ("Merge".equals(operation) || "Update".equals(operation)) {
          dataObjects.set(index, update(dataObjects.get(index)));
        }
        else if ("Delete".equals(operation)) {
          delete(dataObjects.get(index));
        }
      }
  }
  
  return dataObjects;
  }
  
  protected <S> void appendToQueryBuilder(final CriteriaBuilder cb, final CriteriaQuery cq, final Root<S> root, final FindCriteria findCriteria) {
    logger.log(Level.FINEST, "findCriteria: {0}", findCriteria);

    if (findCriteria != null) {
      final List<Order> order = buildOrderBy(cb, cq, root, findCriteria);
      
      logger.log(Level.FINEST, "order: {0}", order);
      
      if (order != null) {
        cq.orderBy(order);
      }
      
      final Predicate predicate = buildWhere(cb, cq, root, findCriteria);
      
      logger.log(Level.FINEST, "predicate: {0}", predicate);

      if (predicate != null) {
        cq.where(predicate);
      }
    }
  }
  
  protected <S> List<Order> buildOrderBy(final CriteriaBuilder cb, final CriteriaQuery cq, final Root<S> root, final FindCriteria findCriteria) {
    logger.log(Level.FINEST, "sortOrder: {0}", findCriteria.getSortOrder());
    
    if (findCriteria.getSortOrder() != null) {
      final List<SortAttribute> sortAttributes = findCriteria.getSortOrder().getSortAttribute();
      
      logger.log(Level.FINEST, "sortAttributes: {0}", sortAttributes);

      if (sortAttributes != null) {
        final List<Order> orders = new ArrayList<Order>(sortAttributes.size());
        
        for (final SortAttribute sortAttribute : sortAttributes) {
          logger.log(Level.FINEST, "sortAttribute: {0}", sortAttribute);
          logger.log(Level.FINEST, "descending: {0}", sortAttribute.getDescending());
          logger.log(Level.FINEST, "name: {0}", sortAttribute.getName());

          orders.add(sortAttribute.getDescending() ? cb.desc(root.get(camelize(sortAttribute.getName()))) : cb.asc(root.get(camelize(sortAttribute.getName()))));
        }
        
        return orders;
      }
    }
    
    return null;
  }
  
  protected <S> Predicate buildWhere(final CriteriaBuilder cb, final CriteriaQuery cq, final Root<S> root, final FindCriteria findCriteria) {
      logger.log(Level.FINEST, "filter: {0}", findCriteria.getFilter());
      
      if (findCriteria.getFilter() != null) {
        final List<ViewCriteriaRow> groups = findCriteria.getFilter().getGroup();
        
        logger.log(Level.FINEST, "groups: {0}", groups);

        if (groups != null) {
          final List<Predicate> wherePredicates = new ArrayList<Predicate>();
          
          for (final ViewCriteriaRow group : groups) {
            logger.log(Level.FINEST, "group: {0}", group);
            
            final List<ViewCriteriaItem> items = group.getItem();
            
            logger.log(Level.FINEST, "items: {0}", items);
            
            if (items != null) {
              for (final ViewCriteriaItem item : items) {
                logger.log(Level.FINEST, "item: {0}", item);
                logger.log(Level.FINEST, "upperCaseCompare: {0}", item.getUpperCaseCompare());
                logger.log(Level.FINEST, "attribute: {0}", item.getAttribute());
                logger.log(Level.FINEST, "operator: {0}", item.getOperator());
                logger.log(Level.FINEST, "list value: {0}", item.getValue());
                
                if ((item.getValue() != null) && !item.getValue().isEmpty()) {
                  logger.log(Level.FINEST, "first value: {0}", item.getValue().get(0));
                  final Path path = root.get(camelize(item.getAttribute()));
                  
                  if (Operator.EQUALS.getValue().equals(item.getOperator())) {
                    wherePredicates.add(toPredicate(cb, cb.equal(path, item.getValue().get(0)), item));
                  }
                  else if (Operator.NOT_EQUALS.getValue().equals(item.getOperator())) {
                    wherePredicates.add(toPredicate(cb, cb.notEqual(path, item.getValue().get(0)), item));
                  }
                  else if (Operator.GREATER_THAN_EQUALS.getValue().equalsIgnoreCase(item.getOperator())) {
                    wherePredicates.add(toPredicate(cb, cb.ge(path, Long.parseLong((String)item.getValue().get(0))), item));
                  }
                  else if (Operator.LESS_THAN_EQUALS.getValue().equalsIgnoreCase(item.getOperator())) {
                    wherePredicates.add(toPredicate(cb, cb.le(path, Long.parseLong((String)item.getValue().get(0))), item));
                  }
                  else if (Operator.GREATER_THAN.getValue().equalsIgnoreCase(item.getOperator())) {
                    wherePredicates.add(toPredicate(cb, cb.greaterThan(path, Long.parseLong((String)item.getValue().get(0))), item));
                  }
                  else if (Operator.LESS_THAN.getValue().equalsIgnoreCase(item.getOperator())) {
                    wherePredicates.add(toPredicate(cb, cb.lessThan(path, Long.parseLong((String)item.getValue().get(0))), item));
                  }
                  else if (Operator.LIKE.getValue().equalsIgnoreCase(item.getOperator())) {
                    final String value = new StringBuilder("%").append(item.getValue().get(0)).append("%").toString();
                    logger.log(Level.FINEST, "string value: {0}", value);
                    
                    if (item.getUpperCaseCompare()) {
                      wherePredicates.add(toPredicate(cb, cb.like(cb.upper(path), value.toUpperCase()), item));
                    }
                    else {
                      wherePredicates.add(toPredicate(cb, cb.like(cb.lower(path), value.toLowerCase()), item));
                    }
                  }
                  else if (Operator.STARTS_WITH.getValue().equalsIgnoreCase(item.getOperator())) {
                    final String value = new StringBuilder().append(item.getValue().get(0)).append("%").toString();
                    logger.log(Level.FINEST, "string value: {0}", value);
                    
                    if (item.getUpperCaseCompare()) {
                      wherePredicates.add(toPredicate(cb, cb.like(cb.upper(path), value.toUpperCase()), item));
                    }
                    else {
                      wherePredicates.add(toPredicate(cb, cb.like(cb.lower(path), value.toLowerCase()), item));
                    }
                  }
                  else if (Operator.ENDS_WITH.getValue().equalsIgnoreCase(item.getOperator())) {
                    final String value = new StringBuilder("%").append(item.getValue().get(0)).toString();
                    logger.log(Level.FINEST, "string value: {0}", value);
                    
                    if (item.getUpperCaseCompare()) {
                      wherePredicates.add(toPredicate(cb, cb.like(cb.upper(path), value.toUpperCase()), item));
                    }
                    else {
                      wherePredicates.add(toPredicate(cb, cb.like(cb.lower(path), value.toLowerCase()), item));
                    }
                  }
                  else if (Operator.BETWEEN.getValue().equalsIgnoreCase(item.getOperator())) {
                    if (path.getJavaType().isAssignableFrom(Date.class)) {
                      wherePredicates.add(toPredicate(cb, cb.ge(path, DatatypeConverter.parseDateTime((String)item.getValue().get(0)).getTimeInMillis()), item));
                      wherePredicates.add(toPredicate(cb, cb.le(path, DatatypeConverter.parseDateTime((String)item.getValue().get(1)).getTimeInMillis()), item));
                    }
                    else {
                      wherePredicates.add(toPredicate(cb, cb.ge(path, Long.parseLong((String)item.getValue().get(0))), item));
                      wherePredicates.add(toPredicate(cb, cb.le(path, Long.parseLong((String)item.getValue().get(1))), item));
                    }
                  }
                  else if (Operator.NOT_BETWEEN.getValue().equalsIgnoreCase(item.getOperator())) {
                    if (path.getJavaType().isAssignableFrom(Date.class)) {
                      wherePredicates.add(toPredicate(cb, cb.not(cb.ge(path, DatatypeConverter.parseDateTime((String)item.getValue().get(0)).getTimeInMillis())), item));
                      wherePredicates.add(toPredicate(cb, cb.not(cb.le(path, DatatypeConverter.parseDateTime((String)item.getValue().get(1)).getTimeInMillis())), item));
                    }
                    else {
                      wherePredicates.add(toPredicate(cb, cb.not(cb.ge(path, Long.parseLong((String)item.getValue().get(0)))), item));
                      wherePredicates.add(toPredicate(cb, cb.not(cb.le(path, Long.parseLong((String)item.getValue().get(1)))), item));
                    }
                  }
                  else if (Operator.CONTAINS.getValue().equalsIgnoreCase(item.getOperator())) {
                    wherePredicates.add(toPredicate(cb, cb.isNotNull(path), item));
                  }
                  else if (Operator.DOES_NOT_CONTAIN.getValue().equalsIgnoreCase(item.getOperator())) {
                    wherePredicates.add(toPredicate(cb, cb.isNull(path), item));
                  }
                  else if (Operator.IN.getValue().equalsIgnoreCase(item.getOperator())) {
                    wherePredicates.add(toPredicate(cb, path.in(item.getValue()), item));
                  }
                  else if (Operator.NOT_IN.getValue().equalsIgnoreCase(item.getOperator())) {
                    wherePredicates.add(toPredicate(cb, cb.not(path.in(item.getValue())), item));
                  }
                }
              }
            }
            
            if (Operator.AND.getValue().equals(group.getConjunction())) {
              return cb.and(wherePredicates.toArray(new Predicate[0]));
            }
            
            return cb.or(wherePredicates.toArray(new Predicate[0]));
          }
        }
      }
      
      return null;
    }
    
    protected Predicate toPredicate(final CriteriaBuilder cb, final Predicate predicate, final ViewCriteriaItem item) {
      if ("And".equals(item.getConjunction())) {
        return cb.and(predicate);
      }
      
      return cb.or(predicate);
    }
  
  // http://stackoverflow.com/questions/4052840/most-efficient-way-to-make-the-first-character-of-a-string-lower-case
  protected String camelize(final String value) {
    if (value != null) {
      return Character.toLowerCase(value.charAt(0)) + (value.length() > 1 ? value.substring(1) : "");
    }
    
    return null;
  }
  
  /*protected <S,T extends S> Class<T> getImplementation(final Class<S> implementation) {
    try {
      final Class<T> result = (Class<T>)Class.forName(new StringBuilder(implementation.getName()).append("Impl").toString());
      System.out.println("RESULT: " + result);
      return result;
    }
    catch(ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }*/
  
  protected <S> Long getId(final S dataObject) {
    for (final Method method : dataObject.getClass().getMethods()) {
        if (method.isAnnotationPresent(Id.class)) {
          try {
            return (Long)method.invoke(dataObject, new Object[]{ null });
          }
          catch(Exception e) {
            throw new RuntimeException(e);
          }
      }
    }
    
    return null;
  }
  
  protected Class getEntityClass(final Class implementation) {
    return ((SDOType)TypeHelper.INSTANCE.getType(implementation)).getImplClass();
  }
}
