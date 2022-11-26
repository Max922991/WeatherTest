package com.example.weathertest.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "models")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "city")
    String город;

    @Column(name = "temp")
    String температура;

    @Column(name = "condition")
    String погода;

    @Column(name = "wind_speed")
    String скорость_ветра;

    @Column(name = "pressure")
    String давление;

    @Column(name = "time_request")
    private Date дата_запроса;
}
