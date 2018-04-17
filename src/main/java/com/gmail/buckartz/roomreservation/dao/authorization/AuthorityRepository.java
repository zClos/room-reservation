package com.gmail.buckartz.roomreservation.dao.authorization;

import com.gmail.buckartz.roomreservation.domain.employee.Authority;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {
    @Query(value = "SELECT " +
            "CASE" +
            " WHEN count(*) > 0" +
            " THEN TRUE" +
            " ELSE FALSE " +
            "END " +
            "FROM Authority" +
            " WHERE LOGIN = :login" +
            " LIMIT 1", nativeQuery = true)
    boolean existsByLogin(@Param("login") String login);

    Authority findFirstByLogin(String login);
}
