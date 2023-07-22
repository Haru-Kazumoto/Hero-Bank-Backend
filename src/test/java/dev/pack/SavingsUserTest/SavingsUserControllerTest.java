package dev.pack.SavingsUserTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pack.configuration.JWTService;
import dev.pack.modules.savingsUser.*;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.BDDMockito.given;

@WebMvcTest(controllers = SavingsUserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@Import({
        JWTService.class,
        ModelMapper.class
})
public class SavingsUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SavingsUserService savingsUserService;

    @Autowired
    private ObjectMapper objectMapper;

    SavingsUser savingsUser = new SavingsUser();

    @BeforeEach
    void setUp() {
        savingsUser = SavingsUser.builder()
                .title("Mau beli keyboard")
                .savingsBalance(1000000L)
                .build();
    }

    @Test
    @Disabled
    void shouldUpdateSavingsUser() throws Exception{

        UUID savingsId = savingsUser.getId();
        //Act
        given(
                savingsUserService.updateSavingsUserById(ArgumentMatchers.any(), savingsId)
        ).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(
                MockMvcRequestBuilders
                        .put(String.format("/api/v1/savings/update-savings/%s", savingsId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savingsUser))
        );


        //Assert
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.title",
                        CoreMatchers.is("Mau beli keyboard")
                ));
    }

    @Test
//    @Disabled
    void shouldUpdateSavingsUsers() throws Exception {
        // Arrange
        UUID savingsUserId = UUID.randomUUID();
        SavingsUserRequest savingsUserRequest = new SavingsUserRequest();
//        savingsUserRequest.setTitle("Mau beli keyboard");

        SavingsUser savedSavingsUser = new SavingsUser();
        savedSavingsUser.setId(savingsUserId);
        savedSavingsUser.setTitle("Mau beli keyboard");

        SavingsUserResponse expectedResponse = new SavingsUserResponse();
        expectedResponse.setTitle("Mau beli keyboard");

        given(
                savingsUserService.updateSavingsUserById(
                        ArgumentMatchers.any(), //Request body
                        ArgumentMatchers.any() //Request id
                )
        ).willReturn(savedSavingsUser);

        // Act
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/savings/update-savings/{id}", savingsUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savingsUserRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.title",
                        CoreMatchers.is("Mau beli keyboard"))
                )
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedResponse)));

        // Assert
        Mockito.verify(
                savingsUserService,
                Mockito.times(1)).updateSavingsUserById(
                        ArgumentMatchers.any(),
                ArgumentMatchers.any()
        );
    }
}
