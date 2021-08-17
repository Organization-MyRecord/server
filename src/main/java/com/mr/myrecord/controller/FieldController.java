package com.mr.myrecord.controller;

import com.mr.myrecord.model.entity.FieldEnum;
import com.mr.myrecord.model.response.FieldResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "분야 리스트")
public class FieldController {

    @GetMapping("/fields")
    @ApiOperation(value = "분야 리스트 반환")
    public FieldResponse getFields() {
        FieldResponse fieldResponse = new FieldResponse();

        for(FieldEnum f : FieldEnum.values()) {
            fieldResponse.getFields().add(f);
        }
        return fieldResponse;
    }

}
