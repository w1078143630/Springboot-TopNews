package com.txdata.controller;

import com.txdata.pojo.vo.PortalVo;
import com.txdata.service.HeadlineService;
import com.txdata.service.TypeService;
import com.txdata.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: PortalController
 * Description:
 *
 * @Author Wei Wang
 * @Create 2024/4/9 14:54
 * @Version 1.0
 */
@RestController
@RequestMapping("portal")
@CrossOrigin
public class PortalController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private HeadlineService headlineService;


    @GetMapping("findAllTypes")
    public Result findAlltypes(){
        Result result = typeService.findAllTypes();
        return result;
    }

    @PostMapping("findNewsPage")
    public Result findNewsPage(@RequestBody PortalVo portalVo){
        Result result = headlineService.findNewsPage(portalVo);
        return result;

    }
    @PostMapping("showHeadlineDetail")
    public Result showHeadlineDetail(Integer hid){
        Result result = headlineService.showHeadlineDetail(hid);
        return result;
    }


}
