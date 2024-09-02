package com.petinder.userservice.dto;

import lombok.Value;

/**
 * An empty response that will be assigned to {@link ResponseDTO}'s data when no data is returned after an API call.
 * This is used so that data will never be null if an API call success.
 */
@Value
public class EmptyResponse {
}
