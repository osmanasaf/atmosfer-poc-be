package org.atmosfer.seed.userservice.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetToken {

        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private Long id;

        @Column
        private String email;

        @Column
        LocalDateTime expiryDate;

        @Column
        private String token;
}
