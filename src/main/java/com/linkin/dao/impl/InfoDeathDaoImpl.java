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
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.linkin.dao.InfoDeathDao;
import com.linkin.entity.InfoDeath;
import com.linkin.model.SearchInfoDeathDTO;

@Repository
@Transactional
public class InfoDeathDaoImpl implements InfoDeathDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addInfo(InfoDeath infoDeath) {
		entityManager.persist(infoDeath);
	}

	@Override
	public void updateInfo(InfoDeath infoDeath) {
		entityManager.merge(infoDeath);
	}

	@Override
	public void deleteInfo(InfoDeath infoDeath) {
		entityManager.remove(infoDeath);
	}

	@Override
	public InfoDeath getById(Long id) {
		return entityManager.find(InfoDeath.class, id);
	}

	@Override
	public List<InfoDeath> find(SearchInfoDeathDTO searchInfoDeathDTO) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<InfoDeath> criteriaQuery = criteriaBuilder.createQuery(InfoDeath.class);
		Root<InfoDeath> root = criteriaQuery.from(InfoDeath.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchInfoDeathDTO.getKeyword())) {
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
					"%" + searchInfoDeathDTO.getKeyword().toLowerCase() + "%");
			predicates.add(criteriaBuilder.or(predicate));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// order
		if (StringUtils.equals(searchInfoDeathDTO.getSortBy().getData(), "id")) {
			if (searchInfoDeathDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchInfoDeathDTO.getSortBy().getData(), "name")) {
			if (searchInfoDeathDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("name")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("name")));
			}
		} else if (StringUtils.equals(searchInfoDeathDTO.getSortBy().getData(), "birthday")) {
			if (searchInfoDeathDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("birthday")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("birthday")));
			}
		} else if (StringUtils.equals(searchInfoDeathDTO.getSortBy().getData(), "deathDay")) {
			if (searchInfoDeathDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("deathDay")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("deathDay")));
			}
		}

		TypedQuery<InfoDeath> typedQuery = entityManager.createQuery(criteriaQuery.select(root));

		if (searchInfoDeathDTO.getStart() != null) {
			typedQuery.setFirstResult((searchInfoDeathDTO.getStart()));
			typedQuery.setMaxResults(searchInfoDeathDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public Long count(SearchInfoDeathDTO searchInfoDeathDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<InfoDeath> root = criteriaQuery.from(InfoDeath.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchInfoDeathDTO.getKeyword())) {
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
					"%" + searchInfoDeathDTO.getKeyword().toLowerCase() + "%");
			predicates.add(criteriaBuilder.or(predicate));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Long countTotal(SearchInfoDeathDTO searchInfoDeathDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<InfoDeath> root = criteriaQuery.from(InfoDeath.class);
		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

}
