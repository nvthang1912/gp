package com.linkin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.linkin.dao.PersonDao;
import com.linkin.entity.Person;
import com.linkin.model.SearchPersonDTO;

@Repository
@Transactional
public class PersonDaoImpl implements PersonDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void add(Person person) {
		entityManager.persist(person);
	}

	@Override
	public void update(Person person) {
		entityManager.merge(person);

	}

	@Override
	public void delete(Long id) {
		entityManager.remove(getByID(id));

	}

	@Override
	public Person getByID(Long id) {
		return entityManager.find(Person.class, id);

	}

	@Override
	public List<Person> find(SearchPersonDTO searchPersonDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);
		Root<Person> root = criteriaQuery.from(Person.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchPersonDTO.getKeyword())) {
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
					"%" + searchPersonDTO.getKeyword().toLowerCase() + "%");
			predicates.add(criteriaBuilder.or(predicate));
		}
		
		if (searchPersonDTO.isFounder()) {
			predicates.add(criteriaBuilder.isNull(root.get("parent")));
		}
		
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// oder
		if (StringUtils.equals(searchPersonDTO.getSortBy().getData(), "id")) {
			if (searchPersonDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchPersonDTO.getSortBy().getData(), "name")) {
			if (searchPersonDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("name")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("name")));
			}
		}

		TypedQuery<Person> typedQuery = entityManager.createQuery(criteriaQuery.select(root));

		if (searchPersonDTO.getStart() != null) {
			typedQuery.setFirstResult((searchPersonDTO.getStart()));
			typedQuery.setMaxResults(searchPersonDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public Long count(SearchPersonDTO searchPersonDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Person> root = criteriaQuery.from(Person.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchPersonDTO.getKeyword())) {
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
					"%" + searchPersonDTO.getKeyword().toLowerCase() + "%");
			predicates.add(criteriaBuilder.or(predicate));
		}
		if (searchPersonDTO.isFounder()) {
			predicates.add(criteriaBuilder.isNull(root.get("parent")));
		}
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Long countTotal(SearchPersonDTO searchPersonDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Person> root = criteriaQuery.from(Person.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

}
