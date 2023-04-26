package dev.pack.UserInfo.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dev.pack.Address.Model.AddressUser;
import dev.pack.User.Model.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    private String pocketNumber;
    private String accountNumber;
    @Column(unique = true, nullable = false)
    private String email;
    private String officialName;
    private String nickname;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate joinAt;

    @OneToOne(
            mappedBy = "userInfo",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private AddressUser addressUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userEntity_id", updatable = false)
    private UserEntity userEntity;

    @PrePersist
    public void setJoinAt() {
        this.joinAt = LocalDate.now();
    }


}
