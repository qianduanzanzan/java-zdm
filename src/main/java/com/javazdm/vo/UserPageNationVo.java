package com.javazdm.vo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserPageNationVo {
    private String nickName;
    private Integer stopFlag;
    @NotNull(message = "参数current未传")
    @Min(value = 1,message = "current不能小于1")
    private Integer current;
    @NotNull(message = "参数size未传")
    @Min(value = 1,message = "size不能小于1")
    private Integer size;
}
