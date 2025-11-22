package org.csu.petstore.orderservice.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("bannerdata")
public class BannerData {
    @TableId("favcategory")
    private String favouriteCategoryId;
    @TableField("bannername")
    private String bannerName;
}
