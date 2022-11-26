package com.example.weathertest.dto;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@EqualsAndHashCode
public class ModelDTO {

    private String город;

    private String температура;

    private String погода;

    private String скорость_ветра;

    private String давление;

    private Date дата_запроса;

    public ModelDTO() {
    }

    public String toString() {
        return "1. город: " + this.getГород() + "\n" +
                "2. температура: " + this.getТемпература() + "\n" +
                "3. погода: " + this.getПогода() + "\n" +
                "4. скорость_ветра: " + this.getСкорость_ветра() + "\n" +
                "5. давление: " + this.getДавление() + "\n" +
                "6. дата_запроса: " + this.getДата_запроса();



//        return "ModelDTO(город="
//                + this.getГород()
//                + ", температура="
//                + this.getТемпература()
//                + ", погода="
//                + this.getПогода()
//                + ", скорость_ветра="
//                + this.getСкорость_ветра()
//                + ", давление=" + this.getДавление()
//                + ", время_запроса="
//                + this.getВремя_запроса() + ")";
    }
}
