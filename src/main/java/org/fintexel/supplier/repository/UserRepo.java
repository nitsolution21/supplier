package org.fintexel.supplier.repository;

import org.fintexel.supplier.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long>{

}
