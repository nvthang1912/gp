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

import com.linkin.dao.AlbumDao;
import com.linkin.entity.Album;
import com.linkin.model.SearchAlbumDTO;

@Repository
@Transactional
public class AlbumDaoImpl implements AlbumDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void add(Album album) {
		entityManager.persist(album);
	}

	@Override
	public void update(Album album) {
		entityManager.merge(album);

	}

	@Override
	public void delete(Long id) {
		entityManager.remove(getByID(id));

	}

	@Override
	public Album getByID(Long id) {
		return entityManager.find(Album.class, id);

	}

	@Override
	public List<Album> find(SearchAlbumDTO searchAlbumDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Album> criteriaQuery = criteriaBuilder.createQuery(Album.class);
		Root<Album> root = criteriaQuery.from(Album.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchAlbumDTO.getKeyword())) {
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
					"%" + searchAlbumDTO.getKeyword().toLowerCase() + "%");
			predicates.add(criteriaBuilder.or(predicate));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// oder
		if (StringUtils.equals(searchAlbumDTO.getSortBy().getData(), "id")) {
			if (searchAlbumDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchAlbumDTO.getSortBy().getData(), "name")) {
			if (searchAlbumDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("name")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("name")));
			}
		} else {
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
		}

		TypedQuery<Album> typedQuery = entityManager.createQuery(criteriaQuery.select(root));

		if (searchAlbumDTO.getStart() != null) {
			typedQuery.setFirstResult((searchAlbumDTO.getStart()));
			typedQuery.setMaxResults(searchAlbumDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public Long count(SearchAlbumDTO searchAlbumDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Album> root = criteriaQuery.from(Album.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchAlbumDTO.getKeyword())) {
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
					"%" + searchAlbumDTO.getKeyword().toLowerCase() + "%");
			predicates.add(criteriaBuilder.or(predicate));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Long countTotal(SearchAlbumDTO searchAlbumDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Album> root = criteriaQuery.from(Album.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

}
