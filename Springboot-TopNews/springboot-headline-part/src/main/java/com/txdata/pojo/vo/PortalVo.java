package com.txdata.pojo.vo;

import lombok.Data;

/**
 * ClassName: PortalVo
 * Description:
 *
 * @Author Wei Wang
 * @Create 2024/4/9 15:07
 * @Version 1.0
 */
@Data
public class PortalVo {
    private String keyWords;
    private int type = 0;
    private int pageNum = 1;
    private int pageSize = 10;
}
