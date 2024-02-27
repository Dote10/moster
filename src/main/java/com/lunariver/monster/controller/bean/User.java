package com.lunariver.monster.controller.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value ={"password","ssn"})
@Schema(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity
@Table(name="users3")
public class User{
    @Schema(title = "사용자 ID", description = "사용자 ID는 자동 생성된다.")
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue()
    private Integer id;

    @Schema(title = "사용자 이름", description = "사용자 이름을 입력합니다.")
    @Size(min = 2 , message = "Name은 2글자 이상 입력해 주세요")
    private String name;

    @Schema(title = "등록일", description = "사용자의 등록일을 입력해 주세요")
    @Past(message = "등록일은 미래 날짜를 입력하실 수 없습니다.")
    private Date joinDate;
    
    //@JsonIgnore
    @Schema(title = "비밀번호", description="사용자의 비밀번호를 입력해주세요")
    private String password;

    //@JsonIgnore
    @Schema(title = "주민번호",description = "사용자의 주민번호를 입력해 주세요")
    private String ssn;
}
