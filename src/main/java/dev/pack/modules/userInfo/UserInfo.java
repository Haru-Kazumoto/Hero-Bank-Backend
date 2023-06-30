package dev.pack.modules.userInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dev.pack.modules.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "User_Info_Table")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class UserInfo{

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String idCardNumber;

    @JsonFormat(pattern = "dd MM yyyy - HH:mm")
    @DateTimeFormat(pattern = "dd MM yyyy - HH:mm")
    private Date joinAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", updatable = false)
    private UserEntity userEntityId;

    @PrePersist
    public void setJoinAt() {
        this.joinAt = new Date();
    }
}
