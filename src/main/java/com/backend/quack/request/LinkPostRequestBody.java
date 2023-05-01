package com.backend.quack.request;

import com.backend.quack.model.CollectionVisibility;
import lombok.Data;

@Data
public class LinkPostRequestBody {
    private String name;
    private String url;
    private long collection_id;
}
