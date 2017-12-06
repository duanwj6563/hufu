package com.sunlands.feo.domain.repository.boutique;

import com.sunlands.feo.domain.model.boutique.BoutiqueRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface BoutiqueRecordRepository extends JpaRepository<BoutiqueRecord,Long> {

    /**
     * 优秀录音分页查询相关方法
     */
    Page<BoutiqueRecord> findAllByIdInAndCounselorIdInAndJoinDateAfterAndJoinDateBeforeAndGoodTypeInAndItemTypeIn(long[] ids,int[] counselorIds ,Date startTime, Date endTime,int[] goodTyeps,int[] itemTyeps, Pageable pageable);

    Page<BoutiqueRecord> findAllByIdInAndJoinDateAfterAndJoinDateBeforeAndGoodTypeInAndItemTypeIn(long[] ids,Date startTime, Date endTime,int[] goodTyeps,int[] itemTyeps, Pageable pageable);

    Page<BoutiqueRecord> findAllByCounselorIdInAndJoinDateAfterAndJoinDateBeforeAndGoodTypeInAndItemTypeIn(int[] ids, Date startTime, Date endTime, int[] goodTyeps, int[] itemTyeps, Pageable pageable);

    Page<BoutiqueRecord> findAllByJoinDateAfterAndJoinDateBeforeAndGoodTypeInAndAndItemTypeIn(Date startTime, Date endTime,int[] goodTyeps,int[] itemTyeps, Pageable pageable);

    @Query(value = "SELECT record_id FROM hufu_boutique_record_user WHERE uid = ?1",nativeQuery = true)
    long[] getIds(Integer uid);

    @Query(value = "SELECT record_id FROM hufu_boutique_record_user WHERE uid = ?1 and record_id=?2 ",nativeQuery = true)
    long[] decideBoutiqueRecord(Integer uid,Long recordId);

    @Query(value = "SELECT id FROM hufu_user_info WHERE org_id IN (?1)",nativeQuery = true)
    int[] getUserInfoIds(Integer[] orgids);

    BoutiqueRecord[] findAllByIdIn(Long[] recordIds);
}
