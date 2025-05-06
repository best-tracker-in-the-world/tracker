package ru.rogzy.tracker_backend.repository.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.Instant;

@Table("verification_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationTokenDO {
    @Id
    private Long id;
    private String token;
    @Column("user_id")
    private Long userId;
    @Column("created_at")
    private Instant createdAt;
    @Column("expires_at")
    private Instant expiresAt;
}
