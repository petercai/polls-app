package com.example.polls.repository;

import com.example.polls.PollsApplication;
import com.example.polls.model.Choice;
import com.example.polls.model.Poll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;


@SpringBootTest(classes = PollsApplication.class)
@ActiveProfiles("mongo")
@ExtendWith(SpringExtension.class)
class PollRepositoryTest {

    @Autowired
    PollRepository pollRepository;

    @MockBean
    AuditorAware<Long> auditorProvider;

    @BeforeEach
    public void setUp() {
        doReturn(Optional.of(2L)).when(auditorProvider).getCurrentAuditor();
    }

    @Test
    void testSaveWithAuditor() {
        Poll poll = new Poll();
        poll.setQuestion("q");
        poll.setExpirationDateTime(Instant.now().plus(10L,
                                                      ChronoUnit.DAYS));
        poll.setChoices(Arrays.asList(Choice.builder().text("2").build(), Choice.builder().text("3").build()));
        pollRepository.save(poll);
    }
    @Test
    void testUpdateWithAuditor() {
        Optional<Poll> p = pollRepository.findById(27L);
        p.ifPresent(poll->{
            poll.setQuestion("q2");
            pollRepository.save(poll);

        });
    }
}