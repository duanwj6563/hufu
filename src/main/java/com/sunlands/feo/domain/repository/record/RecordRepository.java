package com.sunlands.feo.domain.repository.record;

import com.sunlands.feo.domain.model.record.Record;
import com.sunlands.feo.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

    /**
     * 批量查询录音
     *
     * @param idList
     * @return
     */
    List<Record> findAllByIdIn(Long[] idList);

    /**
     * 根据用户查询所有数量
     *
     * @param userId
     * @param selectStatus
     * @param status
     * @return
     */
    @Query(value = "SELECT count(hr.id) FROM `hufu_record` hr WHERE hr.suid=?1 AND hr.select_status=?2 AND hr.`status` !=?3", nativeQuery = true)
    Integer countAllBySuserAndStatusOrStatusAndSelectStatus(Integer userId, Integer selectStatus, Integer status);

    /**
     * 根据质检员和锁定状态查所有录音
     *
     * @param user
     * @param selectStatus
     * @return
     */
    List<Record> findAllBySuserAndSelectStatus(User user, Integer selectStatus);

    /**
     * 批量查询所有未提交的质检打分
     *
     * @param selectStatus
     * @param statuss
     * @return
     */
    List<Record> findAllBySelectStatusAndStatusIn(Integer selectStatus, Integer[] statuss);

    /**
     * 根据录音url获取录音
     * @param url
     * @return
     */
    List<Record> findByDateAndUrl(Date date, String url);

}
