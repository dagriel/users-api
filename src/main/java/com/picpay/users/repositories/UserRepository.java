package com.picpay.users.repositories;

import com.picpay.users.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

//    Retorna com parte do nome completo - Consulta definida JPQL com parâmetros nomeados
    @Query(value = "select u from UserModel u order by u.full_name asc")
    List<UserModel> findAllByFullName();

//    Retorna com parte do nome completo - Consulta definida JPQL com parâmetros nomeados e ordenado por full_name
    /*@Query(value = "select u from UserModel u where u.full_name like :full_name% order by u.full_name asc")
    List<UserModel> findByFullNameStartsWith(@Param("full_name") String full_name);*/

}
