package com.example.polls.repository;

import com.example.polls.PollsApplication;
import com.example.polls.model.ChoiceVoteCount;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PollsApplication.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("mongo")
@Slf4j
class VoteRepositoryTest {

    @Autowired
    VoteRepository voteRepository;

    @Test
    void countByPollIdInGroupByChoiceId() {
        List<ChoiceVoteCount> choiceVoteCounts = voteRepository.countByPollIdInGroupByChoiceId(Arrays.asList(7L));
        log.info("result: " + choiceVoteCounts.toString());
    }


    @Test
    void countByUserId() {
        Pageable pageable = PageRequest.of(0, 2, Sort.Direction.DESC, "createdAt");
        Page<Long> userVotedPollIds = voteRepository.findVotedPollIdsByUserId(1L, pageable);
        log.info(userVotedPollIds.toString());
    }
}