package org.csu.petstore.orderservice.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstore.orderservice.entity.BannerData;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerDataMapper extends BaseMapper<BannerData> {
}
