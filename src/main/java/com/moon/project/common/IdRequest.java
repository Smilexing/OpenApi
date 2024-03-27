package com.moon.project.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Tom Smile
 * @version 1.0
 * @description: TODO
 * @date 2024/3/27 15:51
 */
@Data
public class IdRequest implements Serializable {
    private Long id;
    private static final long serialVersionUID = 1L;
}
