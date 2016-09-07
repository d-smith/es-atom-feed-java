package atom;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeedController {
    @RequestMapping("/notifications/recent")
    public Feed recent() {
        Feed feed = new Feed();
        feed.setId("1");
        feed.setTitle("some stuffs");
        feed.setUpdated("today");

        return feed;
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
