package atom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oracle.ucp.jdbc.*;

import java.sql.Connection;
import java.sql.SQLException;

@RestController
public class FeedController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final AtomRepository atomRepo;

    public FeedController() throws SQLException {
        atomRepo = new JdbcAtomRepository();
    }

    @RequestMapping("/notifications/recent")
    public Feed recent() {
        return atomRepo.retrieveCurrentFeed();
    }

    @RequestMapping("/notifications/{feedId}")
    public Feed feedById(@PathVariable String feedId) {
        Feed feed = new Feed();
        feed.setId("1");
        feed.setTitle("some stuffs");
        feed.setUpdated("today");

        return feed;
    }

    @RequestMapping("/notifications/{aggregateId}/{version}")
    public Entry getEntry(@PathVariable String aggregateId, @PathVariable int version) {
        Entry entry = new Entry();
        entry.setAggregateId(aggregateId);
        entry.setContent("xxx==");
        entry.setPublished("nowish");
        entry.setTypeCode("ABC");
        entry.setVersion(version);

        return entry;
    }
}
