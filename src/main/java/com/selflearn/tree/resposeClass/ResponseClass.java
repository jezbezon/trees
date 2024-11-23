package com.selflearn.tree.resposeClass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseClass {
    private String code;
    private String response_status;
    private Object data;
}
