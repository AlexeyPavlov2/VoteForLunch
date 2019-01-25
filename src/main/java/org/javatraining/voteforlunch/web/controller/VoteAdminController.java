package org.javatraining.voteforlunch.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javatraining.voteforlunch.dto.VoteDto;
import org.javatraining.voteforlunch.exception.NotFoundException;
import org.javatraining.voteforlunch.repository.VoteRepository;
import org.javatraining.voteforlunch.util.entity.VoteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @Autowired
    ObjectMapper mapper;

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAllOrByDate(@RequestParam(value = "date", required = false) LocalDate date) {
        if (date == null) {
            logger.info("Delete all votes");
            voteRepository.deleteAll();
        } else {
            logger.info("Delete by date");
            if (!voteRepository.existsVotesByDatev(date)) {
                throw new NotFoundException("No votes found for this date.");
            }
            voteRepository.removeByDatev(date);
        }
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<VoteDto> getAllByDate(@RequestParam(value = "date", required = false) LocalDate date) {
        if (date == null) {
            logger.info("Get all votes");
            return voteUtil.createDtoListFromEntityList(voteRepository.findAll());
        } else {
            if (!voteRepository.existsVotesByDatev(date)) {
                throw new NotFoundException("No votes found for this date.");
            }
            return voteUtil.createDtoListFromEntityList(voteRepository.findVotesByDatev(date));
        }


    }

}
