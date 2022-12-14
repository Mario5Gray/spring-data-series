package ex.data.polls.db;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/polltopics")
class PollTopicController {

    private final PollTopicRepository repo;

    PollTopicController(PollTopicRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    Iterable<PollTopic> all() {
        return repo.findAll();
    }

    @GetMapping("{id}")
    PollTopic getOne(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping("add")
    ResponseEntity<PollTopic> addTopic(@RequestBody PollTopic topic) {
        var ent = repo.save(topic);
        return ResponseEntity.created(URI.create("/polltopics/" + ent.id())).build();
    }
}