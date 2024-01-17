package com.mcw.sparkweb.domain.po;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author miaochangwei1
 * @Package : com.mcw.sparkweb.domain.po
 * @Description : TODO
 * @Create on : 2024/1/16 10:14
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Speaker {

    @JSONField(name = "_id")
    private String id;

    private String name;

    private String firstName;

    private String title;

    private Date geburtsdatum;

    private String geburtsort;

    private Date sterbedatum;

    private String geschlecht;

    private String beruf;

    private String akademischertitel;

    private String familienstand;

    private String religion;

    private String vita;

    private String adressing;

    private List<String> absence;

    private String party;

    private String role;
}
