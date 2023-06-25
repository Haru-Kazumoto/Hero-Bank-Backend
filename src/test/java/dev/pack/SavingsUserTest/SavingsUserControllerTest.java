package dev.pack.SavingsUserTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pack.configuration.JWTService;
import dev.pack.modules.savingsUser.SavingsUser;
import dev.pack.modules.savingsUser.SavingsUserController;
import dev.pack.modules.savingsUser.SavingsUserService;
import dev.pack.modules.user.UserController;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigInteger;

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
    void shouldCreateSavingsUser() throws Exception {

        given(
                savingsUserService.createSavingsUserBody(ArgumentMatchers.any())
        ).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/savings/create-savings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savingsUser))
        );

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.length()",
                        CoreMatchers.is(1)
                ))
                .andDo(MockMvcResultHandlers.print());

    }
}
