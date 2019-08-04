package com.linkin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.linkin.dao.NewsDao;
import com.linkin.entity.Category;
import com.linkin.entity.News;
import com.linkin.model.SearchNewsDTO;

@Repository
@Transactional
public class NewsDaoimpl implements NewsDao {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void add(News news) {
		entityManager.persist(news);
	}

	@Override
	public void update(News news) {
		entityManager.merge(news);
	}

	@Override
	public void delete(Long id) {
		entityManager.remove(getById(id));
	}

	@Override
	public News getById(Long id) {

		return entityManager.find(News.class, id);
	}

	@Override
	public List<News> find(SearchNewsDTO searchNewsDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<News> criteriaQuery = criteriaBuilder.createQuery(News.class);
		Root<News> root = criteriaQuery.from(News.class);
		Join<News, Category> category = root.join("category");

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchNewsDTO.getKeyword())) {
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
					"%" + searchNewsDTO.getKeyword().toLowerCase() + "%");
			predicates.add(criteriaBuilder.or(predicate));
		}
		
		if (searchNewsDTO.getCategoryId() != null) {
			predicates.add(criteriaBuilder.equal(category.get("id"), searchNewsDTO.getCategoryId()));

		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// order
		if (StringUtils.equals(searchNewsDTO.getSortBy().getData(), "id")) {
			if (searchNewsDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchNewsDTO.getSortBy().getData(), "title")) {
			if (searchNewsDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("title")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("title")));
			}
		} else if (StringUtils.equals(searchNewsDTO.getSortBy().getData(), "categoryId")) {
			if (searchNewsDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("categoryId")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("categoryId")));
			}
		} else {
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));
		}

		TypedQuery<News> typedQuery = entityManager.createQuery(criteriaQuery.select(root));

		if (searchNewsDTO.getStart() != null) {
			typedQuery.setFirstResult((searchNewsDTO.getStart()));
			typedQuery.setMaxResults(searchNewsDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public Long count(SearchNewsDTO searchNewsDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<News> root = criteriaQuery.from(News.class);
		Join<News, Category> category = root.join("category");

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchNewsDTO.getKeyword())) {
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
					"%" + searchNewsDTO.getKeyword().toLowerCase() + "%");
			predicates.add(criteriaBuilder.or(predicate));
		}

		if (searchNewsDTO.getCategoryId() != null) {
			predicates.add(criteriaBuilder.equal(category.get("id"), searchNewsDTO.getCategoryId()));

		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Long countTotal(SearchNewsDTO searchNewsDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<News> root = criteriaQuery.from(News.class);
		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

}
