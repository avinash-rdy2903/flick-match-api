package com.flickmatch.api.pojoClass;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicUserDetails {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
}
