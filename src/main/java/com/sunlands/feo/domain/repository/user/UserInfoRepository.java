package com.sunlands.feo.domain.repository.user;

import com.sunlands.feo.domain.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by huang on 2017/11/30.
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    UserInfo findByName(String name);
}
