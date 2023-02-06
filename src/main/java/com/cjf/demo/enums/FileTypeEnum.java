package com.cjf.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

@AllArgsConstructor
@Getter
public enum FileTypeEnum {

    XLS("s", "xls"),
    XLSX("s", "xlsx"),
    CSV("s", "csv"),
    PDF("f", "pdf"),
    DOCX("f", "docx"),
    WPS("f", "wps"),
    DOC("w", "doc"),
    PPT("p", "ppt");

    private final String type;
    private final String name;

    /**
     * 根据状态获取枚举对象
     * @param name
     * @return 默认返回空，表示不指定类型
     */
    public static String get(String name) {
        for (FileTypeEnum fileTypeEnum : values()) {
            if (!StringUtils.isEmpty(name) && name.contains(fileTypeEnum.name)) {
                return fileTypeEnum.type;
            }
        }
        return null;
    }

}
