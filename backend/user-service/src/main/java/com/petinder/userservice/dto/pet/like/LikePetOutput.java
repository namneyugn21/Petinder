package com.petinder.userservice.dto.pet.like;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value
public class LikePetOutput {
    Boolean isAvailable;
    LocalDateTime earliestReservableDate;
    Set<LocalDateTime> currentAppointments;
}
