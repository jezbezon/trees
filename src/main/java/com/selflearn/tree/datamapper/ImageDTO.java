package com.selflearn.tree.datamapper;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImageDTO {
    private Long id;
    private String fileName;
    private String fileType;
    private String downloadUrl;

}
