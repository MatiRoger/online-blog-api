package com.rogerdev.blog_service.domain.usersec;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserSec, Long> {
}
