package com.mr.myrecord.model.response;

import com.mr.myrecord.model.entity.FieldEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldResponse {

    @ApiModelProperty(hidden = true)
    private Long id;

    @ApiModelProperty(hidden = true)
    private FieldEnum field;

    private List<FieldEnum> fields = new ArrayList<>();

}
