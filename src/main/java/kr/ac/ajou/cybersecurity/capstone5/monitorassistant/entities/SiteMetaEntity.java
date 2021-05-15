package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SiteMetaEntity {

    private String code;
    private String name;

}
