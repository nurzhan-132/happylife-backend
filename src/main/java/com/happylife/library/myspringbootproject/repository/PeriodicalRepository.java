package com.happylife.library.myspringbootproject.repository;

import com.happylife.library.myspringbootproject.models.Periodical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface PeriodicalRepository extends JpaRepository<Periodical, Long> {

    @Procedure(name = Periodical.NamedQuery_GetAllPeriodicals)
    List<Periodical> getAllPeriodicals();

    @Procedure(name = Periodical.NamedQuery_SubscribeTo)
    void subscribeTo(@Param("in_periodical") Long periodicalId,
                     @Param("in_username") String username);

    @Procedure(name = Periodical.NamedQuery_UnSubscribeFrom)
    void unsubscribeFrom(@Param("in_periodical") Long periodicalId,
                         @Param("in_username") String username);

    @Procedure(name = Periodical.NamedQuery_DeletePeriodical)
    void deletePeriodical(@Param("periodical_id") Long periodicalId);

    @Procedure(name = Periodical.NamedQuery_UpdateAvailability)
    void updateAvailability(@Param("periodical_id") Long periodicalId);

}
