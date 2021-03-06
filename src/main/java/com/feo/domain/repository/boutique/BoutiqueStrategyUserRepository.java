package com.feo.domain.repository.boutique;

import com.feo.domain.model.boutique.BoutiqueStrategyUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BoutiqueStrategyUserRepository extends JpaRepository<BoutiqueStrategyUser,Long> {

    Page<BoutiqueStrategyUser> findAllByStrategyIdInAndJoinDateAfterAndJoinDateBefore(Long[] ids,Date startTime,Date endTime, Pageable pageable);

    Page<BoutiqueStrategyUser> findAllByUidAndJoinDateAfterAndJoinDateBefore(Integer uid,Date startTime,Date endTime, Pageable pageable);

    void deleteBoutiqueStrategyUserByStrategyIdAndAndUid(Long strategyId,Integer uid);

    List<BoutiqueStrategyUser> findAllByStrategyId(Long strategyId);
}
