package com.bits_wilp.bookexchangeplatform.repository;

import com.bits_wilp.bookexchangeplatform.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
}
