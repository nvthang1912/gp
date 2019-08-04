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

import com.linkin.dao.AlbumImageDao;
import com.linkin.entity.AlbumImage;
import com.linkin.model.SearchAlbumImageDTO;

@Repository
@Transactional
public class AlbumImageDaoImpl implements AlbumImageDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void add(AlbumImage albumImage) {
		entityManager.persist(albumImage);
	}

	@Override
	public void update(AlbumImage albumImage) {
		entityManager.merge(albumImage);

	}

	@Override
	public void delete(Long id) {
		entityManager.remove(getByID(id));

	}

	@Override
	public AlbumImage getByID(Long id) {
		return entityManager.find(AlbumImage.class, id);

	}

	@Override
	public List<AlbumImage> find(SearchAlbumImageDTO searchAlbumImageDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AlbumImage> criteriaQuery = criteriaBuilder.createQuery(AlbumImage.class);
		Root<AlbumImage> root = criteriaQuery.from(AlbumImage.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchAlbumImageDTO.getKeyword())) {
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
					"%" + searchAlbumImageDTO.getKeyword().toLowerCase() + "%");
			predicates.add(criteriaBuilder.or(predicate));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// oder
		if (StringUtils.equals(searchAlbumImageDTO.getSortBy().getData(), "id")) {
			if (searchAlbumImageDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchAlbumImageDTO.getSortBy().getData(), "name")) {
			if (searchAlbumImageDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("name")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("name")));
			}
		}

		TypedQuery<AlbumImage> typedQuery = entityManager.createQuery(criteriaQuery.select(root));

		if (searchAlbumImageDTO.getStart() != null) {
			typedQuery.setFirstResult((searchAlbumImageDTO.getStart()));
			typedQuery.setMaxResults(searchAlbumImageDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public Long count(SearchAlbumImageDTO searchAlbumImageDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<AlbumImage> root = criteriaQuery.from(AlbumImage.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchAlbumImageDTO.getKeyword())) {
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
					"%" + searchAlbumImageDTO.getKeyword().toLowerCase() + "%");
			predicates.add(criteriaBuilder.or(predicate));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Long countTotal(SearchAlbumImageDTO searchAlbumImageDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<AlbumImage> root = criteriaQuery.from(AlbumImage.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

}
