package com.urlmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urlmanager.entity.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, Integer>{

}
