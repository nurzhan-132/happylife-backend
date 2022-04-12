package com.happylife.library.myspringbootproject.payload.request;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscribeRequest {

    private Long periodicalId;

    private String username;
}
