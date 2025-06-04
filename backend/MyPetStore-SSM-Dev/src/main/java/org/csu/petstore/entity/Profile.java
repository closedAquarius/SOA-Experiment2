package org.csu.petstore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("profile")
public class Profile {
    @TableId(value = "userid")
    private String username;
    @TableField("langpref")
    private String languagePreference;
    @TableField("favcategory")
    private String favouriteCategoryId;
    @TableField("mylistopt")
    private boolean listOption;
    @TableField("banneropt")
    private boolean bannerOption;

}
