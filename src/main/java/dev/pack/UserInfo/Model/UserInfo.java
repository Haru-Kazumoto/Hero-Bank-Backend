package dev.pack.UserInfo.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.pack.Address.Model.AddressUser;
import dev.pack.User.Model.UserEntity;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "User_Info_Table")
public class UserInfo {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    private Long pocketNumber; //Auto generate random number 12 char by spring
    private Long accountNumber; //Auto generate random number 10 char by spring
    @Column(unique = true, nullable = false)
    private String email;
    private String officialName;
    private String nickname;

    @CreatedDate
    @Timestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate joinAt;

//    @OneToOne(mappedBy = "userInfo", cascade = CascadeType.ALL)
//    private AddressUser addressUser;

    @OneToOne
    @JoinColumn(name = "userEntity_id")
    private UserEntity userEntity;

    public void setJoinAt(LocalDate joinAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String formattedDate = joinAt.format(formatter);
        this.joinAt = LocalDate.parse(formattedDate, formatter);
    }

    public void setPocketNumber(Long pocketNumber) {
        this.pocketNumber = pocketNumber != null
                        ? pocketNumber
                        : generateRandomNumber();
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber != null
                        ? accountNumber
                        : generateRandomNumber();
    }

    //Auto generating the pocketNumber and accountNumber.
    private Long generateRandomNumber() {
        Random random_number = new Random();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            int randomDigit = random_number.nextInt(9) + 1;
            builder.append(randomDigit);
        }

        return Long.parseLong(builder.toString());
    }
}
