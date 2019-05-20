package co.qyef.starter.firebase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.qyef.starter.firebase.domain.Authority;


public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
