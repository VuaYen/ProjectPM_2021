//package miu.edu.product.repository.impl;
//
//import miu.edu.product.repository.GenericDao;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.List;
//
//@Repository
//public abstract class GenericDaoImpl<T> implements GenericDao<T> {
//
//	@PersistenceContext
//    protected EntityManager entityManager;
//
//    protected Class<T> daoType;
//
//	public void setDaoType(Class<T> type) {
//			daoType = type;
//	}
//
//    @Override
//    public T save( T entity ){
//        entityManager.persist( entity );
//        return entity;
//     }
//
//    public void delete( T entity ){
//        entityManager.remove( entity );
//     }
//
//	@Override
//	public void deleteById(Long id) {
//        T entity = findById( id );
//        delete( entity );
//    }
//
//	@Override
//	public T findById( Long id ){
//	    return (T) entityManager.find( daoType, id );
//	 }
//
//
//	@Override
//	public List<T> findAll(){
//		      return entityManager.createQuery( "from " + daoType.getName() )
//		       .getResultList();
//		   }
//
//	@Override
//	public T update( T entity ){
//	      return entityManager.merge( entity );
//	   }
//
//
//
//
// }