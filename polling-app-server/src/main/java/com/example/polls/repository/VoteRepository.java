package com.example.polls.repository;

import com.example.polls.model.ChoiceVoteCount;
import com.example.polls.model.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VoteRepository extends MongoRepository<Vote, Long> {
    //    @Query("SELECT NEW com.example.polls.model.ChoiceVoteCount(v.choice.id, count(v.id)) FROM Vote v WHERE v.poll.id in :pollIds GROUP BY v.choice.id")
    @Aggregation(pipeline = {"{$match:{'poll.$id':{$in::#{#pollIds}}}}","{$group:{_id:'$choice.$id',count:{$sum:1}}}","{$project:{_id:1,'choiceId':'$_id',voteCount:'$count'}}"})
    List<ChoiceVoteCount> countByPollIdInGroupByChoiceId(@Param("pollIds") List<Long> pollIds);

    //    @Query("SELECT NEW com.example.polls.model.ChoiceVoteCount(v.choice.id, count(v.id)) FROM Vote v WHERE v.poll.id = :pollId GROUP BY v.choice.id")
    @Aggregation(pipeline = {"{$match:{'poll.$id'::#{#pollId}}}","{$group:{_id:'$choice.$id',count:{$sum:1}}}","{$project:{_id:1,'choiceId':'$_id',voteCount:'$count'}}"})
    List<ChoiceVoteCount> countByPollIdGroupByChoiceId(@Param("pollId") Long pollId);

    @Query("{'user': {'id': :#{#userId}}, 'poll': {'id': {$in: :#{#pollIds}}}}")
    List<Vote> findByUserIdAndPollIdIn(
            @Param("userId") Long userId,
            @Param("pollIds") List<Long> pollIds
    );

    @Query("{'user': {'id': :#{#userId}}, 'poll': {'id': :#{#pollId}}}")
    Vote findByUserIdAndPollId(
            @Param("userId") Long userId,
            @Param("pollId") Long pollId
    );

//    @Query("SELECT COUNT(v.id) from Vote v where v.user.id = :userId")
    @Query(value = "{'productDetails.productType': ?0}", count = true)
    long countByUserId(@Param("userId") Long userId);

    /* named parameter
    @Query("{'store' : :#{#store}, 'app' : :#{#app} }")
    List<T> findByStoreAndApp(@Param("store") String store, @Param("app") String app);
     */

    @Query("{'user': {'id': :#{#userId}}}")
    Page<Long> findVotedPollIdsByUserId(@Param("userId") Long userId, Pageable pageable);
}

