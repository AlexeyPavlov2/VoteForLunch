package org.javatraining.voteforlunch.web.controller;

import org.javatraining.voteforlunch.dto.VoteDto;
import org.javatraining.voteforlunch.exception.NotFoundException;
import org.javatraining.voteforlunch.model.Vote;
import org.javatraining.voteforlunch.repository.VoteRepository;
import org.javatraining.voteforlunch.util.DateTimeUtil;
import org.javatraining.voteforlunch.util.entity.VoteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = VoteAdminController.REST_URL)
public class VoteAdminController {
    private static final Logger logger = LoggerFactory.getLogger(VoteAdminController.class);
    static final String REST_URL = "/admin/votes";

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteUtil voteUtil;

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAll() {
        logger.info("Delete all votes");
        voteRepository.deleteAll();
    }

    @DeleteMapping(value = "/{date}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteByDate(@PathVariable("date") String date) {
        logger.info("Delete by date");
        LocalDateTime localDateTime = DateTimeUtil.getParseDateString(date).atStartOfDay();
        if (!voteRepository.existsVotesByDatev(localDateTime)) {
            throw new NotFoundException("No votes found for this date.");
        }
        voteRepository.removeByDatev(localDateTime);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<VoteDto> getAll() {
        return voteUtil.createDtoListFromEntityList(voteRepository.findAll());
    }

    @GetMapping(value = "/{date}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<VoteDto> getAllByDate(@PathVariable("date") String date) {
        LocalDateTime dateTime = DateTimeUtil.getParseDateString(date).atStartOfDay();
        if (!voteRepository.existsVotesByDatev(dateTime)) {
            throw new NotFoundException("No votes found for this date.");
        }
        return voteUtil.createDtoListFromEntityList(voteRepository.findVotesByDatev(dateTime));
    }










}
