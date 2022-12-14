package ex.data.polls.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class PollTopicRestTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PollTopicRepository repo;

    @BeforeEach
    void setUp() {
        Mockito
                .when(repo.save(Mockito.any()))
                .thenReturn(new PollTopic(1L, "TEST"));
    }

    @Test
    void testShouldAdd() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/polltopics/add")
                                .content("""
                                        {"description":"TEST"}
                                         """)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated());
    }
}