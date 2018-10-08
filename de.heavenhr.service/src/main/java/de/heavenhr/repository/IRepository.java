package de.heavenhr.repository;

import java.util.List;

/**
 * Interface for Repository. It contains default methods such as
 * getAll(),getEntry() and save()
 * 
 * @author Binu VM
 *
 * @param <E> Entity to be perisited
 * @param <P> Primary key type such as i=Integer,String etc
 */
public interface IRepository<E, P> {

	/**
	 * Get all the object from the repository
	 * 
	 * @return list of objects
	 */
	public List<E> getAll();

	/**
	 * Fetch a persistent object based on the primary key
	 * 
	 * @param p primary key 
	 * @return peristed object based on the primary key
	 */
	public E find(P p);

	/**
	 * Persists an entity
	 * 
	 * @param e Perisistent object
	 */
	public void save(E e);
}
